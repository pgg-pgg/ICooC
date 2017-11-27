package com.pgg.icookapp.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.BitmapHelper;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.domain.JobInfo;

import java.util.ArrayList;

/**
 * Created by PDD on 2017/8/4.
 */
public class JobListHolder extends MyBaseHolder<JobInfo> {

    private ImageView iv_home_icon;
    private TextView tv_home_name;
    private TextView tv_home_time;
    private TextView tv_home_compName;
    private TextView tv_home_compCity;
    private TextView tv_home_financingType;
    private TextView tv_home_type;
    private TextView tv_home_education;
    private TextView tv_home_salary;
    private TextView tv_home_introduction;
    private BitmapUtils bitmapUtils;

    @Override
    public View initView() {
        View view= UIUtils.getView(R.layout.list_home_item);
        iv_home_icon = (ImageView)view.findViewById(R.id.iv_home_icon);
        tv_home_name = (TextView)view.findViewById(R.id.tv_home_name);
        tv_home_time = (TextView)view.findViewById(R.id.tv_home_time);
        tv_home_compName = (TextView)view.findViewById(R.id.tv_home_compName);
        tv_home_compCity = (TextView)view.findViewById(R.id.tv_home_compCity);
        tv_home_financingType = (TextView)view.findViewById(R.id.tv_home_financingType);
        tv_home_type = (TextView)view.findViewById(R.id.tv_home_type);
        tv_home_education = (TextView)view.findViewById(R.id.tv_home_education);
        tv_home_salary = (TextView)view.findViewById(R.id.tv_home_salary);
        tv_home_introduction = (TextView)view.findViewById(R.id.tv_home_introduction);
        bitmapUtils = BitmapHelper.getmBitmapUtils();
        return view;
    }

    @Override
    public void RefreshPage(JobInfo data) {
        bitmapUtils.display(iv_home_icon,data.compId.compLogoUrl);
        tv_home_name.setText(data.jobTag.name);
        tv_home_time.setText(data.modified);
        tv_home_compName.setText(data.compId.compName);
        tv_home_compCity.setText(data.compId.compCity);
        tv_home_financingType.setText(data.compId.financingType);
        tv_home_type.setText(data.compId.type);
        tv_home_education.setText(data.education);
        tv_home_salary.setText(data.salary);
        tv_home_introduction.setText(data.compId.introduction);
    }
}
