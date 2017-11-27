package com.pgg.icookapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.http.protocol.FeedBackProtocol;

/**
 * Created by PDD on 2017/7/26.
 */
public class FeedBackActivity extends BaseActivity {

    private EditText edit_feedback;
    private Button btn_submit;
    private static int code;
    private static String userId=LoginActivity.myLoginInfo.id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        TextView tv_title=(TextView)findViewById(R.id.tv_title);
        ImageButton iv_menu=(ImageButton)findViewById(R.id.btn_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("意见反馈");
        iv_menu.setBackgroundResource(R.drawable.item_left);

        edit_feedback = (EditText)findViewById(R.id.edit_feedback);
        btn_submit = (Button)findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=edit_feedback.getText().toString();
                if (!s.equals("")){
                    try {
                        UpdateFeedBackConnection updateFeedBackConnection=new UpdateFeedBackConnection(s);
                        updateFeedBackConnection.start();
                        updateFeedBackConnection.join();
                        if (code==0){
                            Toast.makeText(UIUtils.getContext(),"感谢您的反馈，我们后尽快回复！",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(UIUtils.getContext(),"提交失败，请检查网络之后再重试",Toast.LENGTH_SHORT).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private class UpdateFeedBackConnection extends Thread{
        private String suggestion;
        public UpdateFeedBackConnection( final String suggestion) {
            super();
            this.suggestion=suggestion;
        }
        @Override
        public void run() {
            code=new FeedBackProtocol(userId,suggestion).getData();
        }
    }
}
