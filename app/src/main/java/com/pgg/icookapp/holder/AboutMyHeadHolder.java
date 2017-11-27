package com.pgg.icookapp.holder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.BitmapHelper;
import com.pgg.icookapp.Utils.StringUtils;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.activity.LoginActivity;
import com.pgg.icookapp.activity.MainActivity;
import com.pgg.icookapp.activity.SettingThemePicActivity;
import com.pgg.icookapp.domain.MyInfoDomain;
import com.pgg.icookapp.domain.MyLoginInfo;
import com.pgg.icookapp.fragment.MeFragment;
import com.pgg.icookapp.view.CircleImageView;
import com.pgg.icookapp.view.MyDialogView;

/**
 * Created by PDD on 2017/9/16.
 */
public class AboutMyHeadHolder extends MyBaseHolder<MyInfoDomain> {

    private CircleImageView iv_info_face;
    private ImageView iv_sex_icon;
    private TextView tv_info_name;
    private TextView tv_info_school;
    private TextView tv_info_major;
    private RelativeLayout rl_head_icon;
    private BitmapUtils bitmapUtils;
    private Activity activity;
    public AboutMyHeadHolder (Activity activity){
        this.activity=activity;
    }

    @Override
    public View initView() {
        View view= UIUtils.getView(R.layout.my_info_detail);
        rl_head_icon = (RelativeLayout) view.findViewById(R.id.rl_head_icon);
        iv_info_face = (CircleImageView) view.findViewById(R.id.iv_info_face);
        iv_sex_icon = (ImageView) view.findViewById(R.id.iv_sex_icon);
        tv_info_name = (TextView) view.findViewById(R.id.tv_info_name);
        tv_info_school = (TextView) view.findViewById(R.id.tv_info_school);
        tv_info_major = (TextView) view.findViewById(R.id.tv_info_major);
        rl_head_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyDialogView dialog = new MyDialogView(activity);
                View view1=UIUtils.getView(R.layout.activity_setting_pic);
                view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog != null) {
                            dialog.cancel();
                        }
                    }
                });
                Button btn_change_back = (Button) view1.findViewById(R.id.btn_change_back);
                btn_change_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        if (dialog != null) {
                            dialog.cancel();
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            final AlertDialog alertDialog = builder.create();
                            alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                            View view = UIUtils.getView(R.layout.dialog_show_face);
                            TextView tv_open_camera = (TextView) view.findViewById(R.id.tv_open_camera);
                            TextView tv_open_photo = (TextView) view.findViewById(R.id.tv_open_photo);
                            alertDialog.setView(view);
                            alertDialog.show();
                            tv_open_camera.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //打开相机
                                    String state = Environment.getExternalStorageState();// 获取内存卡可用状态
                                    if (state.equals(Environment.MEDIA_MOUNTED)) {
// 内存卡状态可用
                                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                        activity.startActivityForResult(cameraIntent, 1);
                                    } else {
// 不可用
                                        Toast.makeText(activity, "内存不可用", Toast.LENGTH_LONG).show();
                                    }
                                    alertDialog.dismiss();
                                }
                            });

                            tv_open_photo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //打开相册
                                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    activity.startActivityForResult(intent, 2);
                                    alertDialog.dismiss();
                                }
                            });
                        }
                    }
                });
                dialog.show();
                dialog.setCancelable(false);
                dialog.setContentView(view1);// show方法要在前面
            }
        });
        bitmapUtils = BitmapHelper.getmBitmapUtils();
        return view;
    }

    @Override
    public void RefreshPage(MyInfoDomain data) {
        if (data!=null){
            if (data.gender==1){
                iv_sex_icon.setImageResource(R.drawable.man_icon);
            }else {
                iv_sex_icon.setImageResource(R.drawable.woman_icon);
            }
            if (!StringUtils.isEmpty(data.backgroudImageUrl)){
                bitmapUtils.display(rl_head_icon,data.backgroudImageUrl);
            }
            tv_info_name.setText(data.name);
            tv_info_school.setText(data.school);
            tv_info_major.setText(data.major);
            if (!StringUtils.isEmpty(data.icon)){
                bitmapUtils.display(iv_info_face, data.icon);
            }
        }
    }
}
