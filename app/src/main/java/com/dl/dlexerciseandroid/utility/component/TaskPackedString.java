package com.dl.dlexerciseandroid.utility.component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.util.Base64;

/**
 * A utility class for creating and modifying Strings that are tagged and packed together.
 *
 * Uses non-printable (control chars) for internal delimiters;  Intended for regular displayable
 * strings only, so please use base64 or other encoding if you need to hide any binary data here.
 *
 * Binary compatible with Address.pack() format, which should migrate to use this code.
 */
public class TaskPackedString {

    public static final class Key {
        public static final String ACTION = "action";
        public static final String TYPE = "type";
        public static final String DATA = "data";
        public static final String FLAG = "flag";
        public static final String PACKAGE_NAME = "package_name";
        public static final String CLASS_NAME = "class_name";
    }

    /**
     * Packing format is:
     *   element : [ value ] or [ value TAG-DELIMITER tag ]
     *   packed-string : [ element ] [ ELEMENT-DELIMITER [ element ] ]*
     */
    private static Context mContext;
    private static final char DELIMITER_ELEMENT = '\1';
    private static final char DELIMITER_TAG = '\2';
    private static final String DELIMITER_TYPE = "\3";

    private String mString;
    private HashMap<String, Object> mExploded;
    private static final HashMap<String, Object> EMPTY_MAP = new HashMap<String, Object>();
    private static HashMap<Class<?>, String> sTypeMap = new HashMap<Class<?>, String>();
    private String mPackageName;

    static {
        sTypeMap.put(java.lang.Long.class, "1");
        sTypeMap.put(java.lang.Integer.class, "2");
        sTypeMap.put(java.lang.Float.class, "3");
        sTypeMap.put(java.lang.String.class, "4");
        sTypeMap.put(java.lang.Boolean.class, "5");
        sTypeMap.put(java.lang.Double.class, "6");
        sTypeMap.put(java.lang.Character.class, "7");
        sTypeMap.put(java.lang.CharSequence.class, "8");
        sTypeMap.put(java.lang.Byte.class, "9");
    }

    /**
     * Create a packed string using an already-packed string (e.g. from database)
     * @param string packed string
     */
    public TaskPackedString(String string, Context context) {
        mContext = context;
        mString = string;
        mExploded = null;
    }

    /**
     * Get the value referred to by a given tag.  If the tag does not exist, return null.
     * @param tag identifier of string of interest
     * @return returns value, or null if no string is found
     */
    public Object get(String tag) {
        if (mExploded == null) {
            mExploded = explode(mString);
        }
        return mExploded.get(tag);
    }

    /**
     * Return a map of all of the values referred to by a given tag.  This is a shallow
     * copy, don't edit the values.
     * @return a map of the values in the packed string
     */
    public Map<String, Object> unpack() {
        if (mExploded == null) {
            mExploded = explode(mString);
        }
        return new HashMap<String,Object>(mExploded);
    }

