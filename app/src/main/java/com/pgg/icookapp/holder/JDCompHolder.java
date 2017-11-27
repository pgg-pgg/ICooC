package com.pgg.icookapp.holder;

import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.BitmapHelper;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.domain.JobInfo;
import com.pgg.icookapp.view.CircleImageView;

/**
 * Created by PDD on 2017/8/5.
 */
public class JDCompHolder extends MyBaseHolder<JobInfo.compId> {

    private CircleImageView iv_comp_icon;
    private TextView tv_comp_name;
    private TextView tv_comp_financingType;
    private TextView tv_comp_scale;
    private TextView tv_comp_type;
    private BitmapUtils bitmapUtils;

    @Override
    public View initView() {
        View view= UIUtils.getView(R.layout.holder_comp);
        iv_comp_icon = (CircleImageView)view.findViewById(R.id.iv_info_comp_icon);
        tv_comp_name = (TextView)view.findViewById(R.id.tv_info_comp_name);
        tv_comp_financingType = (TextView)view.findViewById(R.id.tv_info_comp_financingType);
        tv_comp_scale = (TextView)view.findViewById(R.id.tv_info_comp_scale);
        tv_comp_type = (TextView)view.findViewById(R.id.tv_info_comp_type);
        bitmapUtils = BitmapHelper.getmBitmapUtils();
        return view;
    }

    @Override
    public void RefreshPage(JobInfo.compId data) {
        bitmapUtils.display(iv_comp_icon,data.compLogoUrl);
        tv_comp_name.setText(data.compName);
        tv_comp_financingType.setText(data.financingType);
        tv_comp_scale.setText(data.scale);
        tv_comp_type.setText(data.type);
    }
}
