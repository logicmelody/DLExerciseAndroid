package com.dl.dlexerciseandroid.widget;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * Override the method resolveClass of ObjectInputStream in order to find class
 */
public class PackedInputStream extends ObjectInputStream {

    private ClassLoader mClassLoader;

    public PackedInputStream(ByteArrayInputStream bi, ClassLoader c) throws IOException {
        super(bi);
        // TODO Auto-generated constructor stub
        mClassLoader = c;
    }

    // 改寫這個method，讓在serialization and deserialization階段取得的class name，搭配傳進來別的app環境下的class loader
    // 可以還原原本的class
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Class resolveClass(ObjectStreamClass desc)
            throws IOException, ClassNotFoundException {
        String name = desc.getName();

        try {
            return Class.forName(name, false, mClassLoader);
        } catch (ClassNotFoundException ex) {
            throw ex;
        }
    }
}