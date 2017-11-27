package com.pgg.icookapp.holder;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lidroid.xutils.BitmapUtils;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.BitmapHelper;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.domain.ImageInfo;

import java.util.ArrayList;

/**
 * Created by PDD on 2017/7/24.
 */
public class HomeHeadHolder extends MyBaseHolder<ArrayList<ImageInfo>> {

    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private ArrayList<ImageInfo> data;
    private int mPrevious;
    @Override
    public View initView() {
        //创建根布局
        RelativeLayout rl_root=new RelativeLayout(UIUtils.getContext());
        AbsListView.LayoutParams abs_params=new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,UIUtils.dp2px(150));
        rl_root.setLayoutParams(abs_params);
        viewPager = new ViewPager(UIUtils.getContext());
        RelativeLayout.LayoutParams vp_params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        rl_root.addView(viewPager,vp_params);

        linearLayout = new LinearLayout(UIUtils.getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams ll_params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        int padding=UIUtils.dp2px(10);
        linearLayout.setPadding(padding, padding, padding, padding);
        ll_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        ll_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rl_root.addView(linearLayout,ll_params);

        return rl_root;
    }
    class MyViewPagerAdapter extends PagerAdapter {
        private final BitmapUtils bitmapUtils;
        public MyViewPagerAdapter() {
            bitmapUtils= BitmapHelper.getmBitmapUtils();
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view==o;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position=position%data.size();
            ImageView imageView=new ImageView(UIUtils.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            final int finalPosition = position;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent();
                    intent.setData(Uri.parse(data.get(finalPosition).targetUrl));//Url 就是你要打开的网址
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    UIUtils.getContext().startActivity(intent);
                }
            });
            bitmapUtils.display(imageView,data.get(position).imgeUrl);
            container.addView(imageView);
            return imageView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void RefreshPage(final ArrayList<ImageInfo> data) {
        this.data=data;
        viewPager.setAdapter(new MyViewPagerAdapter());
        viewPager.setCurrentItem(data.size() * 10000);

        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i=0;i<data.size();i++){
            ImageView point=new ImageView(UIUtils.getContext());
            if (i==0){
                point.setImageResource(R.drawable.indicator_selected);
            }else {
                point.setImageResource(R.drawable.indicator_normal);
                params.leftMargin=UIUtils.dp2px(4);
            }
            point.setLayoutParams(params);
            linearLayout.addView(point);
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int position=i%data.size();
                ImageView point=(ImageView)linearLayout.getChildAt(position);
                point.setImageResource(R.drawable.indicator_selected);

                ImageView mPreviousPoint=(ImageView)linearLayout.getChildAt(mPrevious);
                mPreviousPoint.setImageResource(R.drawable.indicator_normal);
                mPrevious=position;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        HomeHolderTask homeHolderTask=new HomeHolderTask();
        homeHolderTask.start();
    }
    class HomeHolderTask implements Runnable {

        public void start(){
            UIUtils.getHandler().removeCallbacksAndMessages(null);
            UIUtils.getHandler().postDelayed(this,3000);
        }
        @Override
        public void run() {
            int mCurrentItem=viewPager.getCurrentItem();
            mCurrentItem++;
            viewPager.setCurrentItem(mCurrentItem);
            UIUtils.getHandler().postDelayed(this,3000);
        }
    }

    public interface callback{
        void callback(int position);
    }
}
