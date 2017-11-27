package com.pgg.icookapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pgg.icookapp.Manager.ActivityManager;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.MD5Utils;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.http.protocol.UpdatePwdProtocol;

/**
 * Created by PDD on 2017/7/29.
 */
public class UpdatePwdActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_title;
    private ImageButton btn_menu;
    private EditText edit_old_pwd;
    private EditText edit_new_pwd;
    private Button btn_update_pwd;
    private static int code;

    private static String userId=LoginActivity.myLoginInfo.id;
    private static String oldPassword=LoginActivity.myLoginInfo.password;

    private String password,newPassword;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);

//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        tv_title = (TextView)findViewById(R.id.tv_title);
        btn_menu = (ImageButton)findViewById(R.id.btn_menu);

        tv_title.setText("修改密码");
        btn_menu.setBackgroundResource(R.drawable.item_left);

        edit_old_pwd=(EditText)findViewById(R.id.edit_old_pwd);
        edit_new_pwd=(EditText)findViewById(R.id.edit_new_pwd);

        btn_update_pwd=(Button)findViewById(R.id.btn_update_pwd);
        btn_update_pwd.setOnClickListener(this);
        btn_menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_menu:
                finish();
                break;
            case R.id.btn_update_pwd:
                password=edit_old_pwd.getText().toString().trim();
                newPassword=edit_new_pwd.getText().toString().trim();
                if (!MD5Utils.encoder(password).equals(oldPassword)){
                    Toast.makeText(UIUtils.getContext(),"请输入正确的旧密码",Toast.LENGTH_LONG).show();
                }else {
                    try {
                        UpdatePwdConnection updatePwdConnection=new UpdatePwdConnection(password,newPassword);
                        updatePwdConnection.start();
                        updatePwdConnection.join();
                        if (code==0){
                            Toast.makeText(UIUtils.getContext(),"密码修改成功",Toast.LENGTH_LONG).show();
                            ActivityManager.finish();
                            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }else {
                            Toast.makeText(UIUtils.getContext(),"密码修改失败，请重试",Toast.LENGTH_LONG).show();
                            edit_old_pwd.setText("");
                            edit_new_pwd.setText("");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private class UpdatePwdConnection extends Thread{
        private String password,newPassword;
        public UpdatePwdConnection(final String password, final String newPassword) {
            super();
            this.password=password;
            this.newPassword=newPassword;
        }
        @Override
        public void run() {
            code=new UpdatePwdProtocol(userId,password,newPassword).getData();
        }
    }
}
