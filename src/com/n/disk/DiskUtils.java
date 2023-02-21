package com.n.disk;

import com.n.disk.common.FileUtils;
import com.n.disk.common.IoUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class DiskUtils {

    //1MB的块
    public static final String DISK_HEADER = "DISK_TEMP_TEST";
    public static boolean isRunning = true;

    public static List<PlateInfo> getDisk() {
        String osName = getOsName();
        List<PlateInfo> list = new ArrayList<>();
        if (osName.contains("win")) {

        } else if (osName.contains("mac")) {
            File[] files = new File("/Volumes").listFiles();
            for (File file : files) {
                if (!file.getName().toLowerCase().startsWith("macintosh")) {
                    list.add(new PlateInfo(file.getAbsolutePath()));
                }
            }
        }
        return list;
    }

    public static String getOsName() {
        return System.getProperty("os.name").toLowerCase();
    }

    /**
     * 清空U盘
     *
     * @param platePath
     * @return
     */
    public static boolean clearPlate(String platePath) {
        return FileUtils.deleteDir(new File(platePath));
    }

    /**
     * 删除测试使用的临时文件夹
     *
     * @param platePath
     * @return
     */
    public static int deleteDiskTempFile(String platePath) {
        File file = new File(platePath);
        File[] files = file.listFiles();
        int count = 0;
        for (File file1 : files) {
            if (file1.getName().startsWith(DISK_HEADER)) {
                count++;
                file1.delete();
            }
        }
        return count;
    }

    /**
     * 快速写入数据
     *
     * @param file
     * @param data
     * @throws IOException
     */
    public static void writeDataToFile(File file, byte[] data) throws IOException {
        FileOutputStream is = new FileOutputStream(file);
        is.write(data);
    }

    /**
     * 创建一个固定大小的文件
     *
     * @param file
     * @param length
     */
    public static void createFixedFile(File file, long length) throws Exception {
        FileOutputStream fos = null;
        FileChannel output = null;
        try {
            fos = new FileOutputStream(file);
            output = fos.getChannel();
            output.write(ByteBuffer.allocate(1), length - 1);
        } finally {
            IoUtil.close(fos, output);
        }
    }

    /**
     * 创建一个固定大小的文件
     *
     * @param file
     * @param length
     */
    public static void createFixedFile2(File file, long length){
        try (RandomAccessFile accessFile = new RandomAccessFile(file, "rw")) {
            accessFile.setLength(length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据填充文件的使用个数计算U盘容量
     *
     * @param count_block_16MB
     * @param count_block_1MB
     * @return U盘容量 单位 GB
     */
    private static double computeCapacity(int count_block_16MB, int count_block_1MB) {
        return (16 * count_block_16MB + 1 * count_block_1MB) / 1024.0;
    }

    /**
     * 获取U盘容量空余容量
     *
     * @param platePath U盘的路径 例如 E://
     * @return 商家标注的U盘容量 单位GB
     */
    public static long getPlateFreeSpace(String platePath) {
        return new File(platePath).getFreeSpace();
    }

    /**
     * 获取U盘已使用的容量
     *
     * @param platePath U盘的路径 例如 E://
     * @return 商家标注的U盘容量 单位GB
     */
    public static long getPlateUsableSpace(String platePath) {
        return new File(platePath).getUsableSpace();
    }

    /**
     * 获取商家标注的U盘容量
     *
     * @param platePath U盘的路径 例如 E://
     * @return 商家标注的U盘容量 单位GB
     */
    public static long getPlateTotalSpace(String platePath) {
        return new File(platePath).getTotalSpace();
    }

    /**
     * 停止U盘检测
     */
    public static void stopPlateListener() {
        isRunning = false;
    }

    /**
     * 检测U盘插入、拔出
     * 不能用return返回插入、拔出的U盘信息，因为需要这个方法一直运行
     */
    public static void startPlateListener(OnPlateChangeListener listener) {
        isRunning = true;
        new Thread(() -> {
            int count = 0;
            while (isRunning) {
                List<PlateInfo> list = getDisk();
                if (count != list.size()) {
                    listener.onPlateChange(list);
                    count = list.size();
                }
                try {
                    Thread.sleep(500);//为了避免一直检测消耗资源
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();
    }

    public interface OnPlateChangeListener {
        void onPlateChange(List<PlateInfo> list);
    }
}
