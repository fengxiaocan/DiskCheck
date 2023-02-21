package com.n.disk.common;


import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    /**
     * <p>在工具类中经常使用到工具类的格式化描述，这个主要是一个日期的操作类，所以日志格式主要使用 SimpleDateFormat的定义格式.</p>
     * 格式的意义如下： 日期和时间模式 <br>
     * <p>日期和时间格式由日期和时间模式字符串指定。在日期和时间模式字符串中，未加引号的字母 'A' 到 'Z' 和 'a' 到 'z'
     * 被解释为模式字母，用来表示日期或时间字符串元素。文本可以使用单引号 (') 引起来，以免进行解释。"''"
     * 表示单引号。所有其他字符均不解释；只是在格式化时将它们简单复制到输出字符串，或者在分析时与输入字符串进行匹配。
     * </p>
     * 定义了以下模式字母（所有其他字符 'A' 到 'Z' 和 'a' 到 'z' 都被保留）： <br>
     * <table border="1" cellspacing="1" cellpadding="1" summary="Chart shows format letters, date/time component,
     * presentation, and examples.">
     * <tr>
     * <th align="left">字母</th>
     * <th align="left">日期或时间元素</th>
     * <th align="left">表示</th>
     * <th align="left">示例</th>
     * </tr>
     * <tr>
     * <td><code>G</code></td>
     * <td>Era 标志符</td>
     * <td>Text</td>
     * <td><code>AD</code></td>
     * </tr>
     * <tr>
     * <td><code>y</code> </td>
     * <td>年 </td>
     * <td>Year </td>
     * <td><code>1996</code>; <code>96</code> </td>
     * </tr>
     * <tr>
     * <td><code>M</code> </td>
     * <td>年中的月份 </td>
     * <td>Month </td>
     * <td><code>July</code>; <code>Jul</code>; <code>07</code> </td>
     * </tr>
     * <tr>
     * <td><code>w</code> </td>
     * <td>年中的周数 </td>
     * <td>Number </td>
     * <td><code>27</code> </td>
     * </tr>
     * <tr>
     * <td><code>W</code> </td>
     * <td>月份中的周数 </td>
     * <td>Number </td>
     * <td><code>2</code> </td>
     * </tr>
     * <tr>
     * <td><code>D</code> </td>
     * <td>年中的天数 </td>
     * <td>Number </td>
     * <td><code>189</code> </td>
     * </tr>
     * <tr>
     * <td><code>d</code> </td>
     * <td>月份中的天数 </td>
     * <td>Number </td>
     * <td><code>10</code> </td>
     * </tr>
     * <tr>
     * <td><code>F</code> </td>
     * <td>月份中的星期 </td>
     * <td>Number </td>
     * <td><code>2</code> </td>
     * </tr>
     * <tr>
     * <td><code>E</code> </td>
     * <td>星期中的天数 </td>
     * <td>Text </td>
     * <td><code>Tuesday</code>; <code>Tue</code> </td>
     * </tr>
     * <tr>
     * <td><code>a</code> </td>
     * <td>Am/pm 标记 </td>
     * <td>Text </td>
     * <td><code>PM</code> </td>
     * </tr>
     * <tr>
     * <td><code>H</code> </td>
     * <td>一天中的小时数（0-23） </td>
     * <td>Number </td>
     * <td><code>0</code> </td>
     * </tr>
     * <tr>
     * <td><code>k</code> </td>
     * <td>一天中的小时数（1-24） </td>
     * <td>Number </td>
     * <td><code>24</code> </td>
     * </tr>
     * <tr>
     * <td><code>K</code> </td>
     * <td>am/pm 中的小时数（0-11） </td>
     * <td>Number </td>
     * <td><code>0</code> </td>
     * </tr>
     * <tr>
     * <td><code>h</code> </td>
     * <td>am/pm 中的小时数（1-12） </td>
     * <td>Number </td>
     * <td><code>12</code> </td>
     * </tr>
     * <tr>
     * <td><code>m</code> </td>
     * <td>小时中的分钟数 </td>
     * <td>Number </td>
     * <td><code>30</code> </td>
     * </tr>
     * <tr>
     * <td><code>s</code> </td>
     * <td>分钟中的秒数 </td>
     * <td>Number </td>
     * <td><code>55</code> </td>
     * </tr>
     * <tr>
     * <td><code>S</code> </td>
     * <td>毫秒数 </td>
     * <td>Number </td>
     * <td><code>978</code> </td>
     * </tr>
     * <tr>
     * <td><code>z</code> </td>
     * <td>时区 </td>
     * <td>General time zone </td>
     * <td><code>Pacific Standard Time</code>; <code>PST</code>; <code>GMT-08:00</code> </td>
     * </tr>
     * <tr>
     * <td><code>Z</code> </td>
     * <td>时区 </td>
     * <td>RFC 822 time zone </td>
     * <td><code>-0800</code> </td>
     * </tr>
     * </table>
     * <pre>
     *                                             HH:mm    15:44
     *                                            h:mm a    3:44 下午
     *                                           HH:mm z    15:44 CST
     *                                           HH:mm Z    15:44 +0800
     *                                        HH:mm zzzz    15:44 中国标准时间
     *                                          HH:mm:ss    15:44:40
     *                                        yyyy-MM-dd    2016-08-12
     *                                  yyyy-MM-dd HH:mm    2016-08-12 15:44
     *                               yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
     *                          yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
     *                     EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
     *                          yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
     *                        yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     *                      yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
     *                                            K:mm a    3:44 下午
     *                                  EEE, MMM d, ''yy    星期五, 八月 12, '16
     *                             hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
     *                      yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
     *                        EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
     *                                     yyMMddHHmmssZ    160812154440+0800
     *                        yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
     * </pre>
     * 注意：SimpleDateFormat不是线程安全的，线程安全需用{@code ThreadLocal<SimpleDateFormat>}
     */
    /**
     * 格式:2016-08-20 11:11:11
     */
    public static final String DATE_TYPE1 = "yyyy-MM-dd HH:mm:ss";
    /**
     * 格式:2016-08-20 Tuesday
     */
    public static final String DATE_TYPE2 = "yyyy-MM-dd E";
    /**
     * 格式:2016-08-20
     */
    public static final String DATE_TYPE3 = "yyyy-MM-dd";

    /**
     * 格式:2016年 第181天
     */
    public static final String DATE_TYPE4 = "yyyy年 第D天";
    /**
     * 格式:12:24:36
     */
    public static final String DATE_TYPE5 = "HH:mm:ss";
    /**
     * 格式:2016年6月19日 星期日
     */
    public static final String DATE_TYPE6 = "yyyy年MM月dd日 E";
    /**
     * 格式:2015年12月23日
     */
    public static final String DATE_TYPE7 = "yyyy年MM月dd日";
    /**
     * 格式:2015年12月23日 星期日 下午
     */
    public static final String DATE_TYPE8 = "yyyy年MM月dd日 E a";
    /**
     * 格式:12:23:34 星期日 下午
     */
    public static final String DATE_TYPE9 = "HH:mm:ss E a";
    /**
     * 格式:21时12分18秒
     */
    public static final String DATE_TYPE10 = "HH时mm分ss秒";
    /**
     * 格式:20150223
     */
    public static final String DATE_TYPE11 = "yyyyMMdd";
    /**
     * 格式:20150213 211002
     */
    public static final String DATE_TYPE12 = "yyyyMMdd HHmmss";
    /**
     * 格式:2-23
     */
    public static final String DATE_TYPE13 = "MM-dd";
    /**
     * 格式:12:24
     */
    public static final String DATE_TYPE14 = "HH:mm";
    /**
     * 格式:12.20
     */
    public static final String DATE_TYPE15 = "MM.dd";

    /**
     * 格式:2016-08-20 11:11
     */
    public static final String DATE_TYPE16 = "yyyy-MM-dd HH:mm";

    /**
     * 格式:08-20  22:23
     */
    public static final String DATE_TYPE17 = "MM-dd  HH:mm";
    /**
     * 格式:16-08-20
     */
    public static final String DATE_TYPE18 = "yy-MM-dd";
    /**
     * 格式:2016-08-20 11:11:11
     */
    public static final String DATE_TYPE19 = "yyyy-MM-dd HH:mm:ss";
    /**
     * 格式:20160820-11:11:11
     */
    public static final String DATE_TYPE20 = "yyyyMMdd-HH:mm:ss";
    /**
     * 格式:20160820111111
     */
    public static final String DATE_TYPE21 = "yyyyMMddHHmmss";
    /**
     * 格式:2016_08_20_11:11:11
     */
    public static final String DATE_TYPE22 = "yyyy_MM_dd_HH:mm:ss";

    public static final int SEC = 1000;
    public static final int MIN = 60 * SEC;
    public static final int HOUR = 60 * MIN;
    public static final int DAY = 24 * HOUR;
    public static final int WEEK = 7 * DAY;

    /**
     * 获取当前时间
     *
     * @return 当前时间的毫秒值
     */
    public static long getNowTime() {
        return System.currentTimeMillis();
    }

    /**
     * 判断是否是今年
     *
     * @param time
     * @return
     */
    public static boolean isThisYear(long time) {
        return getYear(time) == getYear(getNowTime());
    }

    /**
     * 判断是否是今年
     *
     * @param time
     * @return
     */
    public static boolean isThisYear(Date time) {
        return getYear(time) == getYear(getNowTime());
    }

    /**
     * 获取当前时间
     *
     * @param type 时间的格式
     * @return 返回格式化后的时间
     */
    public static String getNowTime(String type) {
        return formatTime(getNowTime(), type);
    }

    /**
     * 根据传入的格式来格式化时间
     *
     * @param date 时间 单位:毫秒
     * @param type 日期格式
     * @return 传入格式类型的时间字符串
     */
    public static String formatTime(long date, String type) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(type);
            Date da = new Date(date);
            String time = sdf.format(da);
            return time;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(long date1, long date2) {
        Calendar cal1 = Calendar.getInstance();
        Date da1 = new Date(date1);
        cal1.setTime(da1);

        Calendar cal2 = Calendar.getInstance();
        Date da2 = new Date(date2);
        cal2.setTime(da2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    /**
     * 传入对应的时间字符串和对应的时间格式,转化为long类型的时间毫秒值
     *
     * @param date 例如:2016-02-23
     * @param type 则选择对应的 DATE_TYPE2 格式
     */
    public static long formatTime(String date, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        try {
            Date parse = sdf.parse(date);
            long time = parse.getTime();
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将时间字符串 转化为 对应格式的 时间Date
     *
     * @param strDate 要转化的时间字符串
     * @param type    时间字符串对应的格式类型
     * @return Date类型的时间格式
     * @throws ParseException 格式转换异常
     */
    public static Date formatToDate(String strDate, String type) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(type);
            Date parse = formatter.parse(strDate);
            return parse;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把Date时间转换为指定格式的时间字符串
     *
     * @param date 要转化的时间Date
     * @param type 转化为目标格式时间类型
     * @return 返回指定格式的字符串时间
     */
    public static String formatDate(Date date, String type) {
        SimpleDateFormat formatter = new SimpleDateFormat(type);
        String string = formatter.format(date);
        return string;
    }

    /**
     * 得到现在小时
     *
     * @return int 类型的现在小时数
     */
    public static int getNowHour() {
        Date date = new Date(getNowTime());
        int hours = date.getHours();
        return hours;
    }

    /**
     * 得到现在分钟数
     *
     * @return int 类型的现在分钟数
     */
    public static int getNowMinutes() {
        Date date = new Date(getNowTime());
        int minutes = date.getMinutes();
        return minutes;
    }

    /**
     * 得到现在秒数
     *
     * @return int 类型的现在秒数
     */
    public static int getNowSeconds() {
        Date date = new Date(getNowTime());
        int seconds = date.getSeconds();
        return seconds;
    }

    /**
     * 得到现在是一个星期内的第几天
     *
     * @return int 类型的现在星期几的天数 0为星期天
     */
    public static int getNowDay() {
        Date date = new Date(getNowTime());
        int day = date.getDay();
        return day;
    }

    /**
     * 得到现在星期几
     *
     * @return String 类型的现在星期几的天数:星期几
     */
    public static String getNowWeek() {
        return getWeek(getNowDate());
    }

    /**
     * 得到传入的时间是一个月内的第几天
     *
     * @return int 一个月内的第几天
     */
    public static int getDate(long time) {
        return getDate(new Date(time));
    }

    /**
     * 得到传入的时间是一个月内的第几天
     *
     * @return int 一个月内的第几天
     */
    public static int getDate(Date date) {
        int dates = date.getDate();
        return dates;
    }

    /**
     * 获得今天是一个月内的第几天
     *
     * @return int 一个月内的第几天
     */
    public static int getNowDateInMonth() {
        Date date = new Date(getNowTime());
        int dates = date.getDate();
        return dates;
    }

    public static Date getNowDate() {
        return new Date(getNowTime());
    }

    /**
     * 得到现在是一年内第几个月
     *
     * @return int 现在的一年内第几个月 1为第一个月
     */
    public static int getNowMonth() {
        Date date = new Date(getNowTime());
        int month = date.getMonth() + 1;
        return month;
    }

    /**
     * 得到传入的时间是一年内第几个月
     *
     * @return int 现在的一年内第几个月 1为第一个月
     */
    public static int getMonth(long time) {
        Date date = new Date(time);
        int month = date.getMonth() + 1;
        return month;
    }

    /**
     * 得到现在的第几年
     *
     * @return int 现在是哪一年
     */
    public static int getNowYear() {
        Date date = new Date(getNowTime());
        int year = date.getYear();
        year = year + 1900;
        return year;
    }

    /**
     * 得到Date第几年
     *
     * @return int Date是哪一年
     */
    public static int getYear(Date date) {
        int year = date.getYear();
        year = year + 1900;
        return year;
    }

    /**
     * 得到Date第几年
     *
     * @return int Date是哪一年
     */
    public static int getYear(long time) {
        Date date = new Date(time);
        int year = date.getYear();
        year = year + 1900;
        return year;
    }

    /**
     * 格式化时间状态 比如 1239182398,返回的时间格式是 32分钟前
     */
    public static String formatTimeStatus(long time, String type) {
        long timeMillis = System.currentTimeMillis();
        long dTime = timeMillis - time;

        if (dTime <= 0) {
            return "刚刚";
        }

        dTime = dTime / 1000;

        if (dTime > 0 && dTime < 60) {
            return dTime + "秒前";
        }

        dTime = dTime / 60;

        if (dTime >= 0 && dTime < 60) {
            return dTime + "分钟前";
        }

        dTime = dTime / 60;

        if (dTime >= 0 && dTime <= 24) {
            return dTime + "小时前";
        }
        dTime = dTime / 24;

        if (dTime > 0 && dTime <= 7) {
            return dTime + "天前";
        }

        dTime = dTime / 7;
        if (dTime > 0 && dTime <= 4) {
            return dTime + "周前";
        }

        return formatTime(time, type);
    }

    public static String formatTimeStatus(long time) {
        long timeMillis = System.currentTimeMillis();
        long dTime = timeMillis - time;

        if (dTime <= 0) {
            return "刚刚";
        }

        dTime = dTime / 1000;

        if (dTime > 0 && dTime < 60) {
            return dTime + "秒前";
        }

        dTime = dTime / 60;

        if (dTime >= 0 && dTime < 60) {
            return dTime + "分钟前";
        }

        dTime = dTime / 60;

        if (dTime >= 0 && dTime <= 24) {
            return dTime + "小时前";
        }
        dTime = dTime / 24;

        if (dTime > 0 && dTime <= 7) {
            return dTime + "天前";
        }
        dTime = dTime / 7;
        if (dTime > 0 && dTime <= 4) {
            return dTime + "周前";
        }
        if (dTime > 0 && dTime < 52) {
            long l = dTime / 4;
            return l + "个月前";
        }
        dTime = dTime / 52;
        return dTime + "年前";
    }

    /**
     * 得到年月
     *
     * @return 2018 August
     */
    public static String formatYearMonth(long time) {
        Date date = new Date(time);
        int year = date.getYear();
        year = year + 1900;
        String s = StringUtil.join(year, " ", formatMonth(date, new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
        return s;
    }

    /**
     * 获取月日: 11.11
     */
    public static String formatMonthDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int month = calendar.get(Calendar.MONTH) + 1;  //获取月;
        int date = calendar.get(Calendar.DATE);   //获取天;
        return StringUtil.join(month, ".", date);
    }

    /**
     * 得到传入的时间是一年内第几个月
     *
     * @return int 现在的一年内第几个月一为第一个月
     */
    public static String formatMonth(long time) {
        return formatMonth(time, new String[]{"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"});
    }

    /**
     * 得到传入的时间是一年内第几个月
     *
     * @return int 现在的一年内第几个月一为第一个月
     */
    public static String formatMonth(Date date, String[] moth) {
        int month = date.getMonth();
        return moth[month];
    }

    /**
     * 得到传入的时间是一年内第几个月
     *
     * @return int 现在的一年内第几个月一为第一个月
     */
    public static String formatMonth(long time, String[] moth) {
        Date date = new Date(time);
        return formatMonth(date, moth);
    }

    /**
     * 得到传入的时间是一年内第几个月
     *
     * @return int 现在的一年内第几个月一为第一个月
     */
    public static String formatEnglishMonth(long time) {
        return formatMonth(time, new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
    }

    /**
     * 得到传入的时间是一年内第几个月
     *
     * @return int 现在的一年内第几个月一为第一个月
     */
    public static String formatSimpleEnglishMonth(long time) {
        return formatMonth(time, new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"});
    }


    /**
     * 提取一个月中的最后一天
     */
    public static Date getLastDate(long day) {
        Date date = new Date();
        long date_3_hm = date.getTime() - 3600000 * 34 * day;
        Date date_3_hm_date = new Date(date_3_hm);
        return date_3_hm_date;
    }


    /**
     * 产生周序列,即得到当前时间所在的年度是第几周
     *
     * @return
     */
    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1) {
            week = "0" + week;
        }
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + week;
    }

    /**
     * 获取字符串日期是星期几
     *
     * @param sDate 2015年2月23日
     * @return 返回是星期几的字符串
     */
    public static String getWeek(String sDate, String type) {
        Date date = formatToDate(sDate, type);
        return getWeek(date);
    }

    public static String getWeek(Date date) {
        int week = date.getDay();
        if (week == 0) {
            return "星期日";
        } else if (week == 1) {
            return "星期一";
        } else if (week == 2) {
            return "星期二";
        } else if (week == 3) {
            return "星期三";
        } else if (week == 4) {
            return "星期四";
        } else if (week == 5) {
            return "星期五";
        } else if (week == 6) {
            return "星期六";
        } else {
            return "";
        }
    }

    public static String getWeek(long date) {
        return getWeek(new Date(date));
    }

    /**
     * 两个时间之间的天数
     */
    public static long intervalDays(String date1, String date2, String type) {
        if (date1 == null || date1.equals("")) {
            return 0;
        }
        if (date2 == null || date2.equals("")) {
            return 0;
        }
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat(type);
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (DAY);
        return day;
    }


    /**
     * 判断是否今天
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isToday(String time) {
        return isToday(formatTime(time, DATE_TYPE1));
    }

    /**
     * 判断是否今天
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isToday(String time, String format) {
        return isToday(formatTime(time, format));
    }

    /**
     * 判断是否昨天
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isYesterday(String time, String format) {
        return isYesterday(formatTime(time, format));
    }

    /**
     * 判断是否今天
     *
     * @param date Date类型时间
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isToday(Date date) {
        return isToday(date.getTime());
    }

    /**
     * 判断是否昨天
     *
     * @param date Date类型时间
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isYesterday(Date date) {
        return isYesterday(date.getTime());
    }

    //判断选择的日期是否是本周
    public static boolean isThisWeek(long time) {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(new Date(time));
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        return paramWeek == currentWeek;
    }

    //判断选择的日期是否是今天
    public static boolean isToday(long time) {
        return isThisTime(time, DATE_TYPE3);
    }

    //判断选择的日期是否是明天
    public static boolean isTomorrow(long time) {
        return isThisTime(time - DAY, DATE_TYPE3);
    }

    //判断选择的日期是否是后天
    public static boolean isAfterTomorrow(long time) {
        return isThisTime(time - DAY * 2, DATE_TYPE3);
    }

    //判断选择的日期是否是本月
    public static boolean isThisMonth(long time) {
        return isThisTime(time, "yyyy-MM");
    }

    private static boolean isThisTime(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);//参数时间
        String now = sdf.format(new Date(System.currentTimeMillis()));//当前时间
        return param.equals(now);
    }


    /**
     * 判断是否昨天
     *
     * @param millis 毫秒时间戳
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isYesterday(long millis) {
        return isThisTime(millis + DAY, DATE_TYPE3);
    }

    /**
     * 判断是否润年
     *
     * @param date
     * @return
     */
    public static boolean isLeapYear(String date, String type) {
        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */
        Date d = formatToDate(date, type);
        return isLeapYear(d);
    }


    /**
     * 判断是否闰年
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(String time) {
        return isLeapYear(formatTime(time, DATE_TYPE1));
    }


    /**
     * 判断是否闰年
     *
     * @param date Date类型时间
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    /**
     * 判断是否闰年
     *
     * @param millis 毫秒时间戳
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(long millis) {
        return isLeapYear(new Date(millis));
    }

    /**
     * 判断是否闰年
     *
     * @param year 年份
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 获取中式星期
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 中式星期
     */
    public static String getChineseWeek(String time) {
        return getChineseWeek(formatTime(time, DATE_TYPE1));
    }

    /**
     * 获取中式星期
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 中式星期
     */
    public static String getChineseWeek(String time, String format) {
        return getChineseWeek(formatTime(time, format));
    }

    /**
     * 获取中式星期
     *
     * @param date Date类型时间
     * @return 中式星期
     */
    public static String getChineseWeek(Date date) {
        return new SimpleDateFormat("E", Locale.CHINA).format(date);
    }

    /**
     * 获取中式星期
     *
     * @param millis 毫秒时间戳
     * @return 中式星期
     */
    public static String getChineseWeek(long millis) {
        return getChineseWeek(new Date(millis));
    }

    /**
     * 获取美式星期
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 美式星期
     */
    public static String getUSWeek(String time) {
        return getUSWeek(formatTime(time, DATE_TYPE1));
    }

    /**
     * 获取美式星期
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 美式星期
     */
    public static String getUSWeek(String time, String format) {
        return getUSWeek(formatTime(time, format));
    }

    /**
     * 获取美式星期
     *
     * @param date Date类型时间
     * @return 美式星期
     */
    public static String getUSWeek(Date date) {
        return new SimpleDateFormat("EEEE", Locale.US).format(date);
    }

    /**
     * 获取美式星期
     *
     * @param millis 毫秒时间戳
     * @return 美式星期
     */
    public static String getUSWeek(long millis) {
        return getUSWeek(new Date(millis));
    }

    /**
     * 获取星期索引
     * <p>注意：周日的Index才是1，周六为7</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...7
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     */
    public static int getWeekIndex(String time) {
        return getWeekIndex(formatTime(time, DATE_TYPE1));
    }

    /**
     * 获取星期索引
     * <p>注意：周日的Index才是1，周六为7</p>
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 1...7
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     */
    public static int getWeekIndex(String time, String format) {
        return getWeekIndex(formatTime(time, format));
    }

    /**
     * 获取星期索引
     * <p>注意：周日的Index才是1，周六为7</p>
     *
     * @param date Date类型时间
     * @return 1...7
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     */
    public static int getWeekIndex(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取星期索引
     * <p>注意：周日的Index才是1，周六为7</p>
     *
     * @param millis 毫秒时间戳
     * @return 1...7
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     */
    public static int getWeekIndex(long millis) {
        return getWeekIndex(new Date(millis));
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...5
     */
    public static int getWeekOfMonth(String time) {
        return getWeekOfMonth(formatTime(time, DATE_TYPE1));
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 1...5
     */
    public static int getWeekOfMonth(String time, String format) {
        return getWeekOfMonth(formatTime(time, format));
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param date Date类型时间
     * @return 1...5
     */
    public static int getWeekOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param millis 毫秒时间戳
     * @return 1...5
     */
    public static int getWeekOfMonth(long millis) {
        return getWeekOfMonth(new Date(millis));
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...54
     */
    public static int getWeekOfYear(String time) {
        return getWeekOfYear(formatTime(time, DATE_TYPE1));
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 1...54
     */
    public static int getWeekOfYear(String time, String format) {
        return getWeekOfYear(formatTime(time, format));
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param date Date类型时间
     * @return 1...54
     */
    public static int getWeekOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param millis 毫秒时间戳
     * @return 1...54
     */
    public static int getWeekOfYear(long millis) {
        return getWeekOfYear(new Date(millis));
    }

    /**
     * 获取生肖
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 生肖
     */
    public static String getChineseZodiac(String time) {
        return getChineseZodiac(formatTime(time, DATE_TYPE1));
    }

    /**
     * 获取生肖
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 生肖
     */
    public static String getChineseZodiac(String time, String format) {
        return getChineseZodiac(formatTime(time, format));
    }

    /**
     * 获取生肖
     *
     * @param date Date类型时间
     * @return 生肖
     */
    public static String getChineseZodiac(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String[] strings = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};
        return strings[cal.get(Calendar.YEAR) % 12];
    }

    /**
     * 获取生肖
     *
     * @param millis 毫秒时间戳
     * @return 生肖
     */
    public static String getChineseZodiac(long millis) {
        return getChineseZodiac(new Date(millis));
    }

    /**
     * 获取生肖
     *
     * @param year 年
     * @return 生肖
     */
    public static String getChineseZodiac(int year) {
        String[] strings = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};
        return strings[year % 12];
    }

    /**
     * 获取星座
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 生肖
     */
    public static String getZodiac(String time) {
        return getZodiac(formatTime(time, DATE_TYPE1));
    }

    /**
     * 获取星座
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 生肖
     */
    public static String getZodiac(String time, String format) {
        return getZodiac(formatTime(time, format));
    }

    /**
     * 获取星座
     *
     * @param date Date类型时间
     * @return 星座
     */
    public static String getZodiac(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return getZodiac(month, day);
    }

    /**
     * 获取星座
     *
     * @param millis 毫秒时间戳
     * @return 星座
     */
    public static String getZodiac(long millis) {
        return getZodiac(new Date(millis));
    }

    /**
     * 获取星座
     *
     * @param month 月
     * @param day   日
     * @return 星座
     */
    public static String getZodiac(int month, int day) {
        return new String[]{"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"}[day >= new int[]{20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22}[month - 1] ? month - 1 : (month + 10) % 12];
    }

    /**
     * 获取任意时间后num天的时间
     *
     * @param date java.util.Date
     * @param num
     * @return
     */
    public static Date nextDate(Date date, int num) {
        Calendar cla = Calendar.getInstance();
        cla.setTime(date);
        cla.add(Calendar.DAY_OF_YEAR, num);
        return cla.getTime();
    }

    /**
     * 获取任意时间后num天的时间
     *
     * @param date String; <br>
     *             格式支持�?<br>
     *             yyyy-MM-dd HH:mm:ss <br>
     *             yyyy年MM月dd日HH时mm分ss�?<br>
     *             yyyy/MM/dd HH:mm:ss <br>
     *             默认时间格式
     * @param num  int
     * @return java.util.Date
     * @throws ParseException
     */
    public static Date nextDate(String date, int num) throws ParseException {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = null;
        if (date.indexOf("-") != -1) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if (date.indexOf("-") != -1) {
            sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss");
        } else if (date.indexOf("/") != -1) {
            sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        } else if (date.indexOf("CST") != -1) {
            sdf = new SimpleDateFormat();
        } else {
            System.out.println("no match format:");
        }
        return nextDate(sdf.parse(date), num);
    }

    /**
     * 获取当天时间num天后的时间<br>
     * 如果num小于0则返回当前时间的前num天的时间<br>
     * ，否则返回当天时间后num天的时间
     *
     * @param num int;
     * @return java.util.Date
     */
    public static Date nextDate(int num) {
        return nextDate(new Date(), num);
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long getTime() {
        try {
            URL url = new URL("http://www.bjtime.cn");
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect(); // 发出连接
            return uc.getDate(); // 取得网站日期时间
        } catch (Exception e) {
            e.printStackTrace();
            return getNowTime();
        }
    }
}