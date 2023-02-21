package com.n.disk.common;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.Collator;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author wind
 */
public class StringUtil {

    /**
     * 判断字符串是否为null或空字符串
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || "".contentEquals(str);
    }


    /**
     * 判断字符串是否为null或空字符串
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(CharSequence str) {
        return str != null && !"".equals(str.toString().trim());
    }


    /**
     * url编码
     *
     * @param str
     * @return
     */
    public static String encodeUrl(String str) {
        String s = null;
        try {
            s = URLEncoder.encode(str, Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return s;
    }

    /**
     * url解码
     *
     * @param str
     * @return
     */
    public static String decodeUrl(String str) {
        String s = null;
        try {
            s = URLDecoder.decode(str, Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }


    /**
     * 字符串分隔 StringTokenizer效率是三种分隔方法中最快的
     *
     * @param str
     * @param sign
     * @return
     */
    public static String[] split(String str, String sign) {
        if (str == null) {
            return new String[]{};
        }
        StringTokenizer token = new StringTokenizer(str, sign);
        String[] strArr = new String[token.countTokens()];
        int i = 0;
        while (token.hasMoreElements()) {
            strArr[i] = token.nextElement().toString();
            i++;
        }
        return strArr;
    }

    /**
     * 字符串拼接
     *
     * @param sign
     * @param strArr
     * @return
     */
    public static String joinStr(String sign, String... strArr) {
        Optional<String> optional = Arrays.stream(strArr).filter(Objects::nonNull).reduce((a, b) -> a + sign + b);
        return optional.orElse("");
    }


    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isTrimEmpty(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return a == null ? b == null : a.equalsIgnoreCase(b);
    }

    /**
     * 限制字符串的长度，超过长度的会截取一部分，末尾显示为自定义b字符串
     *
     * @param a      待限制长度字符串a
     * @param b      待替换字符串b
     * @param length 限制长度
     */
    public static String limitString(String a, String b, int length) {
        if (isEmpty(a)) {
            return a;
        }
        if (a.length() > length) {
            String sub = a.substring(0, length - 1) + b;
            return sub;
        }
        return a;
    }

    /**
     * 限制字符串的长度，超过长度的会截取一部分，末尾显示为...
     *
     * @param a      待限制长度字符串a
     * @param length 限制长度
     */
    public static String limitString(String a, int length) {
        return limitString(a, "...", length);
    }

    /**
     * null转为长度为0的字符串
     *
     * @param s 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static CharSequence noNull(CharSequence s) {
        return s == null ? "" : s;
    }

    /**
     * null转为默认的字符串
     *
     * @param str 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static CharSequence noNull(CharSequence str, CharSequence defaultStr) {
        return str == null ? defaultStr : str;
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) {
            return s;
        }
        return (char) (s.charAt(0) - 32) + s.substring(1);
    }


    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return (char) (s.charAt(0) + 32) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(String s) {
        int len = s == null ? 0 : s.length();
        if (len <= 1) {
            return s;
        }
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 判断多个字符串是否相等，如果其中有一个为空字符串或者null，则返回false，只有全相等才返回true
     */
    public static boolean isEquals(String... agrs) {
        String last = null;
        for (int i = 0; i < agrs.length; i++) {
            String str = agrs[i];
            if (isEmpty(str)) {
                return false;
            }
            if (last != null && !str.equals(last)) {
                return false;
            }
            last = str;
        }
        return true;
    }

    public static boolean isEqualsIgnoreCase(String... agrs) {
        String last = null;
        for (int i = 0; i < agrs.length; i++) {
            String str = agrs[i];
            if (isEmpty(str)) {
                return false;
            }
            if (last != null && !str.equalsIgnoreCase(last)) {
                return false;
            }
            last = str;
        }
        return true;
    }

    /**
     * 判断字符串中是否包含多个字符串
     *
     * @param main
     * @param agrs
     * @return
     */
    public static boolean isContains(String main, String... agrs) {
        if (isEmpty(main)) {
            return false;
        }
        for (int i = 0; i < agrs.length; i++) {
            String str = agrs[i];
            if (isEmpty(str)) {
                return false;
            }
            if (!main.contains(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 把Stream转换成String
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 使用字符缓冲区来拼接字符串
     */
    public static String join(String... s) {
        StringBuffer sb = new StringBuffer();
        for (String s1 : s) {
            sb.append(s1);
        }
        return sb.toString();
    }

    /**
     * 使用字符缓冲区来拼接字符串
     */
    public static String join(Object... objects) {
        StringBuffer sb = new StringBuffer();
        for (Object s1 : objects) {
            sb.append(s1);
        }
        return sb.toString();
    }

    /**
     * 使用字符缓冲区来拼接字符串
     */
    public static StringBuffer builder(String insert, Object... objects) {
        if (objects == null || objects.length == 0) {
            return new StringBuffer();
        }
        StringBuffer sb = new StringBuffer();
        int inserCount = objects.length - 1;

        for (int i = 0; i < objects.length; i++) {
            Object object = objects[i];
            sb.append(object);
            if (i < inserCount) {
                sb.append(insert);
            }
        }
        return sb;
    }

    public static StringBuffer builder(Object... objects) {
        if (objects == null || objects.length == 0) {
            return new StringBuffer();
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < objects.length; i++) {
            Object object = objects[i];
            sb.append(object);
        }
        return sb;
    }


    /**
     * 截取字符串
     *
     * @param content
     * @param length
     * @return
     */
    public static String substring(String content, int length) {
        if (content != null) {
            if (content.length() > length) {
                return content.substring(0, length);
            } else {
                return content;
            }
        }
        return null;
    }

    /**
     * 获取流编码
     */
    public static String getTextCode(String filePath) {// 转码
        try {
            File file = new File(filePath);
            return getTextCode(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取流编码
     */
    public static String getTextCode(File file) {
        try {
            FileInputStream is = new FileInputStream(file);
            return getTextCode(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取流编码
     */
    public static String getTextCode(InputStream is) {// 转码
        String text = "GBK";
        try {
            BufferedInputStream in = new BufferedInputStream(is);
            in.mark(4);
            byte[] first3bytes = new byte[3];
            in.read(first3bytes);//找到文档的前三个字节并自动判断文档类型。
            in.reset();
            if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB && first3bytes[2] == (byte) 0xBF) {// utf-8

                text = "utf-8";
            } else if (first3bytes[0] == (byte) 0xFF && first3bytes[1] == (byte) 0xFE) {
                text = "unicode";
            } else if (first3bytes[0] == (byte) 0xFE && first3bytes[1] == (byte) 0xFF) {
                text = "utf-16be";
            } else if (first3bytes[0] == (byte) 0xFF && first3bytes[1] == (byte) 0xFF) {
                text = "utf-16le";
            } else {
                text = "GBK";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(is);
        }
        return text;
    }

    /**
     * 字符串转换unicode
     */
    public static String str2Unicode(String string) {
        if (string == null) {
            return string;
        }
        try {
            StringBuffer unicode = new StringBuffer();
            for (int i = 0; i < string.length(); i++) {
                // 取出每一个字符
                char c = string.charAt(i);
                // 转换为unicode
                String hexString = Integer.toHexString(c);
                unicode.append("\\u" + hexString);
            }
            return unicode.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {
        if (unicode == null) {
            return unicode;
        }
        String[] hex = unicode.split("\\\\u");
        if (hex == null) {
            return unicode;
        } else {
            StringBuffer string = new StringBuffer();
            for (int i = 0; i < hex.length; i++) {
                try {
                    // 转换出每一个代码点
                    int data = Integer.parseInt(hex[i], 16);
                    // 追加成string
                    string.append((char) data);
                } catch (Exception e) {
                    string.append(hex[i]);
                }

            }
            return string.toString();
        }
    }

    /**
     * 获取url的后缀
     */
    public static String getUrlSuffix(String url) {
        if (isEmpty(url)) {
            return "";
        } else {
            int of = url.lastIndexOf('.');
            String fix = null;
            try {
                fix = url.substring(of);
            } catch (Exception e) {
                return "";
            }
            return fix;
        }
    }

    /**
     * 获取url的中的文件名
     */
    public static String getUrlFileName(String url) {
        if (isEmpty(url)) {
            return "";
        } else {
            int of = url.lastIndexOf('/');
            String fix = url.substring(of + 1);
            return fix;
        }
    }

    /**
     * 汉字排序
     */
    public static List<String> ChineseCharacterSort(List<String> list) {
        Comparator<Object> com = Collator.getInstance(Locale.CHINA);
        Collections.sort(list, com);
        return list;
    }

    /**
     * 把字符串加密
     *
     * @param code 要加密的信息
     * @param seed 加密的密码
     * @return 还原后的信息
     */
    public static String encode(String code, int seed) {
        // 加密密码
        byte[] bytes = code.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] ^= seed;
        }
        String string = new String(bytes);
        return string;
    }

    /**
     * 解密加密的字符串
     *
     * @param code 要加密的信息
     * @param seed 解密的密码
     * @return 还原后的信息
     */
    public static String decode(String code, int seed) {
        return encode(code, seed);
    }


    /**
     * 计算中英文字符串的字节长度
     *
     * @param content
     * @return int
     */
    public static int getCharLength(String content) {
        if (content == null || content.length() == 0) {
            return 0;
        }
        int len = 0;
        char[] chars = content.toCharArray();
        for (char c : chars) {
            //UTF-8编码格式中文占三个字节，GBK编码格式 中文占两个字节 ;
            len += c > 255 ? 2 : 1;
        }
        return len;
    }

    /**
     * 格式化文件大小，不保留末尾的0
     *
     * @param len 大小
     * @return
     */
    public static String formatFileSize(long len) {
        return formatFileSize(len, true);
    }

    /**
     * 格式化文件大小，保留末尾的0，达到长度一致
     *
     * @param len      大小
     * @param keepZero 是否保留小数点
     * @return
     */
    public static String formatFileSize(long len, boolean keepZero) {
        String size;
        DecimalFormat formatKeepTwoZero = new DecimalFormat("#.00");
        DecimalFormat formatKeepOneZero = new DecimalFormat("#.0");
        if (len < 1024) {
            size = len + "B";
        } else if (len < 10 * 1024) {
            // [0, 10KB)，保留两位小数
            size = len * 100 / 1024 / (float) 100 + "KB";
        } else if (len < 100 * 1024) {
            // [10KB, 100KB)，保留一位小数
            size = len * 10 / 1024 / (float) 10 + "KB";
        } else if (len < 1024 * 1024) {
            // [100KB, 1MB)，个位四舍五入
            size = len / 1024 + "KB";
        } else if (len < 10 * 1024 * 1024) {
            // [1MB, 10MB)，保留两位小数
            if (keepZero) {
                size = formatKeepTwoZero.format(len * 100 / 1024 / 1024 / (float) 100) + "MB";
            } else {
                size = len * 100 / 1024 / 1024 / (float) 100 + "MB";
            }
        } else if (len < 100 * 1024 * 1024) {
            // [10MB, 100MB)，保留一位小数
            if (keepZero) {
                size = formatKeepOneZero.format(len * 10 / 1024 / 1024 / (float) 10) + "MB";
            } else {
                size = len * 10 / 1024 / 1024 / (float) 10 + "MB";
            }
        } else if (len < 1024 * 1024 * 1024) {
            // [100MB, 1GB)，个位四舍五入
            size = len / 1024 / 1024 + "MB";
        } else {
            // [1GB, ...)，保留两位小数
            size = len * 100 / 1024 / 1024 / 1024 / (float) 100 + "GB";
        }
        return size;
    }


    /**
     * 格式化音乐时间: 120 000 --> 02:00
     *
     * @param time
     * @return
     */
    public static String formatMusicTime(long time) {
        time = time / 1000;
        String formatTime;
        if (time < 10) {
            formatTime = "00:0" + time;
            return formatTime;
        } else if (time < 60) {
            formatTime = "00:" + time;
            return formatTime;
        } else {
            long i = time / 60;
            if (i < 10) {
                formatTime = "0" + i + ":";
            } else {
                formatTime = i + ":";
            }
            long j = time % 60;
            if (j < 10) {
                formatTime += "0" + j;
            } else {
                formatTime += j;
            }

            return formatTime;
        }
    }

    public static String formatK(int number) {
        if (number < 1000) {
            return String.valueOf(number);
        } else {
            final int i = number / 100;
            final int i1 = i % 10;
            if (i1 == 0) {
                return i + "k";
            } else {
                return join(i, ".", i1, "k");
            }
        }
    }

    /**
     * 检测手机号码
     *
     * @param phone
     */
    public static boolean checkPhone(String phone) {
        if (isEmpty(phone)) {
            return false;
        }
        if (phone.length() < 11) {
            return false;
        }
        if (!phone.startsWith("1")) {
            return false;
        }
        return !phone.startsWith("10") && !phone.startsWith("11") && !phone.startsWith("12");
    }

    /**
     * 检测是否是邮箱
     *
     * @param email
     */
    public static boolean checkEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        return Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*").matcher(email).matches();
    }

    /**
     * 检测是否是邮政编码
     *
     * @param postCode
     */
    public static boolean checkIsPostCode(String postCode) {
        if (isEmpty(postCode)) {
            return false;
        }
        String reg = "[1-9]\\d{5}";
        return Pattern.matches(reg, postCode);
    }


    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * 对象转整
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null) {
            return 0;
        }
        return toInt(obj.toString(), 0);
    }

    public static int toInt(String obj) {
        if (obj == null) {
            return 0;
        }
        return toInt(obj, 0);
    }

    /**
     * String转long
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * String转double
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static double toDouble(String obj) {
        try {
            return Double.parseDouble(obj);
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 字符串转布尔
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBoolean(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 得到文件的名字部分。 实际上就是路径中的最后一个路径分隔符后的部分。
     *
     * @param fileName 文件名
     * @return 文件名中的名字部分
     * @since 0.5
     */
    public static String getNamePart(String fileName) {
        int point = getPathLastIndex(fileName);
        int length = fileName.length();
        if (point == -1) {
            return fileName;
        } else if (point == length - 1) {
            int secondPoint = getPathLastIndex(fileName, point - 1);
            if (secondPoint == -1) {
                if (length == 1) {
                    return fileName;
                } else {
                    return fileName.substring(0, point);
                }
            } else {
                return fileName.substring(secondPoint + 1, point);
            }
        } else {
            return fileName.substring(point + 1);
        }
    }

    /**
     * 得到除去文件名部分的路径 实际上就是路径中的最后一个路径分隔符前的部分。
     *
     * @param fileName 文件路径
     * @return 处理结果
     */
    public static String getNameDelLastPath(String fileName) {
        int point = getPathLastIndex(fileName);
        if (point == -1) {
            return fileName;
        } else {
            return fileName.substring(0, point);
        }
    }

    /**
     * 得到路径分隔符在文件路径中最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
     *
     * @param fileName 文件路径
     * @return 路径分隔符在路径中最后出现的位置，没有出现时返回-1。
     * @since 0.5
     */
    public static int getPathLastIndex(String fileName) {
        int point = fileName.lastIndexOf('/');
        if (point == -1) {
            point = fileName.lastIndexOf('\\');
        }
        return point;
    }

    /**
     * 得到路径分隔符在文件路径中指定位置前最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
     *
     * @param fileName  文件路径
     * @param fromIndex 开始查找的位置
     * @return 路径分隔符在路径中指定位置前最后出现的位置，没有出现时返回-1。
     * @since 0.5
     */
    public static int getPathLastIndex(String fileName, int fromIndex) {
        int point = fileName.lastIndexOf('/', fromIndex);
        if (point == -1) {
            point = fileName.lastIndexOf('\\', fromIndex);
        }
        return point;
    }

    /**
     * 将文件名中的类型部分去掉。不含点
     *
     * @param filename 文件名
     * @return 去掉类型部分的结果
     */
    public static String stringByDeletingPathExtension(String filename) {
        int index = filename.lastIndexOf(".");
        if (index != -1) {
            return filename.substring(0, index);
        } else {
            return filename;
        }
    }

    public static String FileNamePathExtension(String filename) {
        int index = filename.lastIndexOf(".");
        if (index != -1) {
            return filename.substring(index);
        } else {
            return "";
        }
    }

    public static String replaceAll(String text, String regex, String replaceContent) {
        if (!isEmpty(text)) {
            return text.replaceAll(regex, replaceContent);
        }
        return null;
    }

    public static String replaceAll(String text, String regex, char replaceContent) {
        if (!isEmpty(text)) {
            StringBuilder builder = new StringBuilder(text);
            int index = text.indexOf(regex);
            while (index > 0) {
                builder.replace(index, index + regex.length(), String.valueOf(replaceContent));
                index = builder.indexOf(regex);
            }
            return builder.toString();
        }
        return null;
    }

    public static String replaceAll(String text, char regex, String replaceContent) {
        if (!isEmpty(text)) {
            StringBuilder builder = new StringBuilder(text);
            int index = text.indexOf(regex);
            while (index > 0) {
                builder.replace(index, index + 1, replaceContent);
                index = builder.toString().indexOf(regex);
            }
            return builder.toString();
        }
        return null;
    }

    /**
     * 隐藏中间的手机号码
     *
     * @param phoneNumber
     * @param regex
     * @return
     */
    public static String hidePhoneNumber(String phoneNumber, String regex) {
        if (isEmpty(phoneNumber) || phoneNumber.length() < 6) {
            return phoneNumber;
        } else {
            if (phoneNumber.length() > 10) {
                String substring = phoneNumber.substring(3, phoneNumber.length() - 4);
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < substring.length(); i++) {
                    builder.append(regex);
                }
                return phoneNumber.replace(substring, builder.toString());
            } else {
                String substring = phoneNumber.substring(3);
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < substring.length(); i++) {
                    builder.append(regex);
                }
                return phoneNumber.replace(substring, builder.toString());
            }
        }
    }

    /**
     * 隐藏中间的邮箱号码
     *
     * @param email
     * @param regex
     * @return
     */
    public static String hideEmailNumber(String email, String regex) {
        if (isEmpty(email) || email.length() < 6) {
            return email;
        } else {
            String substring = email.substring(2, email.indexOf("@"));
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < substring.length(); i++) {
                builder.append(regex);
            }
            return email.replace(substring, builder);
        }
    }
}
