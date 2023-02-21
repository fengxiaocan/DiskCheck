package com.n.disk.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @author wind
 */
public class RegexUtil {

    private final static String PHONE_NUMBER = "^(13[0-9]|14[579]|15[0-3,5-9]|16[0-9]|17[0135678]|18[0-9]|19[89])\\d{8}$";

    private final static String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    private final static String HK_PHONE_NUMBER = "^(5|6|8|9)\\d{7}$";

    private final static String POSITIVE_INTEGER = "^\\d+$";

    private final static String MONEY = "^(\\d+(?:\\.\\d+)?)$";

    private final static String ID_CARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$";

    /**
     * 校验邮箱
     *
     * @param str
     * @return
     */
    public static boolean checkEmail(String str) {
        return checkStr(EMAIL, str);
    }

    /**
     * 校验身份证
     *
     * @param str
     * @return
     */
    public static boolean checkIdCard(String str) {
        return checkStr(ID_CARD, str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     *
     * @param str
     * @return
     */
    public static boolean checkPhone(String str) {
        return checkStr(PHONE_NUMBER, str);
    }

    /**
     * 校验香港
     *
     * @param str
     * @return
     */
    public static boolean checkHKPhone(String str) {
        return checkStr(HK_PHONE_NUMBER, str);
    }

    /**
     * 校验正整数
     *
     * @param str
     * @return
     */
    public static boolean checkPlusInteger(String str) {
        return checkStr(POSITIVE_INTEGER, str);
    }

    /**
     * 校验money
     *
     * @param str
     * @return
     */
    public static boolean checkMoney(String str) {
        return checkStr(MONEY, str);
    }

    /**
     * @param regex
     * @param str
     * @return
     */
    private static boolean checkStr(String regex, String str) {
        if (regex == null || str == null) {
            return false;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    /**
     * 验证手机号（简单）
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileSimple(CharSequence input) {
        return isMatch(RegexUnit.REGEX_MOBILE_SIMPLE, input);
    }

    /**
     * 验证手机号（精确）
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileExact(CharSequence input) {
        return isMatch(RegexUnit.REGEX_MOBILE_SIMPLE, input);
    }

    /**
     * 验证电话号码
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isTel(CharSequence input) {
        return isMatch(RegexUnit.REGEX_TEL, input);
    }

    /**
     * 验证身份证号码15位
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard15(CharSequence input) {
        return isMatch(RegexUnit.REGEX_ID_CARD15, input);
    }

    /**
     * 验证身份证号码18位
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard18(CharSequence input) {
        return isMatch(RegexUnit.REGEX_ID_CARD18, input);
    }

    /**
     * 验证邮箱
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isEmail(CharSequence input) {
        return isMatch(RegexUnit.REGEX_EMAIL, input);
    }

    /**
     * 验证URL
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isURL(CharSequence input) {
        return isMatch(RegexUnit.REGEX_URL, input);
    }

    /**
     * 验证汉字
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isZh(CharSequence input) {
        return isMatch(RegexUnit.REGEX_ZH, input);
    }

    /**
     * 验证用户名
     * <p>取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位</p>
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isUsername(CharSequence input) {
        return isMatch(RegexUnit.REGEX_USERNAME, input);
    }

    /**
     * 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isDate(CharSequence input) {
        return isMatch(RegexUnit.REGEX_DATE, input);
    }

    /**
     * 验证IP地址
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIP(CharSequence input) {
        return isMatch(RegexUnit.REGEX_IP, input);
    }

    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    /**
     * 提取数字id
     *
     * @param input
     * @param startString
     * @return
     */
    public static String getIntString(String input, String startString) {
        String firstMatches = getFirstMatches(startString + "\\d*", input);
        String id = getFirstMatches(RegexUnit.REGEX_NUMBER, firstMatches);
        return id;
    }

    /**
     * 提取数字
     *
     * @param input
     * @param startString
     * @return
     */
    public static int getInt(String input, String startString) {
        String firstMatches = RegexUtil.getFirstMatches(startString + "\\d*", input);
        if (firstMatches == null) {
            return 0;
        }
        String id = RegexUtil.getFirstMatches(RegexUnit.REGEX_NUMBER, firstMatches);
        if (id == null) {
            return 0;
        }
        return Integer.valueOf(id);
    }

    /**
     * 获取正则匹配的部分
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return 正则匹配的部分
     */
    public static List<String> getMatches(String regex, CharSequence input) {
        if (input == null) {
            return null;
        }
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * 是否有符合的正则
     *
     * @param regex
     * @param input
     * @return
     */
    public static boolean hasMatches(String regex, CharSequence input) {
        if (input == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    /**
     * 获取正则匹配的第一个
     *
     * @param regex
     * @param input
     * @return
     */
    public static String getFirstMatches(String regex, CharSequence input) {
        if (input == null) {
            return null;
        }
        String matches = null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            matches = matcher.group();
        }
        return matches;
    }

    /**
     * 获取正则匹配分组
     *
     * @param input 要分组的字符串
     * @param regex 正则表达式
     * @return 正则匹配分组
     */
    public static String[] getSplits(String input, String regex) {
        if (input == null) {
            return null;
        }
        return input.split(regex);
    }

    /**
     * 替换正则匹配的第一部分
     *
     * @param input       要替换的字符串
     * @param regex       正则表达式
     * @param replacement 代替者
     * @return 替换正则匹配的第一部分
     */
    public static String getReplaceFirst(String input, String regex, String replacement) {
        if (input == null) {
            return null;
        }
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    /**
     * 替换所有正则匹配的部分
     *
     * @param input       要替换的字符串
     * @param regex       正则表达式
     * @param replacement 代替者
     * @return 替换所有正则匹配的部分
     */
    public static String getReplaceAll(String input, String regex, String replacement) {
        if (input == null) {
            return null;
        }
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }
}
