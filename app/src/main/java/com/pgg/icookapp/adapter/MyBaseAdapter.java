package com.pgg.icookapp.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;


import com.pgg.icookapp.Manager.ThreadPoolManager;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.holder.MoreHolder;
import com.pgg.icookapp.holder.MyBaseHolder;

import java.util.ArrayList;

/**
 * Created by PDD on 2017/7/12.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    private static final int ITEM_NORMAL=1;
    private static final int ITEM_MORE=0;
    private static boolean isLoadMore=false;
    public boolean hasmore(){
        return true;
    }
    public ArrayList<T> data;
    private MoreHolder mMoreHoldler;

    public MyBaseAdapter(ArrayList<T> data) {
        super();
        this.data=data;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==getCount()-1){
            return ITEM_MORE;
        }else {
            return getType(position);
        }
    }
    public int getType(int pos){
        return ITEM_NORMAL;
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    @Override
    public int getCount() {
        return data.size()+1;
    }
    @Override
    public T getItem(int position) {
        return data.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyBaseHolder myBaseHolder=null;
        if (convertView==null){
            if (getItemViewType(position)==ITEM_MORE){
                myBaseHolder = new MoreHolder(hasmore());
            }else {
                myBaseHolder=getHolder(position);
            }
        }else {
            myBaseHolder=(MyBaseHolder)convertView.getTag();
        }
        if (getItemViewType(position)!=ITEM_MORE){
            myBaseHolder.setData(getItem(position));
        }else {
             mMoreHoldler=(MoreHolder)myBaseHolder;
            if (mMoreHoldler.getData()==MoreHolder.STATE_LOAD_MORE){
                loadMore(mMoreHoldler);
            }
        }
        return myBaseHolder.getmRootView();
    }

    public void loadMore(final MoreHolder moreHolder){
        if (!isLoadMore){
            isLoadMore=true;
            ThreadPoolManager.getmThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    final ArrayList<T> moreData= onLoadMore();
                    UIUtils.RunningMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (moreData != null) {
                                //每一页有20 条数据，如果返回数据小于20条，就认为到了最后一页了，那么就没有更多数据了
                                if (moreData.size() < 6) {
                                    moreHolder.setData(MoreHolder.STATE_LOAD_NONE);
                                    Toast.makeText(UIUtils.getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                                } else {
                                    moreHolder.setData(MoreHolder.STATE_LOAD_MORE);
                                }
                                data.addAll(moreData);
                                MyBaseAdapter.this.notifyDataSetChanged();
                            } else {
                                //加载更多失败
                                moreHolder.setData(MoreHolder.STATE_LOAD_ERROR);
                            }
                            isLoadMore = false;
                        }
                    });
                }
            });
        }
    }
    public abstract MyBaseHolder<T> getHolder(int pos);
    public abstract ArrayList<T> onLoadMore();
    public int getDataSize(){
        return data.size();
    }
}
