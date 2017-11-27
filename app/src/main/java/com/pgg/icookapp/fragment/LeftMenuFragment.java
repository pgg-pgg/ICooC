package com.pgg.icookapp.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.BitmapHelper;
import com.pgg.icookapp.Utils.StringUtils;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.activity.LoginActivity;
import com.pgg.icookapp.domain.MyLoginInfo;
import com.pgg.icookapp.view.CircleImageView;

/**
 * Created by PDD on 2017/7/26.
 */
public class LeftMenuFragment extends Fragment {

    private CircleImageView circleImageView;
    private TextView tv_name_content,tv_signature_content;
    private ListView list_left_menu;
    private int[] icons;
    private String[] items;
    private BitmapUtils bitmapUtils;

    private static MyLoginInfo myLoginInfo=LoginActivity.myLoginInfo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=UIUtils.getView(R.layout.fragment_left_menu);
        circleImageView = (CircleImageView)view.findViewById(R.id.iv_face_content);
        bitmapUtils = BitmapHelper.getmBitmapUtils();
//        if (StringUtils.isEmpty(myLoginInfo.icon)){
//            circleImageView.setBackgroundResource(R.drawable.face_normal);
//        }else {
//            bitmapUtils.display(circleImageView,myLoginInfo.icon);
//        }
        tv_name_content = (TextView)view.findViewById(R.id.tv_name_content);
        tv_name_content.setText(myLoginInfo.name);
        tv_signature_content=(TextView)view.findViewById(R.id.tv_signature_content);
        tv_signature_content.setText(myLoginInfo.introduction);
        list_left_menu = (ListView)view.findViewById(R.id.list_left_menu);
        list_left_menu.setAdapter(new MyListViewAdapter());
        return view;
    }

    class MyListViewAdapter extends BaseAdapter{
        public MyListViewAdapter() {
            super();
            icons=new int[]{R.drawable.icon_vip,R.drawable.item_collect,R.drawable.item_face_box,R.drawable.item_msg_info};
            items=new String[]{"激活会员","我的收藏","表情包","消息通知"};
        }

        @Override
        public int getCount() {
            return icons.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=UIUtils.getView(R.layout.list_left_item);
            ImageView iv_item_icon=(ImageView)convertView.findViewById(R.id.iv_item_icon);
            TextView tv_item_content=(TextView)convertView.findViewById(R.id.tv_item_content);
            iv_item_icon.setImageResource(icons[position]);
            tv_item_content.setText(items[position]);
            return convertView;
        }
    }
}
