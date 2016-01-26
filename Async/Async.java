package com.common.concurrent;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hzxushangfei
 * @date 7/6/15.
 * utils of Asynchronous task execution</p>
 * use with carefully as inner Callable<T> class can leak object</p>
 * Copyright 2015 NetEase. All rights reserved.
 */
public class Async {
    private final static String TAG = "Async";

    private static final Handler sMainHandler = new Handler(Looper.getMainLooper());

    // Allows for simultaneous reads and writes.
    private static final int NUM_IO_BOUND_THREADS = 2;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT+1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT*2+1;
    private static final int KEEP_ALIVE_SECONDS = 5;

    static final ExecutorService sIoBoundExecutor = Executors.newFixedThreadPool
            (NUM_IO_BOUND_THREADS);
    static final ExecutorService sCpuBoundExecutor = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE,
            KEEP_ALIVE_SECONDS,
            // terminated
            TimeUnit.SECONDS,
            /*
            * 使用LinkedBlockingQueue应指定容量
            * 否则LinkedBlockingQueue会默认一个类似无限大小的容量（Integer.MAX_VALUE）
            * 如果生产者的速度一旦大于消费者的速度，也许还没有等到队列满阻塞产生，系统内存就有可能已被消耗殆尽了。
            * */
            new LinkedBlockingQueue<Runnable>(128));
    static final ExecutorService sDbExecutor = Executors.newSingleThreadExecutor();

    private Async() {
    }

    /**
     * run on background thread
     */
    public static <T> Future<T> runOnBgThread(Callable<T> callable) {
        Preconditions.checkNotNull(callable);
        return sCpuBoundExecutor.submit(callable);
    }

    public static void runOnBgThread(Runnable runnable) {
        Preconditions.checkNotNull(runnable);
        sCpuBoundExecutor.submit(runnable);
    }

    /**
     * run on dedicated io thread
     */
    public static <T> Future<T> runOnIoThread(Callable<T> callable) {
        Preconditions.checkNotNull(callable);
        return sIoBoundExecutor.submit(callable);
    }

    public static void runOnIoThread(Runnable runnable) {
        Preconditions.checkNotNull(runnable);
        sIoBoundExecutor.submit(runnable);
    }

    /**
     * run on dedicated single db thread
     * all db operation use single thread,so we need not
     * care about synchronized
     */
    public static <T> Future<T> runOnDbThread(Callable<T> callable) {
        Preconditions.checkNotNull(callable);
        return sDbExecutor.submit(callable);
    }

    public static void runOnDbThread(Runnable runnable) {
        Preconditions.checkNotNull(runnable);
        sDbExecutor.submit(runnable);
    }

    /**
     * run on main thread
     */
    public static void runOnUiThread(final Runnable runnable) {
        Preconditions.checkNotNull(runnable);

        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runnable.run();
            return;
        }

        sMainHandler.post(runnable);
    }

    public static void runDelayedOnUiThread(final Runnable runnable, long delayMillis) {
        Preconditions.checkNotNull(runnable);
        sMainHandler.postDelayed(runnable, delayMillis);
    }
}