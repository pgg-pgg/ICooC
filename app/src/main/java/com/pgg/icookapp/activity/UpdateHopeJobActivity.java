package com.pgg.icookapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pgg.icookapp.Manager.ActivityManager;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.DrawableUtils;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.http.protocol.UpdateJobPosProtocol;
import com.pgg.icookapp.view.FlowLayout;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by PDD on 2017/9/6.
 */


public class UpdateHopeJobActivity extends BaseActivity {

    private static String[] data=new String[]{"IT","电子商务"
            ,"互联网金融","HR","移动互联网","职业发展","团队建设","职业选择","iOS开发","Android开发"
            ,"前端","Java","产品经理","运营","销售","数据分析","后端","智能家居","其他"};

    private ConcurrentHashMap<Integer,View> map_ViewId=new ConcurrentHashMap<>();
    private FlowLayout flowLayout;

    private ImageView btn_sure;
    private static int code;

    private static String userId=LoginActivity.myLoginInfo.id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hope);
//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);


        ImageView iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UIUtils.getContext(),MyInfoItemActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_sure=(ImageView)findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String s=URLEncoder.encode(getHopeJob(), "utf-8");
                    UpdateJobPosConnection updateJobPosConnection=new UpdateJobPosConnection(s);
                    updateJobPosConnection.start();
                    updateJobPosConnection.join();
                    if (code==0){
                        Toast.makeText(UIUtils.getContext(), "意向职位修改成功", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(UIUtils.getContext(),MyInfoItemActivity.class);
                        intent.putExtra("hopejobname",getHopeJob());
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(UIUtils.getContext(),"意向职位修改失败，请重试",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ScrollView scroll_hope_job=(ScrollView)findViewById(R.id.scroll_hope_job);
        int padding= UIUtils.dp2px(10);
        scroll_hope_job.setPadding(padding,padding,padding,padding);
        flowLayout = new FlowLayout(UIUtils.getContext());
        flowLayout.setVerticalSpacing(10);
        for (String value:data){
            final TextView textView=new TextView(UIUtils.getContext());
            textView.setId(View.generateViewId());
            textView.setText(value);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(padding, padding, padding, padding);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textView.setTextColor(Color.WHITE);

            Random random=new Random();
            int r=30+random.nextInt(50);
            int g=30+random.nextInt(100);
            int b=30+random.nextInt(150);

            int color=0xffcecece;
            Drawable selector = DrawableUtils.getStateListDrawable(Color.rgb(r, g, b), color, UIUtils.dp2px(6));
            textView.setBackground(selector);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (map_ViewId.keySet().contains(v.getId())) {
                        textView.getBackground().setAlpha(255);
                        textView.setAlpha(1);
                        for (Integer i : map_ViewId.keySet()) {
                            if (i == v.getId()) {
                                map_ViewId.remove(i);
                            }
                        }
                    } else {
                        textView.getBackground().setAlpha(100);
                        textView.setAlpha(0.3f);
                        map_ViewId.put(v.getId(), v);
                    }
                }
            });
            flowLayout.addView(textView);
        }
        scroll_hope_job.addView(flowLayout);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent=new Intent(UIUtils.getContext(),MyInfoItemActivity.class);
            startActivity(intent);
            finish(); // 在这里进行点击判断
            return false;
            }
        return super.onKeyDown(keyCode, event);
    }

    private String getHopeJob(){
        StringBuffer stringBuffer=new StringBuffer();
        for (Integer i:map_ViewId.keySet()){
            TextView v=(TextView)map_ViewId.get(i);
            String s=v.getText()+" ";
            stringBuffer.append(s);
        }
        return stringBuffer.toString();
    }

    private class UpdateJobPosConnection extends Thread{
        private String tPosition;
        public UpdateJobPosConnection( final String tPosition) {
            super();
            this.tPosition=tPosition;
        }
        @Override
        public void run() {
            code=new UpdateJobPosProtocol(userId,tPosition).getData();
        }
    }
}
