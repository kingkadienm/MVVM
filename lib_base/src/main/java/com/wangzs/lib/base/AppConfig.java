package com.wangzs.lib.base;


/**
 * Author: wangzs<br>
 * Created Date: 2025/8/31 20:10<br>
 * Desc : App 配置管理类 <br>
 */
public final class AppConfig {

    /**
     * 当前是否为调试模式
     */
    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    /**
     * 获取当前构建的模式
     */
    public static String getBuildType() {
        return BuildConfig.BUILD_TYPE;
    }

    /**
     * 当前是否要开启日志打印功能
     */
    public static boolean isLogEnable() {
        return BuildConfig.LOG_ENABLE;
    }

    /**
     * 获取当前应用的包名
     */
    public static String getPackageName() {
        return BuildConfig.APP_APPLICATION_ID;
    }

    /**
     * 获取当前应用的版本名
     */
    public static String getVersionName() {
        return BuildConfig.APP_VERSION_NAME;
    }

    /**
     * 获取当前应用的版本码
     */
    public static int getVersionCode() {
        return BuildConfig.APP_VERSION_CODE;
    }

    /**
     * 获取 Bugly Id
     */
    public static String getBuglyId() {
        return BuildConfig.BUGLY_ID;
    }

    /**
     * 获取服务器主机地址
     */
    public static String getHostUrl() {
        return BuildConfig.HOST_URL;
    }
}