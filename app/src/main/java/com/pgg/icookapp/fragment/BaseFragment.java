package com.pgg.icookapp.fragment;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.view.LoadingPage;

import java.util.ArrayList;


/**
 * Created by PDD on 2017/7/23.
 */
public abstract class BaseFragment extends Fragment {

    private LoadingPage mLoadingPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("dasfasdfasd", "BaseFragment中的onCreateView方法被执行");
        mLoadingPage = new LoadingPage(UIUtils.getContext()){
            @Override
            public View onCreateSuccessPage() {
               // Log.e("dasfasdfasd", "LoadingPage页面onCreateSuccessPage()方法被执行");
                return BaseFragment.this.onCreateSuccessPage();
            }
            @Override
            public LoadingPage.ResultState onLoad() {
                //Log.e("dasfasdfasd", "LoadingPage页面onLoad()方法被执行");
                return BaseFragment.this.onLoad();
            }
        };
        return mLoadingPage;
    }
    public void loadData(){
        if (mLoadingPage!=null){
            mLoadingPage.LoadingData();
        }
    }
    //加载成功界面的实现由上层的LoadingPage交给BaseFragment还是不知道该如何实现，还是交给下一层实现
    public abstract View onCreateSuccessPage();
    public abstract LoadingPage.ResultState onLoad();
    public LoadingPage.ResultState checkData(Object obj){
        if (obj!=null){
            if (obj instanceof ArrayList){
                ArrayList list=(ArrayList)obj;
                if (list.isEmpty()){
                    return LoadingPage.ResultState.STATE_EMPTY;
                }else {
                    return LoadingPage.ResultState.STATE_SUCCESS;
                }
            }
        }
        return LoadingPage.ResultState.STATE_ERROR;
    }
}
