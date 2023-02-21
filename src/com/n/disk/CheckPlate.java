package com.n.disk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

public class CheckPlate {
    public static final int CHECK_ERROR_CODE = 1;
    public static final int READ_ERROR_CODE = 2;
    public static final int WRITE_ERROR_CODE = 3;
    private final String platePath;
    private final long blockSize;
    private final int bufferSize;
    private final OnProgressListener listener;
    private volatile long startTime = 0;
    private volatile boolean isRunning = false;
    private volatile long speedBuffer = 0;
    private volatile long dataBuffer = 0;
    private Thread taskSpeed = new Thread(() -> {
        while (isRunning) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            speedCalculate();
        }
    });

    /**
     * @param platePath U盘路径
     * @param byteSize  读写的块的大小
     * @param listener  回调
     */
    public CheckPlate(String platePath, long byteSize, int bufferSize, OnProgressListener listener) {
        this.platePath = platePath;
        this.blockSize = byteSize;
        this.bufferSize = bufferSize;
        this.listener = listener;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void start() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        taskSpeed.start();

        new Thread(() -> {
            byte[] dataArray = new byte[1024 * bufferSize];
            Arrays.fill(dataArray, (byte) 1);
            startTime = System.currentTimeMillis();
            speedBuffer = 0;

            dataBuffer = 0;

            listener.onWriting();

            while (isRunning && DiskUtils.getPlateFreeSpace(platePath) > blockSize) {
                String name = DiskUtils.DISK_HEADER + System.currentTimeMillis();
                File file = new File(platePath, name);
                int total = 0;
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    while (isRunning && total < blockSize) {
                        synchronized (Object.class) {
                            int len = (blockSize - total) / dataArray.length > 0 ? dataArray.length : (int) ((blockSize - total) % dataArray.length);
                            total += len;
                            speedBuffer += len;
                            dataBuffer += len;
                            fos.write(dataArray, 0, len);
                            fos.flush();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onError(WRITE_ERROR_CODE, "U盘读写操作失败:" + e.getMessage());
                    stop();
                    return;
                }
            }

            if (isRunning) {
                final long freeSpace = DiskUtils.getPlateFreeSpace(platePath);
                if (freeSpace > 1) {
                    String name = DiskUtils.DISK_HEADER + System.currentTimeMillis();
                    File file = new File(platePath, name);
                    int total = 0;
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        long lastSpace = freeSpace - 1;

                        while (isRunning && total < lastSpace) {
                            synchronized (Object.class) {
                                int len = (lastSpace - total) / dataArray.length > 0 ? dataArray.length : (int) ((lastSpace - total) % dataArray.length);
                                total += len;
                                speedBuffer += len;
                                dataBuffer += len;
                                fos.write(dataArray, 0, len);
                                fos.flush();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onError(WRITE_ERROR_CODE, "U盘读写操作失败:" + e.getMessage());
                        stop();
                        return;
                    }
                }
            }

            dataBuffer = 0;
            listener.onChecking();

            File[] files = new File(platePath).listFiles((dir, name) -> name.startsWith(DiskUtils.DISK_HEADER));
            startTime = System.currentTimeMillis();
            if (isRunning) {
                for (File file : files) {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        int len = 0;
                        while (isRunning && (len = fis.read(dataArray)) > 0) {
                            synchronized (Object.class) {
                                speedBuffer += len;
                                dataBuffer += len;
                            }
                            for (int i = 0; i < len; i += 3) {
                                if (dataArray[i] != 1) {
                                    listener.onError(CHECK_ERROR_CODE, "U盘数据校验错误!");
                                    stop();
                                    return;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onError(READ_ERROR_CODE, "U盘数据写入错误:" + e.getMessage());
                        stop();
                        return;
                    }
                }
            }
            listener.onFinish();
            stop();
        }).start();
    }

    public void stop() {
        isRunning = false;
    }

    /**
     * 速度计算回调
     */
    private void speedCalculate() {
        synchronized (Object.class) {
            final long totalSpace = DiskUtils.getPlateTotalSpace(platePath);
            final long speedTemp = speedBuffer;
            speedBuffer = 0;
            long usedTime = System.currentTimeMillis() - startTime;
            float progress = dataBuffer * 1.0f / totalSpace;
            listener.onProgress(speedTemp, usedTime, progress);
        }
    }

    public interface OnProgressListener {
        void onProgress(long speed, long usableTime, float progress);

        void onError(int code, String error);

        void onWriting();

        void onChecking();

        void onFinish();
    }
}
