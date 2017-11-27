package com.pgg.icookapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by PDD on 2017/7/22.
 */
public class PrefUtils {
    private static final String SHARE_NAME="config";
    private static SharedPreferences sp;

    public static boolean getBoolean(String key,boolean defvalue){
        if (sp==null){
            sp = UIUtils.getContext().getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,defvalue);
    }

    public static void putBoolean(String key,boolean value){
        if(sp==null){
            sp=UIUtils.getContext().getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();
    }
    public static int getInt(String key,int defvalue){
        if (sp==null){
            sp = UIUtils.getContext().getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        }
        return sp.getInt(key,defvalue);
    }

    public static void putInt(String key,int value){
        if(sp==null){
            sp=UIUtils.getContext().getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key,value).commit();
    }
    public static String getString(String key,String defvalue){
        if (sp==null){
            sp = UIUtils.getContext().getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defvalue);
    }

    public static void putString(String key,String value){
        if(sp==null){
            sp=UIUtils.getContext().getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    public static Set<String> getStringSet(String key,Set<String> defvalue){
        if (sp==null){
            sp = UIUtils.getContext().getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        }
        return sp.getStringSet(key,defvalue);
    }

    public static void putStringSet(String key,Set<String> defvalue){
        if(sp==null){
            sp=UIUtils.getContext().getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        }
        Set<String> s=getStringSet(key,null);
        if (s!=null&&!s.isEmpty()){
            defvalue.addAll(s);
        }
        sp.edit().putStringSet(key,defvalue).commit();
    }

    public static void removeStringSet(String key){
        if(sp==null){
            sp=UIUtils.getContext().getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        }
        sp.edit().remove(key).commit();
    }

}