    /**
     * Read out all values into a map.
     */
    private static HashMap<String, Object> explode(String packed) {
        if (packed == null || packed.length() == 0) {
            return EMPTY_MAP;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();

        int length = packed.length();
        int elementStartIndex = 0;
        int elementEndIndex = 0;
        int tagEndIndex = packed.indexOf(DELIMITER_TAG);

        while (elementStartIndex < length) {
            elementEndIndex = packed.indexOf(DELIMITER_ELEMENT, elementStartIndex);
            if (elementEndIndex == -1) {
                elementEndIndex = length;
            }
            String tag;
            String value;
            if (tagEndIndex == -1 || elementEndIndex <= tagEndIndex) {
                // in this case the DELIMITER_PERSONAL is in a future pair (or not found)
                // so synthesize a positional tag for the value, and don't update tagEndIndex
                value = packed.substring(elementStartIndex, elementEndIndex);
                tag = Integer.toString(map.size());
            } else {
                value = packed.substring(elementStartIndex, tagEndIndex);
                tag = packed.substring(tagEndIndex + 1, elementEndIndex);
                // scan forward for next tag, if any
                tagEndIndex = packed.indexOf(DELIMITER_TAG, elementEndIndex + 1);
            }
            map.put(tag, convertStringToObject(value));
            elementStartIndex = elementEndIndex + 1;
        }

        return map;
    }

    private static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private static String convertObjectToString(Object value) {
        String result = "";
        ByteArrayOutputStream bo;
        ObjectOutputStream out;
        Class<?> _class = value.getClass();

        if(sTypeMap.containsKey(_class)) {
            result = sTypeMap.get(_class) + DELIMITER_TYPE + value.toString();
        } else {
            try {
                bo = new ByteArrayOutputStream();

                bo = new ByteArrayOutputStream();
                out = new ObjectOutputStream(bo);
                out.writeObject(value);

                result = Builder.mPackagename + DELIMITER_TYPE + Base64
                        .encodeToString(bo.toByteArray(), Base64.DEFAULT);
                bo.close();
                out.close();
            } catch (java.io.NotSerializableException e) {
                throw new UnsupportedOperationException("Sorry! The non-Serializable class named "
                        + _class.getName()
                        + " is not supported. Please use Serializable class type instead");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

    private static Object convertStringToObject(String s) {
        Object result = new Object();
        ByteArrayInputStream bi;
        TaskInputStream in;
        String[] array = s.split(DELIMITER_TYPE);
        String typeString = array[0];
        // value could be null, only type existed
        if(array.length < 2 || TextUtils.isEmpty(array[1])){
            return null;
        }
        try {
            if (sTypeMap.containsValue(typeString)) {
                Class<?> _class = getKeyByValue(sTypeMap, typeString);
                result = array[1];
                if (_class != String.class) {
                    result = _class.getMethod("valueOf", new Class[] {String.class})
                                .invoke(null, new Object[] {result});
                }
            } else {
                bi = new ByteArrayInputStream(
                        Base64.decode(array[1], Base64.DEFAULT));

                Context remote1 = null;

                try {
                    remote1 = mContext.createPackageContext(typeString,
                            Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);

                } catch (NameNotFoundException e) {
                    // TODO Auto-generated catch block
                    throw new IllegalArgumentException(e.getMessage());
                }
                in = new TaskInputStream(bi, remote1.getClassLoader());
                result = in.readObject();

                bi.close();
                in.close();
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Builder class for creating TaskPackedString values.  Can also be used for editing existing
     * TaskPackedString representations.
     */
    static public class Builder {
        HashMap<String, Object> mMap;
        static String mPackagename;

        public void setPackageName(String packagename) {
            mPackagename = packagename;
        }

        /**
         * Create a builder that's empty (for filling)
         */
        public Builder() {
            mMap = new HashMap<String, Object>();
        }

        /**
         * Create a builder using the values of an existing TaskPackedString (for editing).
         */
        public Builder(String packed) {
            mMap = explode(packed);
        }

        /**
         * Add a tagged value
         * @param tag identifier of string of interest
         * @param value the value to record in this position.  null to delete entry.
         */
        public void put(String tag, Object value) {
            if (value == null) {
                mMap.remove(tag);
            } else {
                mMap.put(tag, value);
            }
        }

        /**
         * Get the value referred to by a given tag.  If the tag does not exist, return null.
         * @param tag identifier of string of interest
         * @return returns value, or null if no string is found
         */
        public Object get(String tag) {
            return mMap.get(tag);
        }

        /**
         * Pack the values and return a single, encoded string
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String,Object> entry : mMap.entrySet()) {
                if (sb.length() > 0) {
                    sb.append(DELIMITER_ELEMENT);
                }
                sb.append(convertObjectToString(entry.getValue()));
                sb.append(DELIMITER_TAG);
                sb.append(entry.getKey());
            }
            return sb.toString();
        }

    }
}
