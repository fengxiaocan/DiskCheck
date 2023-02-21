package com.n.disk;

import java.io.File;

/**
 * 快速扩容测试
 */
public class FastCheck {
    private volatile boolean isRunning = false;
    private String platePath;
    private long blockSize;
    private OnCheckPlateListener listener;
    private volatile long startTime;
    private volatile long startFreeSpace;

    /**
     * @param platePath U盘路径
     * @param blockSize 需要测试的块的大小
     * @param listener  回调
     */
    public FastCheck(String platePath, long blockSize, OnCheckPlateListener listener) {
        this.platePath = platePath;
        this.blockSize = blockSize;
        this.listener = listener;
    }

    public void stop() {
        isRunning = false;
    }

    public void start() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        new Thread(() -> {
            startTime = System.currentTimeMillis();
            startFreeSpace = DiskUtils.getPlateFreeSpace(platePath);
            if (startFreeSpace <= 1) {
                DiskUtils.deleteDiskTempFile(platePath);
            }
            long count = startFreeSpace / blockSize;
            for (long i = 0; i < count; i++) {
                String name = DiskUtils.DISK_HEADER + System.nanoTime();
                File file = new File(platePath, name);
                if (isRunning) {
                    try {
                        DiskUtils.createFixedFile2(file, blockSize);
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onError(e);
                        return;
                    }
                }
            }
            final long freeSpace = DiskUtils.getPlateFreeSpace(platePath);
            if (freeSpace > 1) {
                if (isRunning) {
                    String name = DiskUtils.DISK_HEADER + System.nanoTime();
                    File file = new File(platePath, name);
                    try {
                        DiskUtils.createFixedFile2(file, freeSpace - 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onError(e);
                        return;
                    }
                }
            }
            long millis = System.currentTimeMillis();
            long usedTime = millis - startTime;
            stop();
            listener.onFinish(usedTime);
            DiskUtils.deleteDiskTempFile(platePath);
        }).start();
    }

    public interface OnCheckPlateListener {

        /**
         * 如果有异常,可能容量为虚假的
         *
         * @param e
         */
        void onError(Exception e);

        /**
         * 如果测试完成,可以证明容量正确
         *
         * @param usedTime
         */
        void onFinish(long usedTime);
    }
}
