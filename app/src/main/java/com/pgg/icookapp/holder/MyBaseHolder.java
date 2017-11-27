package com.pgg.icookapp.holder;

import android.view.View;

/**
 * Created by PDD on 2017/7/12.
 */
public abstract class MyBaseHolder<T>  {
    private final View mRootView;
    private T data;
    public MyBaseHolder(){
        mRootView = initView();
        mRootView.setTag(this);
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
        RefreshPage(data);
    }
    public View getmRootView() {
        return mRootView;
    }
    //1.加载布局页面    2.格局id找到控件
    public abstract View initView();
    //刷新页面
    public abstract void RefreshPage(T data);
}
