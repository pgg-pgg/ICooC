package com.pgg.icookapp.Utils;

import android.os.CountDownTimer;
import android.widget.Button;

import com.pgg.icookapp.R;

/**
 * Created by PDD on 2017/7/28.
 */
public class TimeCountUtils extends CountDownTimer {

    private Button button;
    public TimeCountUtils(long millisInFuture, long countDownInterval,Button button) {
        super(millisInFuture, countDownInterval);
        this.button=button;
    }

    //计时过程显示
    @Override
    public void onTick(long millisUntilFinished) {
        button.setText(millisUntilFinished / 1000 + "秒后重新发送");
        button.setClickable(false);
    }
    //计时完成触发
    @Override
    public void onFinish() {
        button.setText("获取验证码");
        button.setClickable(true);
    }

}
