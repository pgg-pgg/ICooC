package com.pgg.icookapp.holder;

import android.view.View;
import android.widget.TextView;

import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.domain.JobInfo;

/**
 * Created by PDD on 2017/8/5.
 */
public class JDMyInfoHolder extends MyBaseHolder<JobInfo>  {

    private TextView tv_myinfo_name;
    private TextView tv_myinfo_location;
    private TextView tv_myinfo_grade;
    private TextView tv_myinfo_time;

    @Override
    public View initView() {
        View view= UIUtils.getView(R.layout.holder_myinfo);
        tv_myinfo_name = (TextView)view.findViewById(R.id.tv_myinfo_name);
        tv_myinfo_location = (TextView)view.findViewById(R.id.tv_myinfo_location);
        tv_myinfo_grade = (TextView)view.findViewById(R.id.tv_myinfo_grade);
        tv_myinfo_time = (TextView)view.findViewById(R.id.tv_myinfo_time);
        return view;
    }

    @Override
    public void RefreshPage(JobInfo data) {
        tv_myinfo_name.setText(data.jobName+"工程师");
        tv_myinfo_location.setText(data.place);
        tv_myinfo_grade.setText("大四");
        tv_myinfo_time.setText("实习");
    }
}
