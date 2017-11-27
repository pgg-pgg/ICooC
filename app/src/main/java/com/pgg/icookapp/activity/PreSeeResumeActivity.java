package com.pgg.icookapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pgg.icookapp.Manager.ThreadPoolManager;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.PostJsonUtils;
import com.pgg.icookapp.Utils.PrefUtils;
import com.pgg.icookapp.Utils.StringUtils;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.domain.ResumeInfo;
import com.pgg.icookapp.http.protocol.SubmitResumeProtocol;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by PDD on 2017/9/7.
 */
public class PreSeeResumeActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_base_name;
    private TextView tv_presee_sex_content;
    private TextView tv_presee_birth_content;
    private TextView tv_presee_grade_content;
    private TextView tv_presee_phone_content;
    private TextView tv_presee_email_content;
    private TextView tv_exp_time_content;
    private TextView tv_exp_name_content;
    private TextView tv_exp_duty_content;
    private TextView tv_major_des;
    private TextView tv_gradute_time_content;
    private TextView tv_school_content;
    private TextView tv_grade_content;
    private TextView tv_major_good;
    private TextView tv_hopejob_name;
    private TextView tv_hopejob_des;
    private TextView tv_hope_job_add;
    private TextView tv_prework_time_content;
    private TextView tv_prework_company_content;
    private TextView tv_prework_content;
    private TextView tv_prework_position_content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presee);
//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        ImageView iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView btn_send=(TextView)findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        initView();

    }

    private void initView(){
        tv_base_name = (TextView)findViewById(R.id.tv_base_name);
        tv_presee_sex_content = (TextView)findViewById(R.id.tv_presee_sex_content);
        tv_presee_birth_content = (TextView)findViewById(R.id.tv_presee_birth_content);
        tv_presee_grade_content = (TextView)findViewById(R.id.tv_presee_grade_content);
        tv_presee_phone_content = (TextView)findViewById(R.id.tv_presee_phone_content);
        tv_presee_email_content = (TextView)findViewById(R.id.tv_presee_email_content);
        tv_exp_time_content = (TextView)findViewById(R.id.tv_exp_time_content);
        tv_exp_name_content = (TextView)findViewById(R.id.tv_exp_name_content);
        tv_exp_duty_content = (TextView)findViewById(R.id.tv_exp_duty_content);
        tv_major_des = (TextView)findViewById(R.id.tv_major_des);
        tv_gradute_time_content = (TextView)findViewById(R.id.tv_gradute_time_content);
        tv_school_content = (TextView)findViewById(R.id.tv_school_content);
        tv_grade_content = (TextView)findViewById(R.id.tv_grade_content);
        tv_major_good = (TextView)findViewById(R.id.tv_major_good);
        tv_hopejob_name = (TextView)findViewById(R.id.tv_hopejob_name);
        tv_hopejob_des = (TextView)findViewById(R.id.tv_hopejob_des);
        tv_hope_job_add = (TextView)findViewById(R.id.tv_hope_job_add);
        tv_prework_time_content = (TextView)findViewById(R.id.tv_prework_time_content);
        tv_prework_company_content = (TextView)findViewById(R.id.tv_prework_company_content);
        tv_prework_position_content = (TextView)findViewById(R.id.tv_prework_position_content);
        tv_prework_content = (TextView)findViewById(R.id.tv_prework_content);


        tv_base_name.setText(PrefUtils.getString("base_name", ""));
        tv_presee_sex_content.setText(PrefUtils.getString("base_sex", ""));
        tv_presee_birth_content.setText(PrefUtils.getString("base_birth", ""));
        tv_presee_grade_content.setText(PrefUtils.getString("base_grade", ""));
        tv_presee_phone_content.setText(PrefUtils.getString("base_phone", ""));
        tv_presee_email_content.setText(PrefUtils.getString("base_email", ""));

        tv_exp_time_content.setText(PrefUtils.getString("exp_begin", "") + "~" + PrefUtils.getString("exp_end", ""));
        tv_exp_name_content.setText(PrefUtils.getString("exp_name", ""));
        tv_exp_duty_content.setText(PrefUtils.getString("exp_duty", ""));
        tv_major_des.setText(PrefUtils.getString("exp_des", ""));

        tv_gradute_time_content.setText(PrefUtils.getString("teach_time", ""));
        tv_school_content.setText(PrefUtils.getString("teach_school", ""));
        tv_grade_content.setText(PrefUtils.getString("teach_grade", "") + "-" + PrefUtils.getString("teach_major", ""));
        tv_major_good.setText(PrefUtils.getString("teach_des", ""));

        tv_hopejob_name.setText(PrefUtils.getString("hopejob_name", ""));
        String s=PrefUtils.getString("hopejob_time", "");
        if (StringUtils.isEmpty(s)){
            tv_hopejob_des.setText("");
        }else {
            tv_hopejob_des.setText(s + "/" + PrefUtils.getString("hopejob_city", "") + "/" + PrefUtils.getString("hopejob_money", ""));
        }
        tv_hope_job_add.setText(PrefUtils.getString("hopejob_info", ""));

        tv_prework_time_content.setText(PrefUtils.getString("work_begin_time", "") + "~" + PrefUtils.getString("work_end_time", ""));
        tv_prework_company_content.setText(PrefUtils.getString("work_company", ""));
        tv_prework_position_content.setText(PrefUtils.getString("work_position", ""));
        tv_prework_content.setText(PrefUtils.getString("work_content", ""));

    }
    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.iv_back:
                intent=new Intent(UIUtils.getContext(),MyResumeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_send:
                ThreadPoolManager.getmThreadPool().execute(new Runnable() {
                    URL url = null;

                    @Override
                    public void run() {
//                        SubmitResumeProtocol submitResumeProtocol = new SubmitResumeProtocol(LoginActivity.myLoginInfo.id,PostJsonUtils.resumeJson());
//                        if (submitResumeProtocol.getData() == 0) {
//                            UIUtils.RunningMainThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(PreSeeResumeActivity.this, "简历已发送", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        } else {
//                            UIUtils.RunningMainThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(PreSeeResumeActivity.this, "简历发送失败", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
                        if (new PostJsonUtils().String2Json() == 0) {
                            UIUtils.RunningMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PreSeeResumeActivity.this, "简历已发送", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            UIUtils.RunningMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PreSeeResumeActivity.this, "简历发送失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                });
                break;
        }
    }




}
