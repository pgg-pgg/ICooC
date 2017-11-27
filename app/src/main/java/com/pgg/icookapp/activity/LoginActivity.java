package com.pgg.icookapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.PrefUtils;
import com.pgg.icookapp.Utils.StringUtils;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.domain.MyLoginInfo;
import com.pgg.icookapp.http.protocol.LoginProtocol;

/**
 * Created by PDD on 2017/7/22.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    public EditText edit_username,edit_pwd;
    public Button btn_login,btn_register,btn_forget_pwd;
    private CheckBox cb_isrem;
    private Intent intent;
    public static MyLoginInfo myLoginInfo;
    private String phone;
    private String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        ImageButton ib_menu=(ImageButton)findViewById(R.id.btn_menu);
        ib_menu.setVisibility(View.GONE);
        TextView tv_title=(TextView)findViewById(R.id.tv_title);
        tv_title.setText("登录");

        edit_username=(EditText)findViewById(R.id.username);
        edit_pwd=(EditText)findViewById(R.id.password);
        cb_isrem = (CheckBox)findViewById(R.id.cb_isrem);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_register=(Button)findViewById(R.id.btn_register);
        btn_forget_pwd=(Button)findViewById(R.id.btn_forget_pwd);
        cb_isrem.setChecked(PrefUtils.getBoolean("checked",false));
        if (cb_isrem.isChecked()){
            edit_username.setText(PrefUtils.getString("phone",""));
            edit_pwd.setText(PrefUtils.getString("password",""));
        }else {
            edit_username.setText("");
            edit_pwd.setText("");
        }

        btn_login.setOnClickListener(this);
        btn_forget_pwd.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        cb_isrem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    PrefUtils.putBoolean("checked",isChecked);
                }else {
                    PrefUtils.putBoolean("checked",isChecked);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                phone = edit_username.getText().toString();
                password = edit_pwd.getText().toString();
                if (StringUtils.isEmpty(phone)||StringUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"请填写登录信息",Toast.LENGTH_SHORT).show();
                }else if (phone.length()!=11){
                    Toast.makeText(LoginActivity.this,"用户名不存在",Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        Login login=new Login(phone, password);
                        login.start();
                        login.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (myLoginInfo!=null&&myLoginInfo.code==0) {
                        Log.e("sdsfsaf", myLoginInfo.id);
                        PrefUtils.putString("phone", phone);
                        PrefUtils.putString("password", password);
                        EMSignIn();
//                        intent = new Intent(LoginActivity.this,MainActivity.class);
//                        startActivity(intent);
//                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_register:
                intent=new Intent(UIUtils.getContext(),RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_forget_pwd:
                intent=new Intent(UIUtils.getContext(),ForgetPwdActivity.class);
                startActivity(intent);
                break;
        }
    }
    public class Login extends Thread{
        private String phone,password;
        public Login(final String phone, final String password) {
            super();
            this.phone=phone;
            this.password=password;
        }

        @Override
        public void run() {
            myLoginInfo=new LoginProtocol(phone,password).getData();
        }
    }

    private void EMSignIn(){
        EMClient.getInstance().login(edit_username.getText().toString().trim(), edit_pwd.getText().toString().trim(), new EMCallBack() {
            @Override
            public void onSuccess() {
                UIUtils.RunningMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    }
                });
                intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(final int i, final String s) {
                UIUtils.RunningMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "登录失败" + i + "," + s, Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
