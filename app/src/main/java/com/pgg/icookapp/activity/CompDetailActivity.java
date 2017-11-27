package com.pgg.icookapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.BitmapUtils;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.BitmapHelper;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.domain.JobInfo;
import com.pgg.icookapp.view.CircleImageView;
import com.pgg.icookapp.view.LoadingPage;

/**
 * Created by PDD on 2017/8/5.
 */
public class CompDetailActivity extends BaseActivity {

    private LoadingPage mLoadingPage;
    private JobInfo.compId compId;
    private BitmapUtils bitmapUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        mLoadingPage=new LoadingPage(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessPage() {
                return CompDetailActivity.this.onCreateSuccessPage();
            }

            @Override
            public ResultState onLoad() {
                return CompDetailActivity.this.onLoad();
            }
        };

        bitmapUtils = BitmapHelper.getmBitmapUtils();
        setContentView(mLoadingPage);
        mLoadingPage.LoadingData();
    }

    private LoadingPage.ResultState onLoad() {
        compId=(JobInfo.compId)getIntent().getSerializableExtra("compId");
        if (compId==null){
            return LoadingPage.ResultState.STATE_ERROR;
        }else {
            return LoadingPage.ResultState.STATE_SUCCESS;
        }
    }

    private View onCreateSuccessPage() {
        View view=UIUtils.getView(R.layout.activity_comp_detail);
        ImageButton btn_comp_menu=(ImageButton)view.findViewById(R.id.btn_comp_menu);
        btn_comp_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        CircleImageView iv_comp_icon=(CircleImageView)view.findViewById(R.id.iv_info_comp_icon);
        TextView tv_comp_name=(TextView)view.findViewById(R.id.tv_info_comp_name);
        TextView tv_comp_city=(TextView)view.findViewById(R.id.tv_comp_city);
        TextView tv_comp_financingType=(TextView)view.findViewById(R.id.tv_info_comp_financingType);
        TextView tv_comp_scale=(TextView)view.findViewById(R.id.tv_info_comp_scale);
        TextView tv_comp_type=(TextView)view.findViewById(R.id.tv_info_comp_type);
        TextView tv_comp_des=(TextView)view.findViewById(R.id.tv_comp_des);
        TextView tv_comp_location=(TextView)view.findViewById(R.id.tv_comp_location);

        bitmapUtils.display(iv_comp_icon,compId.compLogoUrl);
        tv_comp_name.setText(compId.compName);
        tv_comp_city.setText(compId.compCity);
        tv_comp_financingType.setText(compId.financingType);
        tv_comp_scale.setText(compId.scale);
        tv_comp_type.setText(compId.type);
        tv_comp_des.setText("详细介绍："+"\n"+compId.introduction);
        tv_comp_location.setText(compId.compLocation);
        
        return view;
    }

}
