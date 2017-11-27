package com.pgg.icookapp.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.PrefUtils;
import com.pgg.icookapp.Utils.StringUtils;
import com.pgg.icookapp.Utils.UIUtils;

/**
 * Created by PDD on 2017/9/7.
 */
public class AddExpActivity extends BaseActivity implements View.OnClickListener {

    private EditText edit_exp_name;
    private EditText edit_exp_duty;
    private EditText edit_exp_des;
    private TextView text_exp_begin;
    private TextView text_exp_end;
    private String begin_time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exp);
//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        ImageView iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        ImageView btn_remember=(ImageView)findViewById(R.id.btn_remember);
        btn_remember.setOnClickListener(this);

        edit_exp_name = (EditText)findViewById(R.id.edit_hopejob_name);
        edit_exp_duty = (EditText)findViewById(R.id.text_hopejob_time);
        edit_exp_des = (EditText)findViewById(R.id.edit_exp_des);

        text_exp_begin = (TextView)findViewById(R.id.text_hopejob_city);
        text_exp_end = (TextView)findViewById(R.id.text_hopejob_money);

        text_exp_begin.setOnClickListener(this);
        text_exp_end.setOnClickListener(this);

        edit_exp_name.setText(PrefUtils.getString("exp_name", ""));
        edit_exp_duty.setText(PrefUtils.getString("exp_duty", ""));
        edit_exp_des.setText(PrefUtils.getString("exp_des", ""));

        text_exp_begin.setText(PrefUtils.getString("exp_begin", ""));
        text_exp_end.setText(PrefUtils.getString("exp_end", ""));

    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.iv_back:
                intent=new Intent(AddExpActivity.this,MyResumeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_remember:
                //保存按钮
                if (StringUtils.isEmpty(edit_exp_name.getText().toString())||StringUtils.isEmpty(edit_exp_duty.getText().toString())
                        ||StringUtils.isEmpty(edit_exp_des.getText().toString())||StringUtils.isEmpty(text_exp_begin.getText().toString())||
                        StringUtils.isEmpty(text_exp_end.getText().toString())){
                    Toast.makeText(AddExpActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                }else {
                    intent=new Intent(AddExpActivity.this,MyResumeActivity.class);
                    PrefUtils.putString("exp_name", edit_exp_name.getText().toString());
                    PrefUtils.putString("exp_duty", edit_exp_duty.getText().toString());
                    PrefUtils.putString("exp_des", edit_exp_des.getText().toString());
                    if (!StringUtils.isEmpty(begin_time)){
                        PrefUtils.putString("exp_begin",begin_time);
                    }
                    PrefUtils.putString("exp_end", text_exp_end.getText().toString());
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.text_hopejob_city:
                new DatePickerDialog(AddExpActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        begin_time=String.format("%d-%d",year,monthOfYear+1);
                        text_exp_begin.setText(begin_time+"项目开始");
                        PrefUtils.putString("exp_year", year + "");
                        PrefUtils.putString("exp_month",(monthOfYear+1)+"");
                    }
                }, 2016, 0, 1).show();
                break;
            case R.id.text_hopejob_money:
                showDialogSet_EndTime();
                break;
        }
    }

    private void showDialogSet_EndTime(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        View view= UIUtils.getView(R.layout.dialog_set_end_time);
        RadioGroup rg_grade=(RadioGroup)view.findViewById(R.id.rg_end_time);
        final RadioButton rb_2012=(RadioButton)rg_grade.findViewById(R.id.rb_2012);
        final RadioButton rb_2013=(RadioButton)rg_grade.findViewById(R.id.rb_2013);
        final RadioButton rb_2014=(RadioButton)rg_grade.findViewById(R.id.rb_2014);
        final RadioButton rb_2015=(RadioButton)rg_grade.findViewById(R.id.rb_2015);
        final RadioButton rb_2016=(RadioButton)rg_grade.findViewById(R.id.rb_2016);
        final RadioButton rb_zhijin=(RadioButton)rg_grade.findViewById(R.id.rb_zhijin);
        rg_grade.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_2012:
                        text_exp_end.setText(rb_2012.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_2013:
                        text_exp_end.setText(rb_2013.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_2014:
                        text_exp_end.setText(rb_2014.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_2015:
                        text_exp_end.setText(rb_2015.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_2016:
                        text_exp_end.setText(rb_2016.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_zhijin:
                        text_exp_end.setText(rb_zhijin.getText());
                        alertDialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }
}
