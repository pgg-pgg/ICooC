package com.pgg.icookapp.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by PDD on 2017/9/9.
 */
public abstract class ListOfCityAdapter<T,Q> extends BaseAdapter{

    public Context context;
    public List<T> list;
    public Q view;// 这里不一定是ListView,比如GridView,CustomListView


    public ListOfCityAdapter(Context ct, List<T> list, Q view) {
        super();
        this.context = ct;
        this.list = list;
        this.view = view;
    }

    public ListOfCityAdapter(Context ct, List<T> list) {
        super();
        this.context = ct;
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list==null){
            return 0;
        }else{
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
