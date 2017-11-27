package com.pgg.icookapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.pgg.icookapp.Manager.ActivityManager;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.AppConfigUtils;
import com.pgg.icookapp.Utils.DataCleanManager;
import com.pgg.icookapp.Utils.FileUtil;
import com.pgg.icookapp.Utils.MethodsCompat;
import com.pgg.icookapp.Utils.UIUtils;

import java.io.File;
import java.util.Properties;

/**
 * Created by PDD on 2017/7/26.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout rl_update_pwd,rl_user_deal,rl_about_app,rl_clear,rl_exit;
    private TextView tv_title,tv_clear_trash;
    private ImageButton btn_menu;
    private final int CLEAN_SUC=1001;
    private final int CLEAN_FAIL=1002;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        tv_title = (TextView)findViewById(R.id.tv_title);
        btn_menu = (ImageButton)findViewById(R.id.btn_menu);

        tv_title.setText("设置");
        btn_menu.setBackgroundResource(R.drawable.item_left);

        tv_clear_trash=(TextView)findViewById(R.id.tv_clear_trash);
        rl_update_pwd=(RelativeLayout)findViewById(R.id.rl_update_pwd);
        rl_user_deal=(RelativeLayout)findViewById(R.id.rl_user_deal);
        rl_about_app=(RelativeLayout)findViewById(R.id.rl_about_app);
        rl_clear=(RelativeLayout)findViewById(R.id.rl_clear);
        rl_exit=(RelativeLayout)findViewById(R.id.rl_exit);

        rl_update_pwd.setOnClickListener(this);
        rl_about_app.setOnClickListener(this);
        rl_clear.setOnClickListener(this);
        rl_exit.setOnClickListener(this);
        rl_user_deal.setOnClickListener(this);
        btn_menu.setOnClickListener(this);
        caculateCacheSize();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_menu:
                finish();
                break;
            case R.id.rl_update_pwd:
                intent = new Intent(UIUtils.getContext(),UpdatePwdActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以关掉所要到的界面中间的activity
                startActivity(intent);
                SettingActivity.this.finish();
                break;
            case R.id.rl_user_deal:

                break;
            case R.id.rl_about_app:

                break;
            case R.id.rl_clear:
                onClickCleanCache();
                break;
            case R.id.rl_exit:
                new AlertDialog.Builder(this).setTitle("退出提示").setMessage("退出后不会删除任何历史数据，下次登录仍然可以使用本账号")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                signOut();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
                break;
            default:
                break;
        }
    }

    //退出环信登录
    private void signOut(){
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(UIUtils.getContext(), LoginActivity.class);
                startActivity(intent);
                ActivityManager.finish();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(UIUtils.getContext(),"退出错误",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    private void onClickCleanCache() {
        getConfirmDialog(SettingActivity.this, "是否清空缓存?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearAppCache();
                tv_clear_trash.setText("0KB");
            }
        }).show();
    }
    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        return builder;
    }
    public static AlertDialog.Builder getDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }

    /**
     * 计算缓存的大小
     */
    private void caculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = UIUtils.getContext().getFilesDir();
        File cacheDir = UIUtils.getContext().getCacheDir();

        fileSize += FileUtil.getDirSize(filesDir);
        fileSize += FileUtil.getDirSize(cacheDir);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            File externalCacheDir = MethodsCompat
                    .getExternalCacheDir(UIUtils.getContext());
            fileSize += FileUtil.getDirSize(externalCacheDir);
            //fileSize += FileUtil.getDirSize(new File(com.FileUtils.getSDCardPath()+ File.separator + "KJLibrary/cache"));
        }
        if (fileSize > 0)
            cacheSize = FileUtil.formatFileSize(fileSize);
        tv_clear_trash.setText(cacheSize);
    }

    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }
    /**
     * 清除app缓存
     */
    public void myclearaAppCache() {
        DataCleanManager.cleanDatabases(UIUtils.getContext());
        // 清除数据缓存
        DataCleanManager.cleanInternalCache(UIUtils.getContext());
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            DataCleanManager.cleanCustomCache(MethodsCompat
                    .getExternalCacheDir(UIUtils.getContext()).getPath());
        }
        // 清除编辑器保存的临时内容
        Properties props = getProperties();
        for (Object key : props.keySet()) {
            String _key = key.toString();
            if (_key.startsWith("temp"))
                removeProperty(_key);
        }
       // Core.getKJBitmap().cleanCache();
    }

    /**
     * 清除保存的缓存
     */
    public Properties getProperties() {
        return AppConfigUtils.getAppConfig(UIUtils.getContext()).get();
    }
    public void removeProperty(String... key) {
        AppConfigUtils.getAppConfig(UIUtils.getContext()).remove(key);
    }
    /**
     * 清除app缓存
     *
     * @param
     */
    public void clearAppCache() {

        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    myclearaAppCache();
                    msg.what = CLEAN_SUC;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = CLEAN_FAIL;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CLEAN_FAIL:
                    Toast.makeText(UIUtils.getContext(),"清除失败",Toast.LENGTH_SHORT).show();
                    break;
                case CLEAN_SUC:
                    Toast.makeText(UIUtils.getContext(),"清除成功",Toast.LENGTH_SHORT).show();
                    break;
            }
        };
    };
}
