package com.pgg.icookapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pgg.icookapp.Manager.ThreadPoolManager;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.activity.FeedBackActivity;
import com.pgg.icookapp.activity.LoginActivity;
import com.pgg.icookapp.activity.MyCollectionActivity;
import com.pgg.icookapp.activity.MyInfoItemActivity;
import com.pgg.icookapp.activity.MyResumeActivity;
import com.pgg.icookapp.activity.SettingActivity;
import com.pgg.icookapp.domain.AboutMyInfo;
import com.pgg.icookapp.domain.MyInfo;
import com.pgg.icookapp.domain.MyInfoDomain;
import com.pgg.icookapp.domain.MyLoginInfo;
import com.pgg.icookapp.holder.AboutMeHeadHolder;
import com.pgg.icookapp.holder.AboutMyHeadHolder;
import com.pgg.icookapp.http.protocol.GetInfoDomainProtocol;
import com.pgg.icookapp.http.protocol.GetMyInfoProtocol;
import com.pgg.icookapp.http.protocol.UnCollectProtocol;
import com.pgg.icookapp.view.LoadingPage;

import java.util.ArrayList;

/**
 * Created by PDD on 2017/7/23.
 */
public class MeFragment extends BaseFragment {

    private ListView list_about_me;
    private int TYPE_NORMAL=0;
    private int TYPE_NULL=1;

    private static MyInfoDomain myInfo;
    private int [] image_list=null;
    private String[] title_list=null;
    private ArrayList<AboutMyInfo> data;
    private AboutMeAdapter aboutMeAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AboutMyHeadHolder myHeadHolder;
    private Activity activity;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                myHeadHolder.setData((MyInfoDomain)msg.obj);
            }
        }
    };
    @Override
    public View onCreateSuccessPage() {
        swipeRefreshLayout = new SwipeRefreshLayout(UIUtils.getContext());
        list_about_me = new ListView(UIUtils.getContext());
        activity=getActivity();
        myHeadHolder = new AboutMyHeadHolder(activity);
        myHeadHolder.setData(myInfo);
        list_about_me.addHeaderView(myHeadHolder.getmRootView());
        aboutMeAdapter = new AboutMeAdapter();
        list_about_me.setAdapter(aboutMeAdapter);
        list_about_me.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private Intent intent;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 2:
                        //我的资料被点击
                        intent = new Intent(UIUtils.getContext(), MyInfoItemActivity.class);
                        intent.putExtra("hopejobname", LoginActivity.myLoginInfo.tPosition);
                        startActivity(intent);
                        break;
                    case 3:
                        //我的收藏被点击
                        intent = new Intent(UIUtils.getContext(), MyCollectionActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        //我的简历被点击
                        intent = new Intent(UIUtils.getContext(), MyResumeActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        //消息通知被点击

                        break;
                    case 7:
                        //意见反馈被点击
                        intent = new Intent(UIUtils.getContext(), FeedBackActivity.class);
                        startActivity(intent);
                        break;
                    case 9:
                        //设置被点击
                        intent = new Intent(UIUtils.getContext(), SettingActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (MeFragment.this.onLoad() == LoadingPage.ResultState.STATE_SUCCESS) {
                    if (aboutMeAdapter != null) {
                        GetMyInfoConn getMyInfoConn=new GetMyInfoConn(LoginActivity.myLoginInfo.id);
                        getMyInfoConn.start();
                        try {
                            getMyInfoConn.join();
                            Message message=new Message();
                            message.what=1;
                            message.obj=myInfo;
                            handler.sendMessage(message);
                            swipeRefreshLayout.setRefreshing(false);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });
        swipeRefreshLayout.addView(list_about_me);
        return swipeRefreshLayout;
    }

    class AboutMeAdapter extends BaseAdapter{

        public AboutMeAdapter() {
            super();
            image_list=new int[]{R.drawable.item_info_icon,R.drawable.item_collect_icon,
                    R.drawable.item_text_icon,R.drawable.item_message_icon,
                    R.drawable.item_return_icon,R.drawable.item_setting_icon};
            title_list=new String[]{"我的资料","我的收藏","我的简历","消息通知","意见反馈","设置"};
            data=new ArrayList<>();
            for (int i=0;i<image_list.length;i++){
                AboutMyInfo aboutMyInfo=new AboutMyInfo();
                aboutMyInfo.my_image=image_list[i];
                aboutMyInfo.my_text=title_list[i];
                data.add(aboutMyInfo);
            }
        }


        @Override
        public int getItemViewType(int position) {
            if (position==0||position==4||position==7){
                return TYPE_NULL;
            }else {
                return TYPE_NORMAL;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getCount() {
            return data.size()+3;
        }

        @Override
        public AboutMyInfo getItem(int position) {
            if (position==0||position==4||position==7){
                return null;
            }else if (position<=3){
                return data.get(position-1);
            }else if (position<=6){
                return data.get(position-2);
            }else {
                return data.get(position-3);
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            int type=getItemViewType(position);
            if(type==TYPE_NORMAL){
                if (convertView==null){
                    convertView=UIUtils.getView(R.layout.list_item_me);
                    viewHolder = new ViewHolder();
                    viewHolder.iv_title=(ImageView)convertView.findViewById(R.id.iv_mark);
                    viewHolder.tv_mark=(TextView)convertView.findViewById(R.id.tv_item_mark);
                    convertView.setTag(viewHolder);
                }else {
                    viewHolder=(ViewHolder)convertView.getTag();
                }
                viewHolder.iv_title.setImageResource(getItem(position).my_image);
                viewHolder.tv_mark.setText(getItem(position).my_text);
            }else {
                convertView=UIUtils.getView(R.layout.list_item_me1);
            }

            return convertView;
        }
    }

    static class ViewHolder {
        ImageView iv_title;
        TextView tv_mark;
    }
    @Override
    public LoadingPage.ResultState onLoad() {
        GetMyInfoConn getMyInfoConn=new GetMyInfoConn(LoginActivity.myLoginInfo.id);
        getMyInfoConn.start();
        try {
            getMyInfoConn.join();
//            Message message=new Message();
//            message.what=1;
//            message.obj=myInfo;
//            handler.sendMessage(message);
            if (myInfo!=null){
                return LoadingPage.ResultState.STATE_SUCCESS;
            }else {
                return LoadingPage.ResultState.STATE_ERROR;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return LoadingPage.ResultState.STATE_ERROR;
    }

    private class GetMyInfoConn extends Thread{
        private String userId;
        public GetMyInfoConn(final String userId) {
            this.userId=userId;
        }
        @Override
        public void run() {
            myInfo=new GetInfoDomainProtocol(userId).getData();
        }
    }
}
