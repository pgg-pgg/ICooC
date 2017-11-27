package com.pgg.icookapp.Utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

import com.pgg.icookapp.global.BaseApplication;

/**
 * Created by PDD on 2017/7/22.
 */
public class UIUtils {
    //1.得到application中初始化的一些常量
    public static Context getContext(){
        return BaseApplication.getContext();
    }
    public static Handler getHandler(){
        return BaseApplication.getHandler();
    }
    public static int getThreadId(){
        return BaseApplication.getThreadId();
    }

    public static View getView(int id){
        return View.inflate(getContext(), id, null);
    }

    ////////////////////////////////////判断当前是否运行在主线程/////////////////////////////////////
    public static boolean IsRunMainThread(){
        return getThreadId()==android.os.Process.myTid();
    }
    ////////////////////////////////////将当前线程转为主线程运行/////////////////////////////////////
    public static void RunningMainThread(Runnable r){
        if (IsRunMainThread()){
            r.run();
        }else {
            getHandler().post(r);
        }
    }
    ////////////////////////////////////根据id获取字符串/////////////////////////////////////
    public static String getString(int id){
        return getContext().getResources().getString(id);
    }
    ////////////////////////////////////根据id获取字符串数组/////////////////////////////////////
    public static String[] getStringArray(int id){
        return getContext().getResources().getStringArray(id);
    }
    ////////////////////////////////////根据id获取图片/////////////////////////////////////
    public static Drawable getDrawable(int id){
        return getContext().getResources().getDrawable(id);
    }
    ////////////////////////////////////根据id获取颜色值/////////////////////////////////////
    public static int getColor(int id){
        return getContext().getResources().getColor(id);
    }
    ////////////////////////////////////根据id获取尺寸值/////////////////////////////////////
    public static float getDimen(int id){
        return getContext().getResources().getDimensionPixelSize(id);
    }
    ////////////////////////////////////根据id获取颜色集合/////////////////////////////////////
    public static ColorStateList getColorStateList(int id){
        return getContext().getResources().getColorStateList(id);
    }
    ////////////////////////////////////dp转px/////////////////////////////////////
    public static int dp2px(float des){
        float density=getContext().getResources().getDisplayMetrics().density;
        return (int)(density*des+0.5f);
    }
    ////////////////////////////////////px转dp/////////////////////////////////////
    public static float px2dp(float px){
        float density=getContext().getResources().getDisplayMetrics().density;
        return px/density;
    }

}
