package com.n.disk.common;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class EmptyUtils {


    /**
     * 判断对象是否为空
     *
     * @param obj 对象
     * @return {@code true}: 为空<br>{@code false}: 不为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        return false;
    }

    /**
     * 判断对象是否非空
     *
     * @param obj 对象
     * @return {@code true}: 非空<br>{@code false}: 空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static void checkNull(Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
    }

    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     *
     * @param obj
     * @return
     */
    public static boolean isNull(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence) {
            //后台可能会返回“null”
            return StringUtil.isEmpty((CharSequence) obj) || "null".equals(obj);
        }
        return false;
    }
}