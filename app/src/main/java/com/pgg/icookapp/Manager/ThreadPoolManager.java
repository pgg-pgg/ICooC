package com.pgg.icookapp.Manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by PDD on 2017/7/18.
 */
public class ThreadPoolManager {
    private static ThreadPool mThreadPool = null;
    private static ThreadPoolExecutor executor;

    public static ThreadPool getmThreadPool() {
        if (mThreadPool == null) {
            synchronized (ThreadPoolManager.class) {
                if (mThreadPool == null) {
                    int cpuCount= Runtime.getRuntime().availableProcessors();
                   // System.out.println("=========Cpu个数======="+"        "+cpuCount);
                    mThreadPool = new ThreadPool(10,10,1L);
                }
            }
        }
        return mThreadPool;
    }

    public static class ThreadPool {
        private int corePoolSize;//核心线程数
        private int maximumPoolSize;//最大线程数
        private long keepAliveTime;//休息时间

        private ThreadPool(int corePoolSize,int maximumPoolSize,long keepAliveTime) {
            this.corePoolSize=corePoolSize;
            this.maximumPoolSize=maximumPoolSize;
            this.keepAliveTime=keepAliveTime;
        }
        public void execute(Runnable r) {
            if (executor==null){
                executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                        keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
                        Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
            }
            executor.execute(r);
        }
        public void cancle(Runnable r){
            if (executor!=null){
                //从线程队列中移除对象
                executor.getQueue().remove(r);
            }
        }
    }
}
