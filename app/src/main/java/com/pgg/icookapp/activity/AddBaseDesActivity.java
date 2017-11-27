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
public class AddBaseDesActivity extends BaseActivity  implements View.OnClickListener{

    private EditText edit_base_name;
    private EditText edit_base_num;
    private EditText edit_base_email;
    private TextView text_base_sex;
    private TextView text_base_birth;
    private TextView text_base_grade;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_basedes);
//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        ImageView iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        ImageView btn_remember=(ImageView)findViewById(R.id.btn_remember);
        btn_remember.setOnClickListener(this);

        edit_base_name = (EditText)findViewById(R.id.edit_base_name);
        edit_base_num = (EditText)findViewById(R.id.edit_base_num);
        edit_base_email = (EditText)findViewById(R.id.edit_base_email);

        text_base_sex = (TextView)findViewById(R.id.text_base_sex);
        text_base_birth = (TextView)findViewById(R.id.text_base_birth);
        text_base_grade = (TextView)findViewById(R.id.text_base_grade);

        text_base_sex.setOnClickListener(this);
        text_base_birth.setOnClickListener(this);
        text_base_grade.setOnClickListener(this);

        edit_base_name.setText(PrefUtils.getString("base_name",""));
        edit_base_num.setText(PrefUtils.getString("base_phone",""));
        edit_base_email.setText(PrefUtils.getString("base_email", ""));
        text_base_sex.setText(PrefUtils.getString("base_sex", ""));
        text_base_birth.setText(PrefUtils.getString("base_birth", ""));
        text_base_grade.setText(PrefUtils.getString("base_grade", ""));
    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()) {
            case R.id.iv_back:
                intent=new Intent(AddBaseDesActivity.this,MyResumeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_remember:
                //保存按钮
                if (StringUtils.isEmpty(edit_base_name.getText().toString())||StringUtils.isEmpty(text_base_sex.getText().toString())
                        ||StringUtils.isEmpty(text_base_birth.getText().toString())||StringUtils.isEmpty(text_base_grade.getText().toString())||
                        StringUtils.isEmpty(edit_base_num.getText().toString())||StringUtils.isEmpty(edit_base_email.getText().toString())){
                    Toast.makeText(AddBaseDesActivity.this,"请填写完整信息",Toast.LENGTH_SHORT).show();
                }else {
                    intent=new Intent(AddBaseDesActivity.this,MyResumeActivity.class);
                    PrefUtils.putString("base_name",edit_base_name.getText().toString());
                    PrefUtils.putString("base_sex", text_base_sex.getText().toString());
                    PrefUtils.putString("base_birth", text_base_birth.getText().toString());
                    PrefUtils.putString("base_grade", text_base_grade.getText().toString());
                    PrefUtils.putString("base_phone", edit_base_num.getText().toString());
                    PrefUtils.putString("base_email", edit_base_email.getText().toString());
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.text_base_sex:
                //选择性别
                showDialogSet_sex();
                break;
            case R.id.text_base_birth:
                //选择出生年月
                new DatePickerDialog(AddBaseDesActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        text_base_birth.setText(String.format("%d-%d-%d",year,monthOfYear+1,dayOfMonth));
                        PrefUtils.putString("base_birth_year", year + "");
                        PrefUtils.putString("base_birth_month",(monthOfYear+1)+"");
                    }
                }, 1997, 0, 1).show();
                break;
            case R.id.text_base_grade:
                //选择年级
                showDialogSet_grade();
                break;
        }
    }

    private void showDialogSet_sex(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        View view= UIUtils.getView(R.layout.dialog_set_sex);
        RadioGroup rg_sex=(RadioGroup)view.findViewById(R.id.rg_sex);
        final RadioButton rb_man=(RadioButton)rg_sex.findViewById(R.id.rb_man);
        final RadioButton rb_women=(RadioButton)rg_sex.findViewById(R.id.rb_women);
        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_man:
                        text_base_sex.setText(rb_man.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_women:
                        text_base_sex.setText(rb_women.getText());
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

    private void showDialogSet_grade(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        View view= UIUtils.getView(R.layout.dialog_set_grade);
        RadioGroup rg_grade=(RadioGroup)view.findViewById(R.id.rg_grade);
        final RadioButton rb_dayi=(RadioButton)rg_grade.findViewById(R.id.rb_dayi);
        final RadioButton rb_daer=(RadioButton)rg_grade.findViewById(R.id.rb_daer);
        final RadioButton rb_dasan=(RadioButton)rg_grade.findViewById(R.id.rb_dasan);
        final RadioButton rb_dasi=(RadioButton)rg_grade.findViewById(R.id.rb_dasi);
        final RadioButton rb_yanyi=(RadioButton)rg_grade.findViewById(R.id.rb_yanyi);
        final RadioButton rb_yaner=(RadioButton)rg_grade.findViewById(R.id.rb_yaner);
        rg_grade.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_dayi:
                        text_base_grade.setText(rb_dayi.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_daer:
                        text_base_grade.setText(rb_daer.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_dasan:
                        text_base_grade.setText(rb_dasan.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_dasi:
                        text_base_grade.setText(rb_dasi.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_yanyi:
                        text_base_grade.setText(rb_yanyi.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_yaner:
                        text_base_grade.setText(rb_yaner.getText());
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
