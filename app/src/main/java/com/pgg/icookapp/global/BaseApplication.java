package com.pgg.icookapp.global;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseUI;
import com.pgg.icookapp.Manager.DBManager;
import java.util.Iterator;
import java.util.List;


/**
 * Created by PDD on 2017/7/22.
 */
public class BaseApplication extends Application {

    private static Context context;
    private static Handler handler;
    private static int threadId;
    private DBManager dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        threadId = android.os.Process.myTid();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "S8G7seHCcMIEqb6QQXDzhLLN-gzGzoHsz", "oUoCvNkyIdRa0cUTq8OUAz8i");
        AVOSCloud.setDebugLogEnabled(true);


        dbHelper = new DBManager(this);
        dbHelper.openDatabase();

        EaseUI.getInstance().init(this,null);
        EMClient.getInstance().setDebugMode(true);
    }
    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getThreadId() {
        return threadId;
    }
}
