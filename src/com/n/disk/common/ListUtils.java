package com.n.disk.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ListUtils {

    /**
     * 将未知类型list转成arraylist
     *
     * @param orig
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> convertToArrayList(Collection<T> orig) {
        //如果为null直接返回，这里也可以把size=0加上
        if (null == orig) {
            return null;
        }
        if (orig instanceof ArrayList) {//判断是否就是ArrayList,如果是，则强转
            return (ArrayList) orig;
        } else {
            ArrayList<T> returnValue = new ArrayList<>(orig.size());
            for (T t : orig) {
                returnValue.add(t);
            }
            //jdk1.8及以上可以使用这样的循环遍历
            //orig.forEach(t -> returnValue.add(t));
            return returnValue;
        }
    }

    /**
     * 获取最后一个数据
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T getLastData(List<T> list) {
        if (EmptyUtils.isEmpty(list)) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    /**
     * 获取第n个数据
     *
     * @param list
     * @param position
     * @param <T>
     * @return
     */
    public static <T> T getData(List<T> list, int position) {
        if (EmptyUtils.isEmpty(list)) {
            return null;
        }
        if (position >= 0 && position < list.size()) {
            return list.get(position);
        } else {
            return null;
        }
    }

    /**
     * 获取第一个数据
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T getFirstData(List<T> list) {
        if (EmptyUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 使用 Iterator 来删除数据
     *
     * @param list
     * @param comparator
     * @param <T>
     */
    public static <T> void deleteIterator(Collection<T> list, Comparator<T> comparator) {
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (comparator.compare(next)) {
                iterator.remove();//使用迭代器的删除方法删除
            }
        }
    }

    public static <T> void delete(Collection<T> list, Comparator<T> comparator) {
        for (T t : list) {
            if (comparator.compare(t)) {
                list.remove(t);//使用迭代器的删除方法删除
                break;
            }
        }
    }
}
