package com.n.disk.common;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池所有线程,执行完成后,线程都会在保活时间内自动销毁;
 * 同时,无论同时塞入多少个任务,只能达到最大的线程数同时运行,其余的都会在队列中等待执行
 * 不会内存溢出
 */
public final class ExecutorTool {
    public static long MAIN_EXECUTOR_KEEP_ALIVE_TIME = TimeUnit.SECONDS.toMillis(60);//主任务线程保活时间
    public static long THREAD_KEEP_ALIVE_TIME = TimeUnit.SECONDS.toMillis(1);//子任务线程保活时间

    public static long SINGLE_EXECUTOR_KEEP_ALIVE_TIME = TimeUnit.SECONDS.toMillis(120);//主任务线程保活时间
    public static int MAIN_EXECUTOR_CORE_THREAD_SIZE = 10;//核心线程数

    private static ThreadPoolExecutor taskExecutor;
    private static ThreadPoolExecutor singleExecutor;

    public static synchronized void recyclerMainExecutor() {
        if (taskExecutor != null) {
            taskExecutor.shutdown();
        }
        taskExecutor = null;
    }

    public static synchronized void recyclerSingleExecutor() {
        if (singleExecutor != null) {
            singleExecutor.shutdown();
        }
        singleExecutor = null;
    }

    /**
     * 创建子任务线程池队列
     *
     * @return
     */
    public static ThreadPoolExecutor newTaskQueue(int corePoolSize) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,
                Integer.MAX_VALUE,//队列数量
                THREAD_KEEP_ALIVE_TIME,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    /**
     * 主要的线程队列
     *
     * @return
     */
    public static synchronized ThreadPoolExecutor mainQueue() {
        if (taskExecutor == null) {
            taskExecutor = new ThreadPoolExecutor(MAIN_EXECUTOR_CORE_THREAD_SIZE,
                    Integer.MAX_VALUE,//队列数量
                    MAIN_EXECUTOR_KEEP_ALIVE_TIME,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());
            taskExecutor.allowCoreThreadTimeOut(true);
        }
        return taskExecutor;
    }

    /**
     * 单个的线程队列
     *
     * @return
     */
    public static synchronized ThreadPoolExecutor singleQueue() {
        if (singleExecutor == null) {
            singleExecutor = new ThreadPoolExecutor(1,
                    Integer.MAX_VALUE,//队列数量
                    SINGLE_EXECUTOR_KEEP_ALIVE_TIME,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());
            singleExecutor.allowCoreThreadTimeOut(true);
        }
        return singleExecutor;
    }
}
