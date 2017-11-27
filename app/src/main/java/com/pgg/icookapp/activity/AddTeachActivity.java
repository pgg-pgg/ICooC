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
public class AddTeachActivity extends BaseActivity implements View.OnClickListener{

    private TextView text_graduate_time;
    private TextView text_graduate_grade;
    private EditText edit_graduate_school;
    private EditText edit_graduate_major;
    private EditText edit_major_des;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teach);
//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        ImageView iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        ImageView btn_remember=(ImageView)findViewById(R.id.btn_remember);
        btn_remember.setOnClickListener(this);

        text_graduate_time = (TextView)findViewById(R.id.edit_hopejob_name);
        text_graduate_grade = (TextView)findViewById(R.id.text_hopejob_city);
        edit_graduate_school = (EditText)findViewById(R.id.text_hopejob_time);
        edit_graduate_major = (EditText)findViewById(R.id.edit_graduate_major);
        edit_major_des = (EditText)findViewById(R.id.edit_major_des);

        text_graduate_time.setOnClickListener(this);
        text_graduate_grade.setOnClickListener(this);

        text_graduate_time.setText(PrefUtils.getString("teach_time", ""));
        text_graduate_grade.setText(PrefUtils.getString("teach_grade",""));
        edit_graduate_school.setText(PrefUtils.getString("teach_school",""));
        edit_graduate_major.setText(PrefUtils.getString("teach_major",""));
        edit_major_des.setText(PrefUtils.getString("teach_des",""));

    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.iv_back:
                intent=new Intent(AddTeachActivity.this,MyResumeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_remember:
                //保存按钮
                if (StringUtils.isEmpty(text_graduate_time.getText().toString())||StringUtils.isEmpty(text_graduate_grade.getText().toString())
                        ||StringUtils.isEmpty(edit_graduate_school.getText().toString())||StringUtils.isEmpty(edit_graduate_major.getText().toString())||
                        StringUtils.isEmpty(edit_major_des.getText().toString())){
                    Toast.makeText(AddTeachActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                }else {
                    intent=new Intent(AddTeachActivity.this,MyResumeActivity.class);
                    PrefUtils.putString("teach_time", text_graduate_time.getText().toString());
                    PrefUtils.putString("teach_school", edit_graduate_school.getText().toString());
                    PrefUtils.putString("teach_grade", text_graduate_grade.getText().toString());
                    PrefUtils.putString("teach_major", edit_graduate_major.getText().toString());
                    PrefUtils.putString("teach_des", edit_major_des.getText().toString());
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.edit_hopejob_name:
                //选择毕业时间
                new DatePickerDialog(AddTeachActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        text_graduate_time.setText(String.format("%d年",year,monthOfYear+1,dayOfMonth));
                    }
                }, 2017, 6, 1).show();
                break;
            case R.id.text_hopejob_city:
                showDialogGraduate_grade();
                break;

        }
    }

    private void showDialogGraduate_grade(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        View view= UIUtils.getView(R.layout.dialog_graduate_grade);
        RadioGroup rg_graduate=(RadioGroup)view.findViewById(R.id.rg_graduate);
        final RadioButton rb_zhuanke=(RadioButton)rg_graduate.findViewById(R.id.rb_zhuanke);
        final RadioButton rb_benke=(RadioButton)rg_graduate.findViewById(R.id.rb_benke);
        final RadioButton rb_yanjiushen=(RadioButton)rg_graduate.findViewById(R.id.rb_yanjiushen);
        final RadioButton rb_boshishen=(RadioButton)rg_graduate.findViewById(R.id.rb_boshishen);
        rg_graduate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_zhuanke:
                        text_graduate_grade.setText(rb_zhuanke.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_benke:
                        text_graduate_grade.setText(rb_benke.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_yanjiushen:
                        text_graduate_grade.setText(rb_yanjiushen.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_boshishen:
                        text_graduate_grade.setText(rb_boshishen.getText());
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
