package com.pgg.icookapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.PrefUtils;
import com.pgg.icookapp.Utils.StringUtils;
import com.pgg.icookapp.Utils.UIUtils;

/**
 * Created by PDD on 2017/9/7.
 */
public class MyResumeActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout container_basedes;
    private FrameLayout container_exp;
    private FrameLayout container_teach;
    private FrameLayout container_hopejob;
    private FrameLayout container_work;
    private Button btn_add_basedes;
    private Button btn_add_exp;
    private Button btn_add_teach;
    private Button btn_add_hopejob;
    private Button btn_add_work;
    private TextView tv_base_name;
    private TextView tv_base_sex;
    private TextView tv_base_birth;
    private TextView tv_base_grade;
    private TextView tv_base_phone;
    private TextView tv_base_email;
    private String begin_time,end_time;
    private TextView tv_project_name;
    private TextView tv_project_duty;
    private TextView tv_project_des;
    private TextView tv_gtime_content;
    private TextView tv_school_content;
    private TextView tv_grade_content;
    private TextView tv_major_des;
    private TextView tv_hopejob_name;
    private TextView tv_hopejob_des;
    private TextView tv_hopejob_add;
    private TextView tv_work_time_content;
    private TextView tv_work_company_content;
    private TextView tv_work_position_content;
    private TextView tv_work_content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        ImageView iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        ImageView btn_prelook=(ImageView)findViewById(R.id.btn_prelook);
        btn_prelook.setOnClickListener(this);

        container_basedes = (FrameLayout)findViewById(R.id.container_basedes);
        container_exp = (FrameLayout)findViewById(R.id.container_exp);
        container_teach = (FrameLayout)findViewById(R.id.container_teach);
        container_hopejob = (FrameLayout)findViewById(R.id.container_hopejob);
        container_work = (FrameLayout)findViewById(R.id.container_work);

        btn_add_basedes = (Button)findViewById(R.id.btn_add_basedes);
        btn_add_exp = (Button)findViewById(R.id.btn_add_exp);
        btn_add_teach = (Button)findViewById(R.id.btn_add_teach);
        btn_add_hopejob = (Button)findViewById(R.id.btn_add_hopejob);
        btn_add_work = (Button)findViewById(R.id.btn_add_work);

        btn_add_basedes.setOnClickListener(this);
        btn_add_exp.setOnClickListener(this);
        btn_add_teach.setOnClickListener(this);
        btn_add_hopejob.setOnClickListener(this);
        btn_add_work.setOnClickListener(this);

        if (!StringUtils.isEmpty(PrefUtils.getString("base_name",""))){
            setViewOfBaseDes();
        }
        if (!StringUtils.isEmpty(PrefUtils.getString("exp_name", ""))){
            setViewOfExpDes();
        }
        if (!StringUtils.isEmpty(PrefUtils.getString("teach_school", ""))){
            setViewOfTeachDes();
        }
        if (!StringUtils.isEmpty(PrefUtils.getString("hopejob_name",""))){
            setViewOfHopeJob();
        }

        if (!StringUtils.isEmpty(PrefUtils.getString("work_company",""))){
            setViewOfWorkExp();
        }

    }

    private void setViewOfHopeJob(){
        btn_add_hopejob.setVisibility(View.GONE);
        View view=UIUtils.getView(R.layout.view_hope_job);
        tv_hopejob_name = (TextView)view.findViewById(R.id.tv_hopejob_name);
        tv_hopejob_des = (TextView)view.findViewById(R.id.tv_hopejob_des);
        tv_hopejob_add = (TextView)view.findViewById(R.id.tv_hopejob_add);

        tv_hopejob_name.setText(PrefUtils.getString("hopejob_name", ""));
        tv_hopejob_add.setText(PrefUtils.getString("hopejob_info", ""));
        tv_hopejob_des.setText(PrefUtils.getString("hopejob_time", "")+"/"+PrefUtils.getString("hopejob_city","")+"/"+PrefUtils.getString("hopejob_money",""));

        TextView tv_hopejob_edit=(TextView)view.findViewById(R.id.tv_hopejob_edit);
        tv_hopejob_edit.setOnClickListener(this);

        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        container_hopejob.addView(view,params);
    }
    private void setViewOfTeachDes(){
        btn_add_teach.setVisibility(View.GONE);
        View view=UIUtils.getView(R.layout.view_teach_des);
        tv_gtime_content = (TextView)view.findViewById(R.id.tv_gtime_content);
        tv_school_content = (TextView)view.findViewById(R.id.tv_school_content);
        tv_grade_content = (TextView)view.findViewById(R.id.tv_grade_content);
        tv_major_des = (TextView)view.findViewById(R.id.tv_major_des);

        tv_gtime_content.setText(PrefUtils.getString("teach_time", ""));
        tv_school_content.setText(PrefUtils.getString("teach_school", ""));
        tv_major_des.setText(PrefUtils.getString("teach_des", ""));
        tv_grade_content.setText(PrefUtils.getString("teach_grade", "")+"-"+PrefUtils.getString("teach_major",""));

        TextView tv_teach_edit=(TextView)view.findViewById(R.id.tv_teach_edit);
        tv_teach_edit.setOnClickListener(this);

        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        container_teach.addView(view,params);
    }
    private void setViewOfExpDes(){
        btn_add_exp.setVisibility(View.GONE);
        View view=UIUtils.getView(R.layout.view_exp_des);
        TextView tv_time_content=(TextView)view.findViewById(R.id.tv_gtime_content);
        tv_project_name = (TextView)view.findViewById(R.id.tv_school_content);
        tv_project_duty = (TextView)view.findViewById(R.id.tv_grade_content);
        tv_project_des = (TextView)view.findViewById(R.id.tv_major_des);

        begin_time=PrefUtils.getString("exp_begin", "");
        end_time=PrefUtils.getString("exp_end", "");
        tv_time_content.setText(begin_time+"~"+end_time);
        tv_project_name.setText(PrefUtils.getString("exp_name", ""));
        tv_project_duty.setText(PrefUtils.getString("exp_duty", ""));
        tv_project_des.setText(PrefUtils.getString("exp_des", ""));

        TextView tv_exp_edit=(TextView)view.findViewById(R.id.tv_exp_edit);
        tv_exp_edit.setOnClickListener(this);

        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        container_exp.addView(view,params);
    }
    private void setViewOfBaseDes(){
        btn_add_basedes.setVisibility(View.GONE);
        View view=UIUtils.getView(R.layout.view_base_des);
        tv_base_name = (TextView)view.findViewById(R.id.tv_base_name);
        tv_base_sex = (TextView)view.findViewById(R.id.tv_school_content);
        tv_base_birth = (TextView)view.findViewById(R.id.tv_grade_content);
        tv_base_grade = (TextView)view.findViewById(R.id.tv_base_grade);
        tv_base_phone = (TextView)view.findViewById(R.id.tv_base_phone);
        tv_base_email = (TextView)view.findViewById(R.id.tv_base_email);

        tv_base_name.setText(PrefUtils.getString("base_name", ""));
        tv_base_phone.setText(PrefUtils.getString("base_phone", ""));
        tv_base_email.setText(PrefUtils.getString("base_email", ""));
        tv_base_sex.setText(PrefUtils.getString("base_sex", ""));
        tv_base_birth.setText(PrefUtils.getString("base_birth", ""));
        tv_base_grade.setText(PrefUtils.getString("base_grade", ""));

        TextView tv_base_edit=(TextView)view.findViewById(R.id.tv_base_edit);
        tv_base_edit.setOnClickListener(this);

        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        container_basedes.addView(view,params);
    }
    private void setViewOfWorkExp(){
        btn_add_basedes.setVisibility(View.GONE);
        View view=UIUtils.getView(R.layout.view_work_exp);
        tv_work_time_content = (TextView)view.findViewById(R.id.tv_work_time_content);
        tv_work_company_content = (TextView)view.findViewById(R.id.tv_work_company_content);
        tv_work_position_content = (TextView)view.findViewById(R.id.tv_work_position_content);
        tv_work_content = (TextView)view.findViewById(R.id.tv_work_content);

        tv_work_time_content.setText(PrefUtils.getString("work_begin_time", "")+"~"+PrefUtils.getString("work_end_time", ""));
        tv_work_company_content.setText(PrefUtils.getString("work_company", ""));
        tv_work_position_content.setText(PrefUtils.getString("work_position", ""));
        tv_work_content.setText(PrefUtils.getString("work_content", ""));

        TextView tv_work_edit=(TextView)view.findViewById(R.id.tv_work_edit);
        tv_work_edit.setOnClickListener(this);

        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        container_work.addView(view,params);
    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_prelook:
                //预览被点击
                intent=new Intent(UIUtils.getContext(),PreSeeResumeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_add_basedes:
                //添加基本信息被点击
                intent=new Intent(UIUtils.getContext(),AddBaseDesActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_add_exp:
                //添加项目经历被点击
                intent=new Intent(UIUtils.getContext(),AddExpActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_add_teach:
                //添加教育经历被点击
                intent=new Intent(UIUtils.getContext(),AddTeachActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_add_hopejob:
                //添加期望工作被点击
                intent=new Intent(UIUtils.getContext(),AddHopeJobActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_add_work:
                //添加工作经历被点击
                intent=new Intent(UIUtils.getContext(),AddWorkExpActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_base_edit:
                //编辑基本信息按钮被点击
                intent=new Intent(UIUtils.getContext(),AddBaseDesActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_exp_edit:
                //编辑项目经历按钮被点击
                intent=new Intent(UIUtils.getContext(),AddExpActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.tv_teach_edit:
                //编辑教育经历按钮被点击
                intent=new Intent(UIUtils.getContext(),AddTeachActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_hopejob_edit:
                //编辑教育经历按钮被点击
                intent=new Intent(UIUtils.getContext(),AddHopeJobActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_work_edit:
                intent=new Intent(UIUtils.getContext(),AddWorkExpActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
