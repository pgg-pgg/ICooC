package com.pgg.icookapp.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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
public class AddHopeJobActivity extends BaseActivity implements View.OnClickListener {

    private EditText edit_hopejob_name;
    private EditText edit_add_info;
    private TextView text_hopejob_time;
    private TextView text_hopejob_city;
    private TextView text_hopejob_money;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hopejob);
//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        ImageView iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        ImageView btn_remember=(ImageView)findViewById(R.id.btn_remember);
        btn_remember.setOnClickListener(this);

        edit_hopejob_name = (EditText)findViewById(R.id.edit_hopejob_name);
        edit_add_info = (EditText)findViewById(R.id.edit_add_info);
        text_hopejob_time = (TextView)findViewById(R.id.text_hopejob_time);
        text_hopejob_city = (TextView)findViewById(R.id.text_hopejob_city);
        text_hopejob_money = (TextView)findViewById(R.id.text_hopejob_money);

        text_hopejob_time.setOnClickListener(this);
        text_hopejob_city.setOnClickListener(this);
        text_hopejob_money.setOnClickListener(this);

        edit_hopejob_name.setText(PrefUtils.getString("hopejob_name",""));
        edit_add_info.setText(PrefUtils.getString("hopejob_info",""));
        text_hopejob_time.setText(PrefUtils.getString("hopejob_time",""));
        text_hopejob_city.setText(PrefUtils.getString("hopejob_city",""));
        text_hopejob_money.setText(PrefUtils.getString("hopejob_money",""));

    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.iv_back:
                intent=new Intent(AddHopeJobActivity.this,MyResumeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_remember:
                //保存按钮
                if (StringUtils.isEmpty(edit_hopejob_name.getText().toString()) ||StringUtils.isEmpty(text_hopejob_time.getText().toString())
                        ||StringUtils.isEmpty(text_hopejob_city.getText().toString())||
                        StringUtils.isEmpty(text_hopejob_money.getText().toString())){
                    Toast.makeText(AddHopeJobActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                }else {
                    intent=new Intent(AddHopeJobActivity.this,MyResumeActivity.class);
                    PrefUtils.putString("hopejob_name", edit_hopejob_name.getText().toString());
                    PrefUtils.putString("hopejob_info", edit_add_info.getText().toString());
                    PrefUtils.putString("hopejob_time", text_hopejob_time.getText().toString());
                    PrefUtils.putString("hopejob_city", text_hopejob_city.getText().toString());
                    PrefUtils.putString("hopejob_money", text_hopejob_money.getText().toString());
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.text_hopejob_time:
                showDialogTime();
                break;
            case R.id.text_hopejob_city:
                intent=new Intent(UIUtils.getContext(),CitySelectActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.text_hopejob_money:
                showDialogMoney();
                break;

        }
    }

    private void showDialogTime(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        View view= UIUtils.getView(R.layout.dialog_hopejob_time);
        RadioGroup rg_time=(RadioGroup)view.findViewById(R.id.rg_time);
        final RadioButton rb_quanzhi=(RadioButton)rg_time.findViewById(R.id.rb_quanzhi);
        final RadioButton rb_jianzhi=(RadioButton)rg_time.findViewById(R.id.rb_jianzhi);
        final RadioButton rb_shixi=(RadioButton)rg_time.findViewById(R.id.rb_shixi);
        rg_time.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_quanzhi:
                        text_hopejob_time.setText(rb_quanzhi.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_jianzhi:
                        text_hopejob_time.setText(rb_jianzhi.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_shixi:
                        text_hopejob_time.setText(rb_shixi.getText());
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
    private void showDialogMoney(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        View view= UIUtils.getView(R.layout.dialog_hopejob_money);
        RadioGroup rg_money=(RadioGroup)view.findViewById(R.id.rg_money);
        final RadioButton rb_2k=(RadioButton)rg_money.findViewById(R.id.rb_2k);
        final RadioButton rb_2k_5k=(RadioButton)rg_money.findViewById(R.id.rb_2k_5k);
        final RadioButton rb_10k_15k=(RadioButton)rg_money.findViewById(R.id.rb_10k_15k);
        final RadioButton rb_15k_25k=(RadioButton)rg_money.findViewById(R.id.rb_15k_25k);
        final RadioButton rb_25k_50k=(RadioButton)rg_money.findViewById(R.id.rb_25k_50k);
        final RadioButton rb_5k_10k=(RadioButton)rg_money.findViewById(R.id.rb_5k_10k);
        final RadioButton rb_50k=(RadioButton)rg_money.findViewById(R.id.rb_50k);
        rg_money.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_2k:
                        text_hopejob_money.setText(rb_2k.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_2k_5k:
                        text_hopejob_money.setText(rb_2k_5k.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_10k_15k:
                        text_hopejob_money.setText(rb_10k_15k.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_15k_25k:
                        text_hopejob_money.setText(rb_15k_25k.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_25k_50k:
                        text_hopejob_money.setText(rb_25k_50k.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_5k_10k:
                        text_hopejob_money.setText(rb_5k_10k.getText());
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_50k:
                        text_hopejob_money.setText(rb_50k.getText());
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
