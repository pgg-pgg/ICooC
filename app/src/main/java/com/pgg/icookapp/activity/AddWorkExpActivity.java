package com.pgg.icookapp.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.PrefUtils;
import com.pgg.icookapp.Utils.StringUtils;

/**
 * Created by PDD on 2017/9/12.
 */
public class AddWorkExpActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_work_begin_time;
    private TextView tv_work_end_time;
    private EditText edit_work_company;
    private EditText edit_work_position;
    private EditText edit_add_work_content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_exp);
//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        ImageView iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        ImageView btn_remember=(ImageView)findViewById(R.id.btn_remember);
        btn_remember.setOnClickListener(this);

        tv_work_begin_time = (TextView)findViewById(R.id.tv_work_begin_time);
        tv_work_end_time = (TextView)findViewById(R.id.tv_work_end_time);
        edit_work_company = (EditText)findViewById(R.id.edit_work_company);
        edit_work_position = (EditText)findViewById(R.id.edit_work_position);
        edit_add_work_content = (EditText)findViewById(R.id.edit_add_work_content);

        tv_work_begin_time.setOnClickListener(this);
        tv_work_end_time.setOnClickListener(this);

        tv_work_end_time.setText(PrefUtils.getString("work_end_time", ""));
        tv_work_begin_time.setText(PrefUtils.getString("work_begin_time",""));
        edit_work_company.setText(PrefUtils.getString("work_company",""));
        edit_work_position.setText(PrefUtils.getString("work_position",""));
        edit_add_work_content.setText(PrefUtils.getString("work_content",""));


    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.iv_back:
                intent=new Intent(AddWorkExpActivity.this,MyResumeActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_remember:
                //保存按钮
                if (StringUtils.isEmpty(tv_work_end_time.getText().toString())||StringUtils.isEmpty(tv_work_begin_time.getText().toString())
                        ||StringUtils.isEmpty(edit_work_company.getText().toString())||StringUtils.isEmpty(edit_work_position.getText().toString())||
                        StringUtils.isEmpty(edit_add_work_content.getText().toString())){
                    Toast.makeText(AddWorkExpActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                }else {
                    intent=new Intent(AddWorkExpActivity.this,MyResumeActivity.class);
                    PrefUtils.putString("work_end_time", tv_work_end_time.getText().toString());
                    PrefUtils.putString("work_begin_time", tv_work_begin_time.getText().toString());
                    PrefUtils.putString("work_company", edit_work_company.getText().toString());
                    PrefUtils.putString("work_position", edit_work_position.getText().toString());
                    PrefUtils.putString("work_content", edit_add_work_content.getText().toString());
                    startActivity(intent);
                    finish();
                }
                break;

            case R.id.tv_work_begin_time:
                new DatePickerDialog(AddWorkExpActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_work_begin_time.setText(String.format("%d-%d",year,monthOfYear+1));
                        PrefUtils.putString("workBeginYear", year + "");
                        PrefUtils.putString("workBeginMonth",(monthOfYear+1)+"");
                    }
                }, 2015, 0, 1).show();
                break;
            case R.id.tv_work_end_time:
                new DatePickerDialog(AddWorkExpActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_work_end_time.setText(String.format("%d-%d",year,monthOfYear+1));
                        PrefUtils.putString("workEndYear", year + "");
                        PrefUtils.putString("workEndMonth", (monthOfYear + 1) + "");
                    }
                }, 2015, 0, 1).show();
                break;
        }
    }
}
