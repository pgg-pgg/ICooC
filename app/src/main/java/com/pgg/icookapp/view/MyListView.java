package com.pgg.icookapp.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ListView;

import com.pgg.icookapp.Utils.UIUtils;

/**
 * Created by PDD on 2017/7/14.
 */
public class MyListView extends ListView {

    private int mMaxOverScrollDistance=50;
    public MyListView(Context context) {
        super(context);
        initView();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView() {
        this.setSelector(new ColorDrawable());
        DisplayMetrics metrics= UIUtils.getContext().getResources().getDisplayMetrics();
        float density=metrics.density;
        mMaxOverScrollDistance=(int)(density*mMaxOverScrollDistance);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxOverScrollDistance, isTouchEvent);
    }
}
