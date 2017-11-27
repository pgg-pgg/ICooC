package com.pgg.icookapp.Manager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PDD on 2017/7/29.
 */
public class ActivityManager {

    public static List<Activity> list = new ArrayList<Activity>();

    public static void addActivity(Activity activity){
        list.add(activity);
    }

    public static void removeActivity(Activity activity){
        list.remove(activity);
    }

    public static void finish(){
        for (Activity activity : list) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
