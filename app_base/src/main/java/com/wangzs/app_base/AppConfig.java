package com.wangzs.app_base;

import android.content.Context;

import com.wangzs.app_base.module_base.utils.FileAccessor;

/**
 * @Description:
 * @Author: wangzs
 * @Date: 6/18/21 3:08 PM
 * @Version:
 */
public class AppConfig {


    /**
     * Flavor字段BuildConfig
     */
    public static String FLAVOR = "mvvm";


    public static void init(Context context){
        AppApplicationContext.setContext(context);
        FileAccessor.initFileAccess();
    }

}
