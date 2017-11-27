package com.pgg.icookapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pgg.icookapp.R;

/**
 * Created by PDD on 2017/9/26.
 */
public class SettingThemePicActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_pic);
        Button btn_change_back=(Button)findViewById(R.id.btn_change_back);
        btn_change_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
