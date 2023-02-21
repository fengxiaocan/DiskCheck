package com.n.disk.common;

import java.util.Map;

public class MapUtils {
    MapUtils() {
    }

    /**
     * 添加非空的数据集合
     *
     * @param map
     * @param key
     * @param value
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> boolean putNoNull(Map<K, V> map, K key, V value) {
        if (map != null && key != null && value != null) {
            map.put(key, value);
            return true;
        }
        return false;
    }

    public static boolean clear(Map map) {
        if (map != null) {
            map.clear();
            return true;
        }
        return false;
    }
}
