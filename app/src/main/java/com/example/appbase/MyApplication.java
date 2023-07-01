package com.example.appbase;


import android.app.Application;

import com.example.appbase.Utils.SharePrefUtils;


public class MyApplication extends Application {

    public void onCreate() {
        super.onCreate();

        SharePrefUtils.init(this);

    }

}
