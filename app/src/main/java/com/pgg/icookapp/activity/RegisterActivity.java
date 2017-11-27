package com.pgg.icookapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVSMS;
import com.avos.avoscloud.AVSMSOption;
import com.avos.avoscloud.RequestMobileCodeCallback;
//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.pgg.icookapp.Manager.ThreadPoolManager;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.PrefUtils;
import com.pgg.icookapp.Utils.StringUtils;
import com.pgg.icookapp.Utils.TimeCountUtils;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.http.protocol.RegisterProtocol;

/**
 * Created by PDD on 2017/7/23.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText edit_phone,edit_pwd,edit_school,edit_process,edit_pwd_sure,edit_register_sure;
    private Button btn_register,btn_get_sure;
    private int code;

    private TimeCountUtils timeCountUtils;
    private String phone,password,password_sure,school,major,identifying_code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        ImageButton im_menu=(ImageButton)findViewById(R.id.btn_menu);
        im_menu.setVisibility(View.GONE);
        TextView tv_title=(TextView)findViewById(R.id.tv_title);
        tv_title.setText("注册");

        edit_phone=(EditText)findViewById(R.id.edit_register_phone);
        edit_pwd=(EditText)findViewById(R.id.edit_register_pwd);
        edit_school=(EditText)findViewById(R.id.edit_register_school);
        edit_process=(EditText)findViewById(R.id.edit_register_process);
        edit_pwd_sure=(EditText)findViewById(R.id.edit_register_pwd_sure);
        edit_register_sure=(EditText)findViewById(R.id.edit_register_sure);

        btn_register=(Button)findViewById(R.id.btn_register_1);
        btn_get_sure=(Button)findViewById(R.id.btn_get_sure);
        btn_register.setOnClickListener(this);
        btn_get_sure.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register_1:
                phone=edit_phone.getText().toString().trim();
                identifying_code=edit_register_sure.getText().toString().trim();
                password=edit_pwd.getText().toString().trim();
                password_sure=edit_pwd_sure.getText().toString().trim();
                school=edit_school.getText().toString().trim();
                major=edit_process.getText().toString().trim();

                if (StringUtils.isEmpty(phone)||StringUtils.isEmpty(identifying_code)||StringUtils.isEmpty(password)
                        ||StringUtils.isEmpty(password_sure)||StringUtils.isEmpty(school)||StringUtils.isEmpty(major)){
                    Toast.makeText(UIUtils.getContext(),"请填写用户完整信息",Toast.LENGTH_LONG).show();
                }else if (phone.length()!=11){
                    Toast.makeText(UIUtils.getContext(),"手机号格式错误，请重新填写",Toast.LENGTH_LONG).show();
                }else if (!password.equals(password_sure)){
                    Toast.makeText(UIUtils.getContext(),"密码确认错误",Toast.LENGTH_LONG).show();
                }else {
                    AVSMS.verifySMSCodeInBackground(identifying_code, phone, new AVMobilePhoneVerifyCallback() {
                        @Override
                        public void done(AVException e) {
                            if (null == e) {
                             /* 请求成功 */
                                try {
                                    RegisterConnection registerConnection=new RegisterConnection(phone,password,school,major);
                                    registerConnection.start();
                                    registerConnection.join();
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                                if (code==0){
                                    Toast.makeText(UIUtils.getContext(),"恭喜您，注册成功！",Toast.LENGTH_LONG).show();
                                    PrefUtils.putString("phone", "");
                                    PrefUtils.putString("password","");
                                    EMSignUp();
                                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(UIUtils.getContext(),"注册失败",Toast.LENGTH_LONG).show();
                                }
                            } else {
                                /* 请求失败 */
                                Toast.makeText(UIUtils.getContext(),"验证码错误，请尝试重新验证",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                break;
            case R.id.btn_get_sure:
                phone = edit_phone.getText().toString().trim();
                if (StringUtils.isEmpty(phone)){
                    Toast.makeText(UIUtils.getContext(),"请填写注册手机号",Toast.LENGTH_LONG).show();
                }else if (phone.length()!=11){
                    Toast.makeText(UIUtils.getContext(),"手机号格式错误，请重新填写",Toast.LENGTH_LONG).show();
                }else {
                    AVSMSOption option = new AVSMSOption();
                    option.setTtl(10);                     // 验证码有效时间为 10 分钟
                    option.setApplicationName("爱酷课");
                    option.setOperation("注册");
                    AVSMS.requestSMSCodeInBackground(phone, option, new RequestMobileCodeCallback() {
                        @Override
                        public void done(AVException e) {
                            if (null == e) {
                           /* 请求成功 */
                                timeCountUtils=new TimeCountUtils(60000,1000,btn_get_sure);
                                timeCountUtils.start();
                                edit_phone.setFocusable(false);
                                edit_phone.setFocusableInTouchMode(false);
                                Toast.makeText(UIUtils.getContext(),"验证码已发送，注意查收短信",Toast.LENGTH_LONG).show();
                            } else {
                           /* 请求失败 */
                                Toast.makeText(UIUtils.getContext(),"发送验证码失败",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                break;
        }
    }


    private class RegisterConnection extends Thread{
        private String phone,password,school,major;
        public RegisterConnection(final String phone, final String password,final String school,final String major) {
            super();
            this.phone=phone;
            this.password=password;
            this.school=school;
            this.major=major;
        }

        @Override
        public void run() {
            code=new RegisterProtocol(phone,password,school,major).getData();
        }
    }


    private void EMSignUp(){
        ThreadPoolManager.ThreadPool threadPool= ThreadPoolManager.getmThreadPool();
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(edit_phone.getText().toString().trim(),edit_pwd.getText().toString().trim());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e("pgg","注册失败"+e.getErrorCode()+","+e.getMessage());
                }
            }
        });
    }
}


