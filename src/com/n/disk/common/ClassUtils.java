package com.n.disk.common;

import java.lang.reflect.*;

public  class ClassUtils {
    /**
     * 获取构造方法
     *
     * @param aClass
     * @param parameterTypes
     * @return
     */
    public static Constructor getConstructor(Class aClass, Class<?>... parameterTypes) {
        try {
            Constructor constructor = aClass.getConstructor(parameterTypes);
            constructor.setAccessible(true);
            return constructor;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 直接反射调用构造函数
     *
     * @param aClass
     * @param parameterTypes
     * @param parameter
     * @return
     */
    public static <T> T newInstance(Class aClass, Class<?>[] parameterTypes, Object[] parameter) {
        Constructor constructor = getConstructor(aClass, parameterTypes);
        if (constructor != null) {
            try {
                return (T) constructor.newInstance(parameter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 直接反射调用构造函数
     *
     * @param aClass
     * @param parameter 参数数组
     * @return
     */
    public static <T> T newInstance(Class aClass, Object... parameter) {
        Class<?>[] parameterTypes = null;
        if (parameter != null) {
            parameterTypes = new Class[parameter.length];
            for (int i = 0; i < parameter.length; i++) {
                parameterTypes[i] = parameter[i].getClass();
            }
        }
        Constructor constructor = getConstructor(aClass, parameterTypes);
        if (constructor != null) {
            try {
                return (T) constructor.newInstance(parameter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 直接反射调用构造函数
     *
     * @param aClass
     * @return
     */
    public static <T> T newInstance(Class aClass) {
        Constructor[] constructors = aClass.getConstructors();
        for (Constructor constructor : constructors) {
            constructor.setAccessible(true);
            Class[] parameterTypes = constructor.getParameterTypes();
            Object[] objects = null;
            if (parameterTypes != null) {
                objects = new Object[parameterTypes.length];
            }
            try {
                return (T) constructor.newInstance(objects);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取方法
     *
     * @param aClass
     * @param methodName
     * @return
     */
    public static Method getMethod(Class aClass, String methodName, Class<?>... parameterTypes) {
        try {
            Method declaredMethod = aClass.getDeclaredMethod(methodName, parameterTypes);
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取方法
     *
     * @param aClass
     * @param methodName
     * @return
     */
    public static Method getMethod(Class aClass, String methodName) {
        try {
            Method declaredMethod = aClass.getDeclaredMethod(methodName);
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用方法
     *
     * @param aClass
     * @param methodName
     * @param parameterTypes
     * @param values
     * @return
     */
    public static <T> T invokeMethod(Class aClass, String methodName, Class<?>[] parameterTypes, Object[] values) {
        Method method = getMethod(aClass, methodName, parameterTypes);
        if (method != null) {
            try {
                return (T) method.invoke(aClass, values);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 调用方法
     *
     * @param aClass
     * @param methodName
     * @return
     */
    public static <T> T invokeMethod(Class aClass, String methodName) {
        Method method = getMethod(aClass, methodName);
        if (method != null) {
            try {
                return (T) method.invoke(aClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 获取成员变量
     *
     * @param aClass
     * @param fieldName
     * @return
     */
    public static Field getField(Class aClass, String fieldName) {
        try {
            Field declaredField = aClass.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            return declaredField;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取成员变量的值
     *
     * @param aClass
     * @param object
     * @param fieldName
     * @param <T>
     * @return
     */
    public static <T> T getFieldValue(Class aClass, Object object, String fieldName) {
        Field field = getField(aClass, fieldName);
        if (field != null) {
            try {
                return (T) field.get(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 反射设置成员变量的值
     *
     * @param aClass    class类
     * @param object    需要反射设置的对象
     * @param fieldName 成员变量名字
     * @param value     需要设置的值
     */
    public static void setFieldValue(Class aClass, Object object, String fieldName, Object value) {
        Field field = getField(aClass, fieldName);
        if (field != null) {
            try {
                field.set(object, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取当前类的泛型
     */
    public static <T> Class<T> getParameterizedClass(Class aClass, int index) {
        return (Class<T>) getParameterizedType(aClass, index);
    }

    /**
     * 获取当前类的第一个泛型
     */
    public static <T> Class<T> getParameterizedClass(Class aClass) {
        return getParameterizedClass(aClass, 0);
    }

    /**
     * 获取当前类的泛型
     */
    public static Type getParameterizedType(Class aClass, int index) {
        ParameterizedType parameterizedType = (ParameterizedType) aClass.getGenericSuperclass();
        return parameterizedType.getActualTypeArguments()[index];
    }

    /**
     * 获取当前类的第一个泛型
     */
    public static Type getParameterizedType(Class aClass) {
        ParameterizedType parameterizedType = (ParameterizedType) aClass.getGenericSuperclass();
        return parameterizedType.getActualTypeArguments()[0];
    }
}
