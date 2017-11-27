package com.pgg.icookapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.PrefUtils;

import java.util.ArrayList;

/**
 * Created by PDD on 2017/7/22.
 */
public class GuideActivity extends BaseActivity {
    private ViewPager mViewPager;
    private int[] mImageIds=new int[]{ R.drawable.guide1,
            R.drawable.guide2,R.drawable.guide3};
    private ImageView red_point;
    private ArrayList<ImageView> mImageViewArrayList;
    private LinearLayout llcontainer;
    private int pointSpace;//两点之间的间距
    private Button btnStatr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        red_point=(ImageView)findViewById(R.id.iv_red_point);
        llcontainer=(LinearLayout)findViewById(R.id.ll_container);
        mViewPager=(ViewPager)findViewById(R.id.vp_guide);
        btnStatr=(Button)findViewById(R.id.btn_start);
        btnStatr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.putBoolean("is_first_enter", false);
                Intent intent=new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        initData();
        mViewPager.setAdapter(new GuideAdapter());

        //measure -> layout -> draw
        //获得视图树观察者，观察当整个布局的layout的事件
        red_point.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //此方法只需要执行一次就可以，把当前的监听事件从视图树中移除掉，以后就不会在回调此事件了
                red_point.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //点的距离 = 第1个点的左边 - 第0个点的左边
                pointSpace = llcontainer.getChildAt(1).getLeft() - llcontainer.getChildAt(0).getLeft();
                System.out.println("两点之间的距离:" + pointSpace);

//                System.out.println("onGlobalLayout");

            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //System.out.println("位置:" + position + ",位置移动的百分比:" + positionOffset + ",位置移动的像素:" + positionOffsetPixels);
                int leftMargin = (int) (pointSpace * positionOffset + pointSpace * position);
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) red_point.getLayoutParams();
                lp.leftMargin = leftMargin;
                red_point.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {

                if (position==mImageIds.length-1){
                    btnStatr.setVisibility(View.VISIBLE);
                }else{
                    btnStatr.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initData(){
        mImageViewArrayList=new ArrayList<ImageView>();
        for (int i=0;i<mImageIds.length;i++){
            ImageView view=new ImageView(this);
            view.setBackgroundResource(mImageIds[i]);
            mImageViewArrayList.add(view);

            //设置小圆点
            ImageView point=new ImageView(this);
            point.setImageResource(R.drawable.guide_point_shape);//设置灰点
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i>0){
                params.leftMargin=10;
            }
            point.setLayoutParams(params);
            llcontainer.addView(point);



        }
    }
    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view=mImageViewArrayList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
