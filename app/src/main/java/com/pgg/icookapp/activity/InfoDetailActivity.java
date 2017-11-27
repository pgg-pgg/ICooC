package com.pgg.icookapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.BitmapUtils;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.BitmapHelper;
import com.pgg.icookapp.domain.JobInfo;
import com.pgg.icookapp.domain.MyInfo;
import com.pgg.icookapp.view.CircleImageView;

/**
 * Created by PDD on 2017/8/6.
 */
public class InfoDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton btn_info_menu;
    private RelativeLayout rl_head_icon;
    private CircleImageView iv_info_face;
    private ImageView iv_sex_icon;
    private TextView tv_info_name;
    private TextView tv_info_school;
    private TextView tv_info_major;
    private RelativeLayout rl_info_comp;
    private ImageView iv_info_comp_icon;
    private TextView tv_info_comp_name;
    private TextView tv_info_comp_financingType;
    private TextView tv_info_comp_scale;
    private TextView tv_info_comp_type;
    private ListView list_job_send;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        setContentView(R.layout.activity_info_detail);

        btn_info_menu = (ImageButton)findViewById(R.id.btn_info_menu);
        rl_head_icon = (RelativeLayout)findViewById(R.id.rl_head_icon);
        iv_info_face = (CircleImageView)findViewById(R.id.iv_info_face);
        iv_sex_icon = (ImageView)findViewById(R.id.iv_sex_icon);
        tv_info_name = (TextView)findViewById(R.id.tv_info_name);
        tv_info_school = (TextView)findViewById(R.id.tv_info_school);
        tv_info_major = (TextView)findViewById(R.id.tv_info_major);
        rl_info_comp = (RelativeLayout)findViewById(R.id.rl_info_comp);
        iv_info_comp_icon = (ImageView)findViewById(R.id.iv_info_comp_icon);
        tv_info_comp_name = (TextView)findViewById(R.id.tv_info_school);
        tv_info_comp_financingType = (TextView)findViewById(R.id.tv_info_school);
        tv_info_comp_scale = (TextView)findViewById(R.id.tv_info_school);
        tv_info_comp_type = (TextView)findViewById(R.id.tv_info_school);
        list_job_send = (ListView)findViewById(R.id.list_job_send);

        MyInfo myInfo=(MyInfo)getIntent().getSerializableExtra("myInfo");
        JobInfo.compId compId=(JobInfo.compId)getIntent().getSerializableExtra("compId");

        BitmapUtils bitmapUtils= BitmapHelper.getmBitmapUtils();

        bitmapUtils.display(iv_info_face,myInfo.icon);
        bitmapUtils.display(iv_info_comp_icon, compId.compLogoUrl);
        bitmapUtils.display(rl_head_icon,myInfo.backgroudImageUrl);
        if (myInfo.gender==0){
            iv_sex_icon.setImageResource(R.drawable.man_icon);
        }else {
            iv_sex_icon.setImageResource(R.drawable.woman_icon);
        }
        tv_info_name.setText(myInfo.name);
        tv_info_school.setText(myInfo.school);
        tv_info_major.setText(myInfo.major);
        tv_info_comp_name.setText(compId.compName);
        tv_info_comp_financingType.setText(compId.financingType);
        tv_info_comp_scale.setText(compId.scale);
        tv_info_comp_type.setText(compId.type);

        btn_info_menu.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_info_menu:
                InfoDetailActivity.this.finish();
                break;
            case R.id.rl_info_comp:

                break;
        }
    }
}
