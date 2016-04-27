package com.dl.dlexerciseandroid.utility.component;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * Override the method resolveClass of ObjectInputStream in order to find class
 */
public class TaskInputStream extends ObjectInputStream {

    private ClassLoader mClassLoader;
    TaskInputStream(ByteArrayInputStream bi, ClassLoader c) throws IOException {
        super(bi);
        // TODO Auto-generated constructor stub
        mClassLoader = c;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Class resolveClass(ObjectStreamClass desc)
            throws IOException, ClassNotFoundException
    {
        String name = desc.getName();
        try {
            return Class.forName(name, false, mClassLoader);
        } catch (ClassNotFoundException ex) {
                throw ex;
        }
    }
}