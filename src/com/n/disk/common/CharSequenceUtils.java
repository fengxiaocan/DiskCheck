package com.n.disk.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharSequenceUtils {


    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 字符串局部对比匹配
     *
     * @param cs
     * @param ignoreCase
     * @param thisStart
     * @param substring
     * @param start
     * @param length
     * @return
     */
    public static boolean regionMatches(CharSequence cs, boolean ignoreCase, int thisStart, CharSequence substring,
                                        int start, int length) {
        if ((cs instanceof String) && (substring instanceof String)) {
            return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
        }
        int index1 = thisStart;
        int index2 = start;
        int tmpLen = length;

        while (tmpLen-- > 0) {
            char c1 = cs.charAt(index1++);
            char c2 = substring.charAt(index2++);

            if (c1 == c2) {
                continue;
            }

            if (!(ignoreCase)) {
                return false;
            }

            if ((Character.toUpperCase(c1) != Character.toUpperCase(c2)) && (Character.toLowerCase(c1) !=
                    Character.toLowerCase(c2))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 所有字母转为大写
     *
     * @param character 待转字符串
     * @return 大写字符串
     */
    public static CharSequence toUpperCase(CharSequence character) {
        if (character == null || character.equals("")) {
            return character;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < character.length(); i++) {
            buffer.append(Character.toUpperCase(character.charAt(i)));
        }
        return buffer.toString();
    }

    /**
     * 所有字母转为小写
     *
     * @param character 待转字符串
     * @return 小写字符串
     */
    public static CharSequence toLowerCase(CharSequence character) {
        if (character == null || character.equals("")) {
            return character;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < character.length(); i++) {
            buffer.append(Character.toLowerCase(character.charAt(i)));
        }
        return buffer.toString();
    }

    /**
     * 截取字符串
     *
     * @param content
     * @param lenght
     * @return
     */
    public static CharSequence substring(CharSequence content, int lenght) {
        if (content != null) {
            if (content.length() > lenght) {
                return content.subSequence(0, lenght);
            } else {
                return content;
            }
        }
        return content;
    }

    /**
     * 判断一个字符串是不是数字
     */
    public static boolean isNumber(CharSequence str) {
        Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) {
            return true;
        }
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}