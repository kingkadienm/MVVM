package com.wangzs.app_base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import com.wangzs.app_base.module_base.utils.log.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InvalidClassException;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @Description:
 * @Author: wangzs
 * @Date: 6/18/21 3:15 PM
 * @Version:
 */
public class AppApplicationContext {

    public static final String TAG = "AppApplicationContext";

    private static Context mContext;
    private static String mPackageName;
    private static Resources mResources;



    public static void setContext(Context context) {
        LogUtil.d(TAG, "[setContext] context " + context);
        mContext = context;
        if (mContext == null) {
            return;
        }
        mPackageName = context.getPackageName();
        LogUtil.d(TAG, "[setContext] context packageName:" + mPackageName);
    }


    /**
     * 程序上下文对象
     *
     * @return 上下文
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 应用程序包名
     *
     * @return 应用程序包名
     */
    public static String getPackageName() {
        return mPackageName;
    }

    public static Resources getResources() {
        return mResources;
    }

    public static void setResource(Resources resource) {
        mResources = resource;
    }

    /**
     * 获取应用程序版本名称
     *
     * @return
     */
    public static String getVersion() {
        String version = "0.0.0";
        try {
            PackageInfo packageInfo = getContext().getPackageManager().getPackageInfo(
                    getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }

    /**
     * 获取应用版本号
     *
     * @return 版本号
     */
    public static int getVersionCode() {
        int code = 1;
        try {
            PackageInfo packageInfo = getContext().getPackageManager().getPackageInfo(
                    getPackageName(), 0);
            code = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return code;
    }

    /**
     * 获取应用的SHA1
     *
     * @return
     */
    public static String getSHA1() {
        String apkPath = getContext().getPackageCodePath();
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = new byte[1024];
            int byteCount;
            FileInputStream fis = new FileInputStream(new File(apkPath));
            while ((byteCount = fis.read(bytes)) > 0) {
                msgDigest.update(bytes, 0, byteCount);
            }
            fis.close();
            BigInteger bi = new BigInteger(1, msgDigest.digest());
            String sha = bi.toString(16);
            return sha;
            // 这里添加从服务器中获取哈希值然后进行对比校验
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static void sendBroadcast(String action) {
        try {
            Intent i = new Intent(action);
            getContext().sendBroadcast(i);
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        }
    }

    public static void sendBroadcast(Intent i) {
        try {
            getContext().sendBroadcast(i);
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        }
    }
}
