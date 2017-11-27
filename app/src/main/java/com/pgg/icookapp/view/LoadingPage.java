package com.pgg.icookapp.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.pgg.icookapp.Manager.ThreadPoolManager;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.UIUtils;

/**
 * Created by PDD on 2017/7/23.
 */
public abstract class LoadingPage extends FrameLayout {
    private static final int STATE_LOAD_UNDO=0;//未加载
    private static final int STATE_LOAD_LOADING=1;//加载中
    private static final int STATE_LOAD_ERROR=2;//加载失败
    private static final int STATE_LOAD_EMPTY=3;//数据为空
    private static final int STATE_LOAD_SUCCESS=4;//加载成功
    private int mCurrentState=STATE_LOAD_UNDO;
    private View mLoadingPage;
    private View mLoadingErrorPage;
    private View mLoadingEmptyPage;
    private View mLoadingSuccessPage;

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    public LoadingPage(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (mLoadingPage==null){
            mLoadingPage = UIUtils.getView(R.layout.fragment_pageloading);
            addView(mLoadingPage);
        }
        if (mLoadingErrorPage==null){
            mLoadingErrorPage = UIUtils.getView(R.layout.fragment_pageloadingerror);
            Button btn_retry=(Button)mLoadingErrorPage.findViewById(R.id.btn_retry);
            btn_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoadingData();
                }
            });
            addView(mLoadingErrorPage);
        }
        if (mLoadingEmptyPage==null){
            mLoadingEmptyPage = UIUtils.getView(R.layout.fragment_pageloadingempty);
            addView(mLoadingEmptyPage);
        }
        showRightPage();
    }

    public void LoadingData(){
        if (mCurrentState == STATE_LOAD_EMPTY
                || mCurrentState == STATE_LOAD_ERROR
                || mCurrentState == STATE_LOAD_SUCCESS) {
            mCurrentState = STATE_LOAD_UNDO;
        }
        if (mCurrentState==STATE_LOAD_UNDO){
            ThreadPoolManager.getmThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    final ResultState resultState=onLoad();
                    UIUtils.RunningMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resultState!=null){
                                mCurrentState=resultState.getState();
                                if (mCurrentState==STATE_LOAD_ERROR){
                                    LoadingData();
                                }
                                showRightPage();
                            }
                        }
                    });
                }
            });
        }
    }

    public enum ResultState{
        STATE_SUCCESS(STATE_LOAD_SUCCESS),STATE_EMPTY(STATE_LOAD_EMPTY),STATE_ERROR(STATE_LOAD_ERROR);
        private int state;
        ResultState(int state){
            this.state=state;
        }
        public int getState(){
            return state;
        }
    }

    private void showRightPage() {
        if (mLoadingEmptyPage!=null){
            mLoadingEmptyPage.setVisibility((mCurrentState==STATE_LOAD_EMPTY)?VISIBLE:GONE);
        }
        if (mLoadingErrorPage!=null){
            mLoadingErrorPage.setVisibility(mCurrentState==STATE_LOAD_ERROR?VISIBLE:GONE);
        }
        if (mLoadingPage!=null){
            mLoadingPage.setVisibility((mCurrentState == STATE_LOAD_LOADING||mCurrentState==STATE_LOAD_UNDO) ? VISIBLE : GONE);
        }
        if (mLoadingSuccessPage==null&&mCurrentState==STATE_LOAD_SUCCESS){
            mLoadingSuccessPage = onCreateSuccessPage();
            if (mLoadingSuccessPage!=null){
                addView(mLoadingSuccessPage);
            }
        }
        if (mLoadingSuccessPage!=null){
            mLoadingSuccessPage.setVisibility(mCurrentState == STATE_LOAD_SUCCESS ? VISIBLE : GONE);
        }
    }

    //加载成功LoadingPage不知如何去实现，只能交给子类去实现
    public abstract View onCreateSuccessPage();

    //怎样加载数据LoadingPage不知道，交给子类
    public abstract ResultState onLoad();
}
