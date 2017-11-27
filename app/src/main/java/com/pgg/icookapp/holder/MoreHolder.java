package com.pgg.icookapp.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.UIUtils;


/**
 * Created by PDD on 2017/7/12.
 */
public class MoreHolder extends MyBaseHolder<Integer>{
    public static final int STATE_LOAD_MORE=0;
    public static final int STATE_LOAD_ERROR=1;
    public static final int STATE_LOAD_NONE=2;

    private LinearLayout ll_more_page;
    private LinearLayout ll_error_page;

    public MoreHolder(boolean hasmore) {
        setData(hasmore?STATE_LOAD_MORE:STATE_LOAD_NONE);
    }

    @Override
    public View initView() {
        View view= UIUtils.getView(R.layout.list_moretype);
        ll_more_page = (LinearLayout)view.findViewById(R.id.ll_more_page);
        ll_error_page = (LinearLayout)view.findViewById(R.id.ll_error_page);
        return view;
    }

    @Override
    public void RefreshPage(Integer data) {
        switch (data){
            case STATE_LOAD_MORE:
                ll_more_page.setVisibility(View.VISIBLE);
                ll_error_page.setVisibility(View.GONE);
                break;
            case STATE_LOAD_ERROR:
                ll_more_page.setVisibility(View.GONE);
                ll_error_page.setVisibility(View.VISIBLE);
                break;
            case STATE_LOAD_NONE:
                ll_more_page.setVisibility(View.GONE);
                ll_error_page.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}
