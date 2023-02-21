package com.n.disk.common;

import java.io.*;

public class ObjectUtils {

    /**
     * Require the object is not null.
     *
     * @param object  The object.
     * @param message The message to use with the NullPointerException.
     * @param <T>     The value type.
     * @return the object
     * @throws NullPointerException if object is null
     */
    public static <T> T requireNonNull(final T object, final String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    /**
     * Return the hash code of object.
     *
     * @param o The object.
     * @return the hash code of object
     */
    public static int hashCode(final Object o) {
        return o != null ? o.hashCode() : 0;
    }

    /**
     * Save.
     *
     * @param file  the file
     * @param value the value
     */
    public static void save(File file, Serializable value) {
        if (!FileUtils.isFileExists(file)) {
            return;
        }
        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        try {
            fos = new FileOutputStream(file);
            os = new ObjectOutputStream(fos);
            os.writeObject(value);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(fos, os);
        }
    }

    /**
     * Save.
     *
     * @param fileName the file name
     * @param value    the value00
     */
    public static void save(String fileName, Serializable value) {
        save(new File(fileName), value);
    }

    /**
     * Save.
     *
     * @param <T>  the type parameter
     * @param file the file
     * @return the t
     */
    public static <T extends Serializable> T get(File file) {
        if (!FileUtils.isFileExists(file)) {
            return null;
        }
        FileInputStream fis = null;
        ObjectInputStream os = null;
        T t = null;
        try {
            fis = new FileInputStream(file);
            os = new ObjectInputStream(fis);
            t = (T) os.readObject();
            os.close();
        } catch (Exception e) {
        } finally {
            IoUtil.close(fis);
            IoUtil.close(os);
        }
        return t;
    }

    /**
     * Save.
     *
     * @param <T>      the type parameter
     * @param fileName the file path name
     * @return the t
     */
    public static <T extends Serializable> T get(String fileName) {
        return get(new File(fileName));
    }
}
