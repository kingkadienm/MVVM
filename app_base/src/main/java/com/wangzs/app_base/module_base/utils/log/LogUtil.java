/*
 *  Copyright (c) 2015 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package com.wangzs.app_base.module_base.utils.log;


import android.text.TextUtils;

import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;
import com.wangzs.app_base.AppApplicationContext;
import com.wangzs.app_base.BuildConfig;
import com.wangzs.app_base.module_base.utils.FileAccessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * 日志打印工具（使用mars xlog日志打印框架）
 */
public class LogUtil {

    public static final String TAG = "MVVM～～～～～>>>>>>>>>";

    static {
        System.loadLibrary("c++_shared");
        System.loadLibrary("marsxlog");
    }

    public static void setDebugMode() {
        final String logPath = FileAccessor.APPS_ROOT_DIR + "/log";
        final String cachePath = AppApplicationContext.getContext().getFilesDir() + "/xlog";

        String fileName = getProcessName();


        Xlog xlog = new Xlog();
        Log.setLogImp(xlog);

        if (BuildConfig.DEBUG) {
            Log.setConsoleLogOpen(true);
            Log.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, cachePath, logPath, fileName, 0);
        } else {
            Log.setConsoleLogOpen(false);
            Log.appenderOpen(Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, cachePath, logPath, fileName, 0);
        }
    }

    /**
     * 获取进程号对应的进程名
     *
     * @return 进程名
     */
    public static String getProcessName() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + android.os.Process.myPid() + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            String[] split = processName.split(":");
            if (split.length == 2) {
                return "[" + split[1] + "]";
            }
            return "";
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return "xxx";
    }

    public static void close() {
        Log.appenderClose();
    }

    /**
     * 使用默认的TAG 打印日志
     *
     * @param msg 日志内容
     */
    public static void v(String msg) {
        v(TAG, msg);
    }

    /**
     * 根据提高的日志TAG和msg内容打印日志
     *
     * @param tag 日志tag
     * @param msg 日志内容
     */
    public static void v(String tag, String msg) {
        v(tag, msg, (Object[]) null);
    }

    /**
     * 根据提供的日志TAG进行日志格式化输出
     *
     * @param tag    日志tag
     * @param format 格式化占位符
     * @param obj    格式化占位符替代内容
     */
    public static void v(String tag, final String format, final Object... obj) {
        Log.v(tag, format, obj);
    }

    /**
     * 使用默认的TAG 打印日志
     *
     * @param msg 日志内容
     */
    public static void d(String msg) {
        d(TAG, msg);
    }

    /**
     * 根据提高的日志TAG和msg内容打印日志
     *
     * @param tag 日志tag
     * @param msg 日志内容
     */
    public static void d(String tag, String msg) {
        d(tag, msg, (Object[]) null);
    }

    /**
     * 根据提供的日志TAG进行日志格式化输出
     *
     * @param tag    日志tag
     * @param format 格式化占位符
     * @param obj    格式化占位符替代内容
     */
    public static void d(String tag, final String format, final Object... obj) {
        Log.d(tag, format, obj);
    }

    /**
     * 使用默认的TAG 打印日志
     *
     * @param msg 日志内容
     */
    public static void i(String msg) {
        i(TAG, msg);
    }

    /**
     * 根据提高的日志TAG和msg内容打印日志
     *
     * @param tag 日志tag
     * @param msg 日志内容
     */
    public static void i(String tag, String msg) {
        i(tag, msg, (Object[]) null);
    }

    /**
     * 根据提供的日志TAG进行日志格式化输出
     *
     * @param tag    日志tag
     * @param format 格式化占位符
     * @param obj    格式化占位符替代内容
     */
    public static void i(String tag, final String format, final Object... obj) {
        Log.i(tag, format, obj);
    }

    /**
     * 使用默认的TAG 打印日志
     *
     * @param msg 日志内容
     */
    public static void w(String msg) {
        w(TAG, msg);
    }

    /**
     * 根据提高的日志TAG和msg内容打印日志
     *
     * @param tag 日志tag
     * @param msg 日志内容
     */
    public static void w(String tag, String msg) {
        w(tag, msg, (Object[]) null);
    }

    /**
     * 根据提供的日志TAG进行日志格式化输出
     *
     * @param tag    日志tag
     * @param format 格式化占位符
     * @param obj    格式化占位符替代内容
     */
    public static void w(String tag, final String format, final Object... obj) {
        Log.w(tag, format, obj);
    }

    /**
     * 使用默认的TAG 打印日志
     *
     * @param msg 日志内容
     */
    public static void e(String msg) {
        e(TAG, msg);
    }

    /**
     * 根据提高的日志TAG和msg内容打印日志
     *
     * @param tag 日志tag
     * @param msg 日志内容
     */
    public static void e(String tag, String msg) {
        e(tag, msg, (Object[]) null);
    }

    /**
     * 根据提供的日志TAG进行日志格式化输出
     *
     * @param tag    日志tag
     * @param format 格式化占位符
     * @param obj    格式化占位符替代内容
     */
    public static void e(String tag, final String format, final Object... obj) {
        Log.e(tag, format, obj);
    }

    /**
     * 打印程序异常日志
     *
     * @param tag    日志tag
     * @param tr     异常信息
     * @param format 格式化串
     * @param obj    格式化占位
     */
    public static void printErrStackTrace(String tag, Throwable tr, final String format, final Object... obj) {
        Log.printErrStackTrace(tag, tr, format, obj);
    }


    public static String getLogUtilsTag(Class<? extends Object> clazz) {
        return LogUtil.TAG + "." + clazz.getSimpleName();
    }
}
