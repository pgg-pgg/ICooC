package com.pgg.icookapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.PrefUtils;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.adapter.MyBaseAdapter;
import com.pgg.icookapp.domain.JobInfo;
import com.pgg.icookapp.holder.JobListHolder;
import com.pgg.icookapp.holder.MyBaseHolder;
import com.pgg.icookapp.http.protocol.SearchJobProtocol;
import com.pgg.icookapp.view.LoadingPage;

import java.util.ArrayList;

/**
 * Created by PDD on 2017/9/11.
 */
public class ShowSearchJobActivity extends BaseActivity {

    private LoadingPage mLoadingPage;
    private ListView lv_search;
    private ArrayList<JobInfo> list;
    private SwipeRefreshLayout sr_search_result_list;
    MySearchAdapter adapter=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_search);
//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        EditText edit_search=(EditText)findViewById(R.id.edit_search);
        sr_search_result_list = (SwipeRefreshLayout)findViewById(R.id.sr_search_result_list);
        sr_search_result_list.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (ShowSearchJobActivity.this.onLoad() == LoadingPage.ResultState.STATE_SUCCESS) {
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
                sr_search_result_list.setRefreshing(false);
            }
        });
        edit_search.setText(getIntent().getStringExtra("key"));
        edit_search.clearFocus();
        TextView btn_cancel=(TextView)findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowSearchJobActivity.this,SearchJobActivity.class);
                startActivity(intent);
                finish();
            }
        });
        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                }
                return false;
            }
        });

        FrameLayout container_search_result=(FrameLayout)findViewById(R.id.container_search_result);

        mLoadingPage=new LoadingPage(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessPage() {
                return ShowSearchJobActivity.this.onCreateSuccessPage();
            }

            @Override
            public ResultState onLoad() {
                return ShowSearchJobActivity.this.onLoad();
            }
        };
       container_search_result.addView(mLoadingPage);
        mLoadingPage.LoadingData();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent=new Intent(ShowSearchJobActivity.this,SearchJobActivity.class);
            startActivity(intent);
            finish();// 在这里进行点击判断
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private LoadingPage.ResultState onLoad() {
        SearchJobProtocol searchJobProtocol=new SearchJobProtocol(1,getIntent().getStringExtra("key"),LoginActivity.myLoginInfo.id);
        list = searchJobProtocol.getData();
        if (list==null||list.isEmpty()){
            return LoadingPage.ResultState.STATE_EMPTY;
        }else {
            return LoadingPage.ResultState.STATE_SUCCESS;
        }
    }

    private View onCreateSuccessPage() {
        lv_search = new ListView(UIUtils.getContext());
        lv_search.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if(lv_search != null && lv_search.getChildCount() > 0){
                    // check if the first item of the list is visible
                    boolean firstItemVisible = lv_search.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = lv_search.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                sr_search_result_list.setEnabled(enable);
            }
        });
        adapter=new MySearchAdapter(list);
        lv_search.setAdapter(adapter);
        lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobInfo jobInfo = list.get(position);
                JobInfo.compId compId = jobInfo.compId;
                JobInfo.jobTag jobTag = jobInfo.jobTag;
                JobInfo.jobType jobType = jobInfo.jobType;
                Intent intent = new Intent(UIUtils.getContext(), JobDetailActivity.class);
                intent.putExtra("JobInfo", jobInfo);
                intent.putExtra("compId", compId);
                intent.putExtra("jobTag", jobTag);
                intent.putExtra("jobType", jobType);
                startActivity(intent);
            }
        });
        return lv_search;
    }

    public interface OnSwipeIsValid{
        public void setSwipeEnabledTrue();
        public void setSwipeEnabledFalse();
    }

    class MySearchAdapter extends MyBaseAdapter<JobInfo> {

        public MySearchAdapter(ArrayList<JobInfo> data) {
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
}
