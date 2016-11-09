package me.khrystal.android_sqlcipher_ormlite;

import android.app.Application;
import android.content.Context;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/11/9
 * update time:
 * email: 723526676@qq.com
 */

public class MainApplication extends Application {

    public static Context APP_CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        APP_CONTEXT = this;
    }
}
