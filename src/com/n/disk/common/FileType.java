package com.n.disk.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FileType {

    /**
     * 得到上传文件的文件头.
     *
     * @param src 传入进来的只需要文件输入流的前十个字节!
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        //遍历字节数组
        for (int i = 0; i < src.length; i++) {
            //进行位与运算
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        //将转换后的十个数字串联在一起就成了一个文件的格式信息(我们看到的是数字,但是这些数字都是有规律的, 你可以每个类型的文件都上传一次,就能看到同类型的文件得到的数字其头部或者尾部是一致的).
        return stringBuilder.toString();
    }

    /**
     * 获取文件类型
     *
     * @param file
     * @return
     */
    public static String getFileType(File file) {
        if (file == null || !file.exists()) {
            return null;
        } else {
            try {
                return getFileType(new FileInputStream(file));
            } catch (Exception e) {
                return null;
            }
        }
    }

    /**
     * 获取文件类型
     *
     * @param file
     * @return
     */
    public static String getFileType(String file) {
        if (file == null) {
            return null;
        } else {
            try {
                return getFileType(new FileInputStream(file));
            } catch (Exception e) {
                return null;
            }
        }
    }

    /**
     * 使用这个工具类时候, 实际上就是调用这个方法即可.
     * 根据指定文件的文件头判断其文件类型---> 上面两个方法都是准备工作,这个才是最好得到文件类型的主方法.
     *
     * @param :MultipartFile multipartFile: 我这里用的是SSM自带的接收上传文件的类. 你可以根据不同的接收进行转变.
     * @return
     */
    public static String getFileType(InputStream is) {
        String res = null;
        try {
            //将接收的上传资源转换成文件输入流.
            //准备数组,固定长度为10,我们只需要前十个.
            byte[] b = new byte[10];
            //读取前十个到数组中去.
            is.read(b, 0, b.length);
            //调用方法,得到该文件的格式数字,说白了, 前面十个字节里存储的就是文件的格式信息,有的在尾部,这个此处不细分,感兴趣的可以百度ID3自己学习.
            String fileCode = bytesToHexString(b).toUpperCase();

            T[] values = T.values();
            for (T value : values) {
                if (value.value.startsWith(fileCode)) {
                    return value.name;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(is);
        }
        return res;
    }

    /**
     * 获取上传的文件类型
     *
     * @param file
     * @return
     */
    public static String getMimeType(String file) {
        if (file == null) {
            return null;
        }
        String type = "*/*";
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = file.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名 */
        String end = file.substring(dotIndex).toLowerCase();
        return MimeType.getMimeType(end);
    }

    public static String getMimeType(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        return getMimeType(file.getName());
    }

    enum T {
        JPG("FFD8FFE000104A464946", "jpg"), //JPEG (jpg)
        PNG("89504E470D0A1A0A0000", "png"), //PNG (png)
        GIF("47494638396126026F01", "gif"), //GIF (gif)
        TIF("49492A00227105008037", "tif"), //TIFF (tif)
        BMP("424D228C010000000000", "bmp"), //16色位图(bmp)
        BMP1("424D8240090000000000", "bmp"), //24位位图(bmp)
        BMP2("424D8E1B030000000000", "bmp"), //256色位图(bmp)
        DWG("41433130313500000000", "dwg"), //CAD (dwg)
        HTMl("3C21444F435459504520", "html"), //HTML (html)
        HTM("3C21646F637479706520", "htm"), //HTM (htm)
        CSS("48544D4C207B0D0A0942", "css"), //css
        JS("696B2E71623D696B2E71", "js"), //js
        RTF("7B5C727466315C616E73", "rtf"), //Rich Text Format (rtf)
        PSD("38425053000100000000", "psd"), //Photoshop (psd)
        EML("46726F6D3A203D3F6762", "eml"), //Email [Outlook Express 6] (eml)
        DOC("D0CF11E0A1B11AE10000", "doc"), //MS Excel 注意：word、msi 和 excel的文件头一样
        VSD("D0CF11E0A1B11AE10000", "vsd"), //Visio 绘图
        MDB("5374616E64617264204A", "mdb"), //MS Access (mdb)
        PS("252150532D41646F6265", "ps"),
        PDF("255044462D312E350D0A", "pdf"), //Adobe Acrobat (pdf)
        RMVB("2E524D46000000120001", "rmvb"), //rmvb/rm相同
        FLV("464C5601050000000900", "flv"), //flv与f4v相同
        MP4("00000020667479706D70", "mp4"),
        MP3("49443303000000002176", "mp3"),
        MPG("000001BA210001000180", "mpg"), //
        WMV("3026B2758E66CF11A6D9", "wmv"), //wmv与asf相同
        WAV("52494646E27807005741", "wav"), //Wave (wav)
        AVI("52494646D07D60074156", "avi"),
        MID("4D546864000000060001", "mid"), //MIDI (mid)
        ZIP("504B0304140000000800", "zip"),
        RAR("526172211A0700CF9073", "rar"),
        INI("235468697320636F6E66", "ini"),
        JAR("504B03040A0000000000", "jar"),
        EXE("4D5A9000030000000400", "exe"),//可执行文件
        JSP("3C25402070616765206C", "jsp"),//jsp文件
        MF("4D616E69666573742D56", "mf"),//MF文件
        XML("3C3F786D6C2076657273", "xml"),//xml文件
        SQL("494E5345525420494E54", "sql"),//xml文件
        JAVA("7061636B616765207765", "java"),//java文件
        BAT("406563686F206F66660D", "bat"),//bat文件
        GZ("1F8B0800000000000000", "gz"),//gz文件
        PROPERTIES("6C6F67346A2E726F6F74", "properties"),
        CLASS("CAFEBABE0000002E0041", "class"),//bat文件
        CHM("49545346030000006000", "chm"),//bat文件
        MXP("04000000010000001300", "mxp"),//bat文件
        DOCX("504B0304140006000800", "docx"),//docx文件
        WPS("D0CF11E0A1B11AE10000", "wps"),//WPS文字wps、表格et、演示dps都是一样的
        TORRENT("6431303A637265617465", "torrent"),
        WPD("FF575043", "wpd"), //WordPerfect (wpd)
        MOV("6D6F6F76", "mov"), //Quicktime (mov)
        DBX("CFAD12FEC5FD746F", "dbx"), //Outlook Express (dbx)
        PST("2142444E", "pst"), //Outlook (pst)
        QDF("AC9EBD8F", "qdf"), //Quicken (qdf)
        PWL("E3828596", "pwl"), //Windows Password (pwl)
        RAM("2E7261FD", "ram"); //Real Audio (ram)
        String value;
        String name;

        T(String value, String name) {
            this.value = value;
            this.name = name;
        }
    }
}
