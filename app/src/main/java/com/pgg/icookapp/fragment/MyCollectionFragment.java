package com.pgg.icookapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.activity.JobDetailActivity;
import com.pgg.icookapp.activity.LoginActivity;
import com.pgg.icookapp.adapter.MyBaseAdapter;
import com.pgg.icookapp.domain.JobInfo;
import com.pgg.icookapp.holder.HomeHeadHolder;
import com.pgg.icookapp.holder.JobListHolder;
import com.pgg.icookapp.holder.MyBaseHolder;
import com.pgg.icookapp.http.protocol.GetCollectonListProtocol;
import com.pgg.icookapp.view.LoadingPage;

import java.util.ArrayList;

/**
 * Created by PDD on 2017/9/6.
 */
public class MyCollectionFragment extends BaseFragment {

    private ListView lv_collection;
    private ArrayList<JobInfo> list;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.loadData();
    }

    @Override
    public View onCreateSuccessPage() {
        lv_collection = new ListView(UIUtils.getContext());
        lv_collection.setAdapter(new CollectionAdapter(list));
        lv_collection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobInfo jobInfo=list.get(position);
                JobInfo.compId compId=jobInfo.compId;
                JobInfo.jobTag jobTag=jobInfo.jobTag;
                JobInfo.jobType jobType=jobInfo.jobType;
                Intent intent=new Intent(UIUtils.getContext(), JobDetailActivity.class);
                intent.putExtra("isCollection",true);
                intent.putExtra("JobInfo",jobInfo);
                intent.putExtra("compId",compId);
                intent.putExtra("jobTag",jobTag);
                intent.putExtra("jobType",jobType);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return lv_collection;
    }

    class CollectionAdapter extends MyBaseAdapter<JobInfo> {

        public CollectionAdapter(ArrayList<JobInfo> data) {
            super(data);
        }

        @Override
        public boolean hasmore() {
            return false;
        }

        @Override
        public MyBaseHolder<JobInfo> getHolder(int pos) {
            return new JobListHolder();
        }

        @Override
        public ArrayList<JobInfo> onLoadMore() {
            return null;
        }
    }
    @Override
    public LoadingPage.ResultState onLoad() {
        GetCollectonListProtocol getCollectonListProtocol=new GetCollectonListProtocol(1, LoginActivity.myLoginInfo.id);
        list=getCollectonListProtocol.getData();
        return checkData(list);
    }
}
