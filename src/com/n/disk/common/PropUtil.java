package com.n.disk.common;


import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * @author wind
 * @version V1.0
 * @Title: PropUtil
 * @Package com.wind.common
 * @Description: 解析properties文件工具类
 * @date 2018/10/11 9:30
 */
public class PropUtil {

    /**
     * 解析properties文件
     *
     * @param filePath
     * @return
     */
    public static Properties getProp(String filePath) {
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
            return getProp(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Properties();
    }

    /**
     * 解析properties文件
     *
     * @param in
     * @return
     */
    public static Properties getProp(InputStream in) {
        Properties props = new Properties();
        try {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return props;
    }


    /**
     * 写入properties信息
     *
     * @param filepath
     * @param map
     */
    public static void setProp(String filepath, Map<String, String> map) {
        Properties props = getProp(filepath);
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(filepath);
            map.forEach(props::setProperty);
            props.store(fos, "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
