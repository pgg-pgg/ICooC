package com.pgg.icookapp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by PDD on 2017/7/23.
 */
public class NoScrollViewPager extends ViewPager {
    private boolean isIntercept;
    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                if (ev.getY()>HomePage.viewPager.getY()&&ev.getY()<HomePage.viewPager.getY()+180){
//                    isIntercept=false;
//                }else{
//                    isIntercept=false;
//                }
//                break;
//        }
//        return isIntercept;
//    }
}
