package com.wangzs.mvvm;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.wangzs.mvvm.base.BaseApp;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description:
 * @Author: wangzs
 * @Date: 6/18/21 5:53 PM
 * @Version:
 */
public class App extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        List<String> aa = new ArrayList<>();
        aa.add("123");
        aa.add("1234");
        aa.add("12345");
        aa.add("qwer");
        aa.add("asdf");
        //输入 1  你想要直接显示 123  1234 12345
        String input = "1";
        List<String> searchList = new ArrayList<>();
        for (String s : aa) {
            if (s.contains(input)){
                searchList.add(s);
            }
        }

    }
}
