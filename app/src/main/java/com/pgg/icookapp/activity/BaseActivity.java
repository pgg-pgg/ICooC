package com.pgg.icookapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.Toast;

//import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.pgg.icookapp.Manager.ActivityManager;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.UIUtils;

/**
 * Created by PDD on 2017/7/23.
 */
public class BaseActivity extends FragmentActivity {

    private long exitTime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setBehindContentView(R.layout.left_menu);
        ActivityManager.addActivity(this);
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            exit(); // 在这里进行点击判断
//            return false;
//            }
//        return super.onKeyDown(keyCode, event);
//    }
//    public void exit() {
//        if ((System.currentTimeMillis() - exitTime) > 2000) {
//            // 点击间隔大于两秒，做出提示
//            Toast.makeText(UIUtils.getContext(), "再按一次退出应用", Toast.LENGTH_SHORT).show();
//            exitTime = System.currentTimeMillis();
//            } else {
//            // 连续点击量两次，进行应用退出的处理
//            System.exit(0);
//        }
//    }

}
