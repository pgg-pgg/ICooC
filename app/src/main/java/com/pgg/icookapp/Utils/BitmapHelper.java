package com.pgg.icookapp.Utils;


import com.lidroid.xutils.BitmapUtils;

/**
 * 单例模式的懒汉模式
 * Created by PDD on 2017/7/14.
 */
public class BitmapHelper {
    private static BitmapUtils mBitmapUtils=null;
    public static BitmapUtils getmBitmapUtils(){
        if (mBitmapUtils==null){
            synchronized (BitmapHelper.class){
                if (mBitmapUtils==null){
                    mBitmapUtils=new BitmapUtils(UIUtils.getContext());
                }
            }
        }
        return mBitmapUtils;
    }
}
