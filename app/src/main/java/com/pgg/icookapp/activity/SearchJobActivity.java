package com.pgg.icookapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.pgg.icookapp.Manager.ActivityManager;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.DrawableUtils;
import com.pgg.icookapp.Utils.PrefUtils;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.http.protocol.UpdateJobPosProtocol;
import com.pgg.icookapp.view.FlowLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * Created by PDD on 2017/9/10.
 */
public class SearchJobActivity extends BaseActivity implements View.OnClickListener {

    private EditText edit_search;
    private TextView btn_cancel;
    private FrameLayout container_hot_search;
    private ListView list_search_history;
    private FlowLayout flowLayout;
    private long exitTime;

    private Set<String> list_history=new HashSet<>();

    private static String[] data=new String[]{"Java","PHP"
            ,"Android","Web前端","iOS","运营","产品经理"};
    private TextView tv_clear_search;
    private MyHistoryListAdapter adapter;

    private ArrayList<String> s;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchjob);
//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        edit_search = (EditText)findViewById(R.id.edit_search);
        edit_search.clearFocus();
        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    s.add(edit_search.getText().toString());
                    list_history.add(edit_search.getText().toString());
                    PrefUtils.putStringSet("list_history", list_history);
                    Intent intent = new Intent(SearchJobActivity.this, ShowSearchJobActivity.class);
                    intent.putExtra("key", edit_search.getText().toString());
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
        btn_cancel = (TextView)findViewById(R.id.btn_cancel);
        container_hot_search = (FrameLayout)findViewById(R.id.container_hot_search);
        list_search_history = (ListView)findViewById(R.id.list_search_history);
        adapter = new MyHistoryListAdapter();
        list_search_history.setAdapter(adapter);
        list_search_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchJobActivity.this, ShowSearchJobActivity.class);
                intent.putExtra("key",s.get(position));
                startActivity(intent);
                finish();
            }
        });
        tv_clear_search = (TextView)findViewById(R.id.tv_clear_search);
        tv_clear_search.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        int padding= UIUtils.dp2px(10);
        container_hot_search.setPadding(padding,padding,padding,padding);
        flowLayout = new FlowLayout(UIUtils.getContext());
        flowLayout.setVerticalSpacing(10);

        for (String value:data) {
            final TextView textView = new TextView(UIUtils.getContext());
            textView.setText(value);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(padding, padding, padding, padding);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textView.setTextColor(Color.WHITE);

            Random random = new Random();
            int r = 30 + random.nextInt(200);
            int g = 30 + random.nextInt(200);
            int b = 30 + random.nextInt(200);

            int color = 0xffcecece;
            Drawable selector = DrawableUtils.getStateListDrawable(Color.rgb(r, g, b), color, UIUtils.dp2px(6));
            textView.setBackground(selector);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list_history.add(textView.getText().toString());
                    Intent intent = new Intent(SearchJobActivity.this, ShowSearchJobActivity.class);
                    intent.putExtra("key", textView.getText().toString());
                    startActivity(intent);
                    finish();
                    PrefUtils.putStringSet("list_history", list_history);
                }
            });
            flowLayout.addView(textView);
        }
        container_hot_search.addView(flowLayout);
    }

    class MyHistoryListAdapter extends BaseAdapter{
        public MyHistoryListAdapter() {
            super();
            s=new ArrayList<>();
            if (PrefUtils.getStringSet("list_history", null)!=null){
                for (String s1:PrefUtils.getStringSet("list_history", null)){
                    s.add(s1);
                }
            }

        }

        @Override
        public int getCount() {
            if (PrefUtils.getStringSet("list_history",null)==null){
                return 0;
            }else {
                return PrefUtils.getStringSet("list_history",null).size();
            }
        }

        @Override
        public String getItem(int position) {
           return s.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                convertView=UIUtils.getView(R.layout.list_history_item);
                viewHolder = new ViewHolder();
                viewHolder.tv_history_item=(TextView)convertView.findViewById(R.id.tv_history_item);
                convertView.setTag(viewHolder);
            }else {
                viewHolder=(ViewHolder)convertView.getTag();
            }
            viewHolder.tv_history_item.setText(getItem(position));
            return convertView;
        }
    }

    static class ViewHolder{
        TextView tv_history_item;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit(); // 在这里进行点击判断
            return false;
            }
        return super.onKeyDown(keyCode, event);
    }

        //退出环信登录
    private void signOut(){
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(UIUtils.getContext(), LoginActivity.class);
                startActivity(intent);
                ActivityManager.finish();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(UIUtils.getContext(), "退出错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            // 点击间隔大于两秒，做出提示
            Toast.makeText(UIUtils.getContext(), "再按一次退出应用", Toast.LENGTH_LONG).show();
            exitTime = System.currentTimeMillis();
            } else {
            // 连续点击量两次，进行应用退出的处理
            signOut();
            System.exit(0);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:
                Intent intent=new Intent(SearchJobActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_clear_search:
                PrefUtils.removeStringSet("list_history");
                adapter.notifyDataSetChanged();
                break;
        }
    }


}
