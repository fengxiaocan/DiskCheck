package com.n.disk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

public class DiskSpeed {
    private volatile long speedBuffer = 0;
    private volatile long dataBuffer = 0;
    private volatile long maxSpeed = 0;
    private volatile long startTime;
    private volatile boolean isRunning = false;
    private volatile boolean isWrite = false;
    private final long blockSize;
    private final int arraySize;

    private String platePath;
    private OnSpeedListener listener;

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
     * @param blockSize 每一次测试读写的数据的总大小,比如500MB,则blockSize=5
     * @param arraySize 每一次测试读写的数组大小,比如 new byte[16*1024],则arraySize=16
     * @param platePath U盘盘符
     * @param listener  回调
     */
    public DiskSpeed(int blockSize, int arraySize, String platePath, OnSpeedListener listener) {
        this.blockSize = blockSize * 1024L * 1024L;
        this.platePath = platePath;
        this.arraySize = arraySize;
        this.listener = listener;
    }

    private void onWriteFinal() {
        synchronized (DiskSpeed.class) {
            long usedTime = System.currentTimeMillis() - startTime;
            long speed = (long) (dataBuffer / (usedTime / 1000f));
            listener.onWriteFinish(usedTime, maxSpeed, speed);

            isWrite = false;
            reset();
        }
    }

    private void onReadFinal() {
        synchronized (DiskSpeed.class) {
            long usedTime = System.currentTimeMillis() - startTime;
            long speed = (long) (dataBuffer / (usedTime / 1000f));
            listener.onReadFinish(usedTime, maxSpeed, speed);

            isRunning = false;
            reset();
        }
    }

    public void start() {
        if (isRunning) {
            listener.onError("正在测试中");
            return;
        }
        //需要的总的数据存储空间
        DiskUtils.deleteDiskTempFile(platePath);
        if (DiskUtils.getPlateFreeSpace(platePath) < blockSize) {
            DiskUtils.deleteDiskTempFile(platePath);
        }
        if (DiskUtils.getPlateFreeSpace(platePath) < blockSize) {
            listener.onError("U盘存储空间不足");
            stop();
            return;
        }
        isRunning = true;

        taskSpeed.start();
        new Thread(() -> {
            reset();
            File file = new File(platePath, DiskUtils.DISK_HEADER);
            writeTask(file);
            readTask(file);
        }).start();
    }


    private void reset() {
        dataBuffer = 0;
        speedBuffer = 0;
        maxSpeed = 0;
        startTime = System.currentTimeMillis();
    }

    private void writeTask(File file) {
        isWrite = true;
        byte[] dataArray = getDataArray();
        Arrays.fill(dataArray, (byte) 1);

        file.deleteOnExit();
        long len;
        long bufferSize = 0;

        try (FileOutputStream fos = new FileOutputStream(file, false)) {
            while (isRunning && blockSize > bufferSize) {
                synchronized (Object.class) {
                    len = (blockSize - bufferSize) / dataArray.length > 0 ? dataArray.length : (blockSize - bufferSize) % dataArray.length;
                    bufferSize += len;
                    speedBuffer += len;
                    dataBuffer += len;
                    fos.write(dataArray, 0, (int) len);
                    fos.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            listener.onError("U盘数据写入错误:" + e.getMessage());
            stop();
            return;
        }
        onWriteFinal();
    }


    private void readTask(File file) {
        byte[] dataArray = getDataArray();
        try (FileInputStream fis = new FileInputStream(file)) {
            int len = 0;
            while (isRunning && (len = fis.read(dataArray)) > 0) {
                synchronized (Object.class) {
                    speedBuffer += len;
                    dataBuffer += len;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            listener.onError("U盘数据写入错误:" + e.getMessage());
            stop();
            return;
        }
        onReadFinal();
    }

    private byte[] getDataArray() {
        return new byte[arraySize * 1024];
    }

    public void stop() {
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    /**
     * 速度计算回调
     */
    private void speedCalculate() {
        synchronized (Object.class) {
            final long speed = speedBuffer;
            speedBuffer = 0;
            maxSpeed = Math.max(maxSpeed, speed);
            long usedTime = System.currentTimeMillis() - startTime;
            float progress =  dataBuffer*1.0f/blockSize;
            listener.onProgress(isWrite, usedTime, speed,progress);
        }
    }

    public interface OnSpeedListener {
        void onProgress(boolean isWrite, long usedTime, long speed,float progress);

        void onWriteFinish(long usedTime, long maxSpeed, long averageSpeed);

        void onReadFinish(long usedTime, long maxSpeed, long averageSpeed);

        void onError(String error);
    }

}
