package com.pgg.icookapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.PrefUtils;
import com.pgg.icookapp.Utils.StringUtils;
import com.pgg.icookapp.Utils.TimeCountUtils;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.http.protocol.ForgetPwdProtocol;
import com.pgg.icookapp.http.protocol.RegisterProtocol;

/**
 * Created by PDD on 2017/7/26.
 */
public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_title;
    private ImageButton btn_menu;
    private EditText edit_update_phone,edit_update_identity;
    private EditText edit_update_pwd;
    private Button btn_update_by_msg;
    private Button btn_update;
    private int code;

    private String phone,identity_code,newPassword;
    private TimeCountUtils timeCountUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);

//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        tv_title = (TextView)findViewById(R.id.tv_title);
        btn_menu = (ImageButton)findViewById(R.id.btn_menu);

        tv_title.setText("忘记密码");
        btn_menu.setBackgroundResource(R.drawable.item_left);

        edit_update_phone = (EditText)findViewById(R.id.edit_update_phone);
        edit_update_pwd = (EditText)findViewById(R.id.edit_update_pwd);
        edit_update_identity=(EditText)findViewById(R.id.edit_update_identity);

        btn_update_by_msg = (Button)findViewById(R.id.btn_update_by_msg);
        btn_update = (Button)findViewById(R.id.btn_update);

        btn_menu.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_update_by_msg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_menu:
                finish();
                break;
            case R.id.btn_update:
                phone=edit_update_phone.getText().toString().trim();
                identity_code=edit_update_identity.getText().toString().trim();
                newPassword=edit_update_pwd.getText().toString().trim();
                if (StringUtils.isEmpty(phone)||StringUtils.isEmpty(identity_code)||StringUtils.isEmpty(newPassword)){
                    Toast.makeText(UIUtils.getContext(),"请填写用户完整信息",Toast.LENGTH_LONG).show();
                }else if (phone.length()!=11){
                    Toast.makeText(UIUtils.getContext(),"手机号格式错误，请重新填写",Toast.LENGTH_LONG).show();
                }else {
                    AVSMS.verifySMSCodeInBackground(identity_code, phone, new AVMobilePhoneVerifyCallback() {
                        @Override
                        public void done(AVException e) {
                            if (null == e) {
                             /* 请求成功 */
                                try {
                                    ForgetPwdConnection forgetPwdConnection=new ForgetPwdConnection(phone,newPassword);
                                    forgetPwdConnection.start();
                                    forgetPwdConnection.join();
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                                if (code==0){
                                    Toast.makeText(UIUtils.getContext(),"密码修改成功！",Toast.LENGTH_LONG).show();
                                    PrefUtils.putString("phone", "");
                                    PrefUtils.putString("password","");
                                    Intent intent=new Intent(ForgetPwdActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(UIUtils.getContext(),"密码修改失败，请重试",Toast.LENGTH_LONG).show();
                                }
                            } else {
                             /* 请求失败 */
                                Toast.makeText(UIUtils.getContext(),"验证码错误，请尝试重新验证",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                break;
            case R.id.btn_update_by_msg:
                phone = edit_update_phone.getText().toString().trim();
                if (StringUtils.isEmpty(phone)){
                    Toast.makeText(UIUtils.getContext(), "请填写注册手机号", Toast.LENGTH_LONG).show();
                }else if (phone.length()!=11){
                    Toast.makeText(UIUtils.getContext(),"手机号格式错误，请重新填写",Toast.LENGTH_LONG).show();
                }else {
                    AVSMSOption option = new AVSMSOption();
                    option.setTtl(10);                     // 验证码有效时间为 10 分钟
                    option.setApplicationName("爱酷课");
                    option.setOperation("修改密码");
                    AVSMS.requestSMSCodeInBackground(phone, option, new RequestMobileCodeCallback() {
                        @Override
                        public void done(AVException e) {
                            if (null == e) {
                                /* 请求成功 */
                                timeCountUtils = new TimeCountUtils(60000, 1000, btn_update_by_msg);
                                timeCountUtils.start();
                                edit_update_phone.setFocusable(false);
                                edit_update_phone.setFocusableInTouchMode(false);
                                Toast.makeText(UIUtils.getContext(), "验证码已发送，注意查收短信", Toast.LENGTH_LONG).show();
                            } else {
                                /* 请求失败 */
                                Toast.makeText(UIUtils.getContext(), "发送验证码失败", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    private class ForgetPwdConnection extends Thread{
        private String phone,newPassword;
        public ForgetPwdConnection(final String phone, final String newPassword) {
            super();
            this.phone=phone;
            this.newPassword=newPassword;
        }
        @Override
        public void run() {
            code=new ForgetPwdProtocol(phone,newPassword).getData();
        }
    }
}
