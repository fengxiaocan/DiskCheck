package com.n.disk.common;

import java.io.*;

public class StreamUtils {

    /**
     * 将输入流写入文件
     *
     * @param is 输入流
     * @param os 输入流
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static long inputToOutput(InputStream is, OutputStream os, byte[] data) throws IOException {
        if (os == null || is == null) {
            return 0;
        }
        long total = 0;
        int len;
        while ((len = is.read(data, 0, data.length)) != -1) {
            total += len;
            os.write(data, 0, len);
        }
        return total;
    }

    /**
     * 将输入流写入文件
     *
     * @param is 输入流
     * @param os 输入流
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static long inputToOutput(InputStream is, OutputStream os) throws IOException {
        return inputToOutput(is, os, new byte[1024]);
    }
    /**
     * 将输入流写入文件
     *
     * @param is 输入流
     * @param os 输入流
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static long tryCatchInputToOutput(InputStream is, OutputStream os) throws IOException {
        try {
            return inputToOutput(is, os, new byte[1024]);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }finally {
            IoUtil.close(is,os);
        }
    }

    /**
     * inputStream转outputStream
     *
     * @param is 输入流
     * @return outputStream子类
     */
    public static ByteArrayOutputStream input2OutputStream(InputStream is) {
        if (is == null) {
            return null;
        }
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 8];
            int len;
            while ((len = is.read(b, 0, 1024 * 8)) != -1) {
                os.write(b, 0, len);
            }
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            IoUtil.close(is);
        }
    }

    /**
     * inputStream转byteArr
     *
     * @param is 输入流
     * @return 字节数组
     */
    public static byte[] inputStream2Bytes(InputStream is) {
        if (is == null) {
            return null;
        }
        ByteArrayOutputStream outputStream = input2OutputStream(is);
        byte[] bytes = outputStream.toByteArray();
        IoUtil.close(outputStream);
        return bytes;
    }

    /**
     * byteArr转inputStream
     *
     * @param bytes 字节数组
     * @return 输入流
     */
    public static InputStream bytes2InputStream(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        return new ByteArrayInputStream(bytes);
    }

    /**
     * outputStream转byteArr
     *
     * @param out 输出流
     * @return 字节数组
     */
    public static byte[] outputStream2Bytes(OutputStream out) {
        if (out == null) {
            return null;
        }
        byte[] bytes = ((ByteArrayOutputStream) out).toByteArray();
        IoUtil.close(out);
        return bytes;
    }

    /**
     * outputStream转byteArr
     *
     * @param bytes 字节数组
     * @return 字节数组
     */
    public static OutputStream bytes2OutputStream(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            os.write(bytes);
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            IoUtil.close(os);
        }
    }

    /**
     * inputStream转string按编码
     *
     * @param is          输入流
     * @param charsetName 编码格式
     * @return 字符串
     */
    public static String inputStream2String(InputStream is, String charsetName) {
        if (is == null || StringUtil.isEmpty(charsetName)) {
            return null;
        }
        try {
            return new String(inputStream2Bytes(is), charsetName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IoUtil.close(is);
        }
    }

    /**
     * string转inputStream按编码
     *
     * @param string      字符串
     * @param charsetName 编码格式
     * @return 输入流
     */
    public static InputStream string2InputStream(String string, String charsetName) {
        if (string == null || StringUtil.isEmpty(charsetName)) {
            return null;
        }
        try {
            return new ByteArrayInputStream(string.getBytes(charsetName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * outputStream转string按编码
     *
     * @param out         输出流
     * @param charsetName 编码格式
     * @return 字符串
     */
    public static String outputStream2String(OutputStream out, String charsetName) {
        if (out == null || StringUtil.isEmpty(charsetName)) {
            return null;
        }
        try {
            return new String(outputStream2Bytes(out), charsetName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IoUtil.close(out);
        }
    }

    /**
     * string转outputStream按编码
     *
     * @param string      字符串
     * @param charsetName 编码格式
     * @return 输入流
     */
    public static OutputStream string2OutputStream(String string, String charsetName) {
        if (string == null || StringUtil.isEmpty(charsetName)) {
            return null;
        }
        try {
            return bytes2OutputStream(string.getBytes(charsetName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * outputStream转inputStream
     *
     * @param out 输出流
     * @return inputStream子类
     */
    public ByteArrayInputStream output2InputStream(OutputStream out) {
        if (out == null) {
            return null;
        }
        return new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
    }
}
