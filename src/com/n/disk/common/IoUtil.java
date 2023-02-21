package com.n.disk.common;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author wind
 * @version V1.0
 * @Title: IoUtil
 * @Package com.wind.common
 * @Description: io工具类
 * @date 2018/10/11 9:28
 */
public class IoUtil {

    /**
     * 关闭字节输入流
     *
     * @param in
     */
    public static void close(Closeable in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 批量关闭流
     *
     * @param outs
     */
    public static void close(Closeable... outs) {
        Arrays.asList(outs).parallelStream().forEach(IoUtil::close);
    }


}
