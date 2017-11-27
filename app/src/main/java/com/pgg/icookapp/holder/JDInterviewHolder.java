package com.pgg.icookapp.holder;

import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.BitmapHelper;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.domain.MyInfo;
import com.pgg.icookapp.view.CircleImageView;

/**
 * Created by PDD on 2017/8/5.
 */
public class JDInterviewHolder extends MyBaseHolder<MyInfo> {

    private CircleImageView iv_inter_icon;
    private TextView tv_inter_name;
    private TextView tv_inter_job;
    private TextView tv_inter_school;
    private TextView tv_inter_major;
    private BitmapUtils bitmapUtils;

    @Override
    public View initView() {
        View view= UIUtils.getView(R.layout.holder_interview);
        iv_inter_icon = (CircleImageView)view.findViewById(R.id.iv_inter_icon);
        tv_inter_name = (TextView)view.findViewById(R.id.tv_inter_name);
        tv_inter_job = (TextView)view.findViewById(R.id.tv_inter_job);
        tv_inter_school = (TextView)view.findViewById(R.id.tv_inter_school);
        tv_inter_major = (TextView)view.findViewById(R.id.tv_inter_major);
        bitmapUtils = BitmapHelper.getmBitmapUtils();
        return view;
    }

    @Override
    public void RefreshPage(MyInfo data) {
        if (data!=null){
            bitmapUtils.display(iv_inter_icon,data.icon);
            tv_inter_name.setText(data.name);
            tv_inter_job.setText(data.company);
            tv_inter_school.setText(data.school);
            tv_inter_major.setText(data.major);
        }
    }
}
