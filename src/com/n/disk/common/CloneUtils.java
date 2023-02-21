package com.n.disk.common;

import java.io.*;

public  class CloneUtils {

    /**
     * Deep clone.
     *
     * @param data The data.
     * @param <T>  The value type.
     * @return The object of cloned
     */
    public static <T> T deepClone(Serializable data) {
        if (data == null)
            return null;
        return (T) bytes2Object(serializable2Bytes(data));
    }

    private static byte[] serializable2Bytes(Serializable serializable) {
        if (serializable == null)
            return null;
        ByteArrayOutputStream baos;
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos = new ByteArrayOutputStream());
            oos.writeObject(serializable);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IoUtil.close(oos);
        }
    }

    private static Object bytes2Object(final byte[] bytes) {
        if (bytes == null)
            return null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
