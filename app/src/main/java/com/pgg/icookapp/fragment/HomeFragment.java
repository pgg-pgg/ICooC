package com.pgg.icookapp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pgg.icookapp.Utils.PrefUtils;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.activity.JobDetailActivity;
import com.pgg.icookapp.activity.LoginActivity;
import com.pgg.icookapp.adapter.MyBaseAdapter;
import com.pgg.icookapp.domain.ImageInfo;
import com.pgg.icookapp.domain.JobInfo;
import com.pgg.icookapp.holder.HomeHeadHolder;
import com.pgg.icookapp.holder.JobListHolder;
import com.pgg.icookapp.holder.MyBaseHolder;
import com.pgg.icookapp.http.protocol.GetJobListProtocol;
import com.pgg.icookapp.http.protocol.ImageResProtocol;
import com.pgg.icookapp.view.LoadingPage;
import com.pgg.icookapp.view.MyListView;

import java.util.ArrayList;

/**
 * Created by PDD on 2017/7/23.
 */
public class HomeFragment extends BaseFragment {

    private MyListView list_about_me;
    private ArrayList<ImageInfo> data;
    private ArrayList<JobInfo> list;

    @Override
    public View onCreateSuccessPage() {
        list_about_me = new MyListView(UIUtils.getContext());
        list_about_me.setOverScrollMode(View.OVER_SCROLL_NEVER);
        HomeHeadHolder meHeadHolder=new HomeHeadHolder();
        meHeadHolder.setData(data);
        list_about_me.addHeaderView(meHeadHolder.getmRootView());
        list_about_me.setAdapter(new MyJobInfoAdapter(list));
        list_about_me.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobInfo jobInfo=list.get(position - 1);
                JobInfo.compId compId=jobInfo.compId;
                JobInfo.jobTag jobTag=jobInfo.jobTag;
                JobInfo.jobType jobType=jobInfo.jobType;
                Intent intent=new Intent(UIUtils.getContext(), JobDetailActivity.class);
                intent.putExtra("isCollection",false);
                intent.putExtra("JobInfo",jobInfo);
                intent.putExtra("compId",compId);
                intent.putExtra("jobTag",jobTag);
                intent.putExtra("jobType",jobType);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return list_about_me;
    }

    class MyJobInfoAdapter extends MyBaseAdapter<JobInfo>{

        public MyJobInfoAdapter(ArrayList<JobInfo> data) {
            super(data);
        }

        @Override
        public MyBaseHolder<JobInfo> getHolder(int pos) {
            return new JobListHolder();
        }

        @Override
        public ArrayList<JobInfo> onLoadMore() {
            GetJobListProtocol getJobListProtocol=new GetJobListProtocol(PrefUtils.getInt("count",6)/3, LoginActivity.myLoginInfo.id);
            ArrayList<JobInfo> list1=getJobListProtocol.getData();
            return list1;
        }
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        ImageResProtocol imageResProtocol=new ImageResProtocol();
        data=imageResProtocol.getData();
        GetJobListProtocol getJobListProtocol=new GetJobListProtocol(1, LoginActivity.myLoginInfo.id);
        list=getJobListProtocol.getData();
        return checkData(list);
    }


}
