package com.wangzs.app_base.module_base.utils;

import android.os.*;
import android.os.Process;

import com.wangzs.app_base.module_base.utils.log.LogUtil;


public class HandlerHelper {
    private static final String TAG = LogUtil.getLogUtilsTag(HandlerHelper.class);

    private static Handler iHandler;
    private Handler mHandler;
    private HandlerThread mHandlerThread;

    public HandlerHelper() {
         init();
    }

    private static Handler getMainLooperHandler() {
        if(iHandler == null) {
            iHandler = new Handler(Looper.getMainLooper());
        }
        return iHandler;
    }

    /**
     *
     * @param r 任务
     * @param delayMillis 延迟时间
     */
    public static void postDelayedRunnOnUI(Runnable r , long delayMillis) {
        if(r == null) {
            return ;
        }
        getMainLooperHandler().postDelayed(r, delayMillis);
    }

    /**
     *
     * @param r 任务
     */
    public static void postRunnOnUI(Runnable r) {
        if(r == null) {
            return ;
        }
        getMainLooperHandler().post(r);
    }


    /**
     *
     */
    private void init() {
        iHandler = null;
        mHandler = null;
        mHandlerThread = new HandlerThread(HandlerHelper.class.getSimpleName(), Process.THREAD_PRIORITY_DEFAULT);
        mHandlerThread.start();
    }

    /**
     *
     * @param r 任务
     */
    public static void removeCallbacksRunnOnUI(Runnable r) {
        if(r == null) {
            return ;
        }
        getMainLooperHandler().removeCallbacks(r);
    }

    /**
     * @param r 任务
     * @param delayMillis 延迟时间
     */
    public void postDelayedRunnOnThead(Runnable r , long delayMillis) {
        if(r == null) {
            return ;
        }
        getTheadHandler().postDelayed(r, delayMillis);
    }

    /**
     * 设置线程为最高优先等级
     */
    public void setHighPriority() {
        if(mHandlerThread == null || !mHandlerThread.isAlive()){
            LogUtil.e(TAG,  "setHighPriority failed thread is dead");
            return ;
        }
        int tid = mHandlerThread.getThreadId();
        try {
            if (Process.getThreadPriority(tid) != Process.THREAD_PRIORITY_URGENT_DISPLAY) {
                Process.setThreadPriority(tid, Process.THREAD_PRIORITY_URGENT_DISPLAY);
            } else {
                LogUtil.d(TAG, "setHighPriority No Need.");
                return ;
            }
        } catch (Exception e) {
            LogUtil.e(TAG,  "thread: " + tid + " setHighPriority failed");
            return ;
        }
        LogUtil.d(TAG, "thread:" + tid + " setHighPriority to" + Process.getThreadPriority(tid));
    }

    /**
     * 检查是否出处在最高优先等级
     * @return 是否设置成功
     */
    public boolean checkInHighPriority() {
        boolean inHighPriority = true;
        if(mHandlerThread == null || !mHandlerThread.isAlive()){
            LogUtil.e(TAG, "check inHighPriority failed thread is dead");
            inHighPriority = false;
            return inHighPriority;
        }

        int tid = mHandlerThread.getThreadId();
        try {
            int priority = Process.getThreadPriority(tid);
            inHighPriority = priority == Process.THREAD_PRIORITY_URGENT_DISPLAY;

        } catch (Exception e) {
            LogUtil.e(TAG, "thread:" + tid + "  check inHighPriority failed");
        }
        return inHighPriority;
    }

    /**
     * 设置线程为最低优先等级
     */
    public void setLowPriority() {
        if(mHandlerThread == null || !mHandlerThread.isAlive()){
            LogUtil.e(TAG, "setLowPriority failed thread is dead");
            return ;
        }
        int tid = mHandlerThread.getThreadId();
        try {
            if (Process.getThreadPriority(tid) != Process.THREAD_PRIORITY_DEFAULT) {
                Process.setThreadPriority(tid, Process.THREAD_PRIORITY_DEFAULT);
            } else {
                LogUtil.d(TAG, "setLowPriority No Need.");
                return ;
            }
        } catch (Exception e) {
            LogUtil.e(TAG,  "thread: " + tid + " setLowPriority failed");
            return ;
        }
        LogUtil.d(TAG, "thread:" + tid + " setLowPriority to" + Process.getThreadPriority(tid));
    }

    /**
     * 是否主线程
     * @return 是否主线程
     */
    public static boolean isMainThread() {
        return (Thread.currentThread().getId() != Looper.getMainLooper().getThread().getId());
    }

    /**
     * 获得线程Handler
     * @return Handler
     */
    public Handler getTheadHandler() {
        if(mHandler == null) {
            mHandler = new Handler(mHandlerThread.getLooper());
        }
        return mHandler;
    }

    public final Looper getLooper(){
        return mHandlerThread.getLooper();
    }

    /**
     * 是否运行在主线程
     * @return 是否子线程
     */
    public boolean isRunnOnThead() {
        return (Thread.currentThread().getId() != mHandlerThread.getId());
    }

    /**
     *
     * @param r 任务
     * @return 是否成功
     */
    public int postRunnOnThead(Runnable r) {
        if(r == null) {
            return -1;
        }
        getTheadHandler().post(r);
        return 0;
    }
}
