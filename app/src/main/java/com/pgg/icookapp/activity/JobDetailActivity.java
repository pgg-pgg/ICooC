package com.pgg.icookapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.domain.JobInfo;
import com.pgg.icookapp.domain.MyInfo;
import com.pgg.icookapp.fragment.HomeFragment;
import com.pgg.icookapp.holder.JDCompHolder;
import com.pgg.icookapp.holder.JDInterviewHolder;
import com.pgg.icookapp.holder.JDMyInfoHolder;
import com.pgg.icookapp.http.protocol.CollectProtocol;
import com.pgg.icookapp.http.protocol.GetMyInfoProtocol;
import com.pgg.icookapp.http.protocol.UnCollectProtocol;
import com.pgg.icookapp.view.LoadingPage;

import java.util.ArrayList;

/**
 * Created by PDD on 2017/8/5.
 */
public class JobDetailActivity extends BaseActivity implements View.OnClickListener{

    private LoadingPage mLoadingPage;
    private JobInfo jobInfo;
    private JobInfo.compId compId;
    private MyInfo myInfo;
    private ImageButton ib_add_collect;
    private int code;
    private boolean isCollect=false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        mLoadingPage=new LoadingPage(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessPage() {
                return JobDetailActivity.this.onCreateSuccessPage();
            }

            @Override
            public ResultState onLoad() {
                return JobDetailActivity.this.onLoad();
            }
        };
        jobInfo = (JobInfo)getIntent().getSerializableExtra("JobInfo");
        compId = (JobInfo.compId)getIntent().getSerializableExtra("compId");
        setContentView(mLoadingPage);
        mLoadingPage.LoadingData();
    }

    private LoadingPage.ResultState onLoad() {
        GetMyInfoProtocol getMyInfoProtocol=new GetMyInfoProtocol(jobInfo.interviewerid);
        myInfo = getMyInfoProtocol.getData();
        if (myInfo==null){
            return LoadingPage.ResultState.STATE_ERROR;
        }else {
            return LoadingPage.ResultState.STATE_SUCCESS;
        }
    }

    private View onCreateSuccessPage() {
        View view=UIUtils.getView(R.layout.activity_jobdetail);
        ImageButton btn_job_menu=(ImageButton)view.findViewById(R.id.btn_job_menu);
        btn_job_menu.setOnClickListener(this);
        Button btn_send_info=(Button)view.findViewById(R.id.btn_send_info);
        btn_send_info.setOnClickListener(this);
        Button btn_communicating=(Button)view.findViewById(R.id.btn_communicating);
        btn_communicating.setOnClickListener(this);

        ib_add_collect = (ImageButton)view.findViewById(R.id.ib_add_collect);
        if (jobInfo.isFavorite==0){
            isCollect=false;
            ib_add_collect.setBackgroundResource(R.drawable.addcollect);
        }else if (jobInfo.isFavorite==1){
            isCollect=true;
            ib_add_collect.setBackgroundResource(R.drawable.addcollect_press);
        }
        ib_add_collect.setOnClickListener(this);

        FrameLayout fl_container1=(FrameLayout)view.findViewById(R.id.fl_container1);
        JDMyInfoHolder jdMyInfoHolder=new JDMyInfoHolder();
        fl_container1.addView(jdMyInfoHolder.getmRootView());
        jdMyInfoHolder.setData(jobInfo);

        FrameLayout fl_container2=(FrameLayout)view.findViewById(R.id.fl_container2);
        JDCompHolder jdCompHolder=new JDCompHolder();
        fl_container2.addView(jdCompHolder.getmRootView());
        jdCompHolder.setData(compId);
        fl_container2.setOnClickListener(this);

        FrameLayout fl_container4=(FrameLayout)view.findViewById(R.id.fl_container4);
        JDInterviewHolder jdInterviewHolder=new JDInterviewHolder();
        fl_container4.addView(jdInterviewHolder.getmRootView());
        jdInterviewHolder.setData(myInfo);
        fl_container4.setOnClickListener(this);

        TextView tv_job_des=(TextView)view.findViewById(R.id.tv_job_des);
        tv_job_des.setText("岗位职责："+"\n"+jobInfo.shortDesc+"\n"+"任职要求："+"\n"+jobInfo.req);

        return view;
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_job_menu:
                if (getIntent().getBooleanExtra("isCollection",false)){
                    intent=new Intent(UIUtils.getContext(),MyCollectionActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    intent=new Intent(UIUtils.getContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                break;
            case R.id.ib_add_collect:
                if (!isCollect){
                    ib_add_collect.setBackgroundResource(R.drawable.addcollect_press);
                    isCollect=true;
                    try {
                        CollectConnection collectConnection=new CollectConnection(LoginActivity.myLoginInfo.id,jobInfo.id);
                        collectConnection.start();
                        collectConnection.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (code ==0){
                        Toast.makeText(UIUtils.getContext(),"收藏成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(UIUtils.getContext(),"收藏失败",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    ib_add_collect.setBackgroundResource(R.drawable.addcollect);
                    isCollect=false;
                    try {
                        UnCollectConnection collectConnection=new UnCollectConnection(LoginActivity.myLoginInfo.id,jobInfo.id);
                        collectConnection.start();
                        collectConnection.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (code==0){
                        Toast.makeText(UIUtils.getContext(),"取消收藏",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(UIUtils.getContext(),"取消收藏失败",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_send_info:

                break;
            case R.id.btn_communicating:

                break;
            case R.id.fl_container2:
                intent = new Intent(JobDetailActivity.this,CompDetailActivity.class);
                intent.putExtra("compId", compId);
                startActivity(intent);
                break;
            case R.id.fl_container4:
                intent=new Intent(JobDetailActivity.this,InfoDetailActivity.class);
                intent.putExtra("myInfo",myInfo);
                intent.putExtra("compId",compId);
                startActivity(intent);
                break;
        }
    }


    private class CollectConnection extends Thread{
        private String userId,jobId;
        public CollectConnection(final String userId, final String jobId) {
            super();
            this.userId=userId;
            this.jobId=jobId;
        }

        @Override
        public void run() {
            code=new CollectProtocol(userId,jobId).getData();
        }
    }

    private class UnCollectConnection extends Thread{
        private String userId,jobId;
        public UnCollectConnection(final String userId, final String jobId) {
            super();
            this.userId=userId;
            this.jobId=jobId;
        }

        @Override
        public void run() {
            code=new UnCollectProtocol(userId,jobId).getData();
        }
    }
}
