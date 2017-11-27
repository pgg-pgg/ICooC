package com.pgg.icookapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.pgg.icookapp.Manager.ActivityManager;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.PostFaceUtils;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.domain.FaceInfo;
import com.pgg.icookapp.fragment.BaseFragment;
import com.pgg.icookapp.fragment.FactoryFragment;
import com.pgg.icookapp.http.protocol.ChangeHeadProtocol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends BaseActivity {

    private ViewPager viewPager;
    private RadioGroup rb_group;
    private ViewPagerAdapter viewPagerAdapter;
    private BaseFragment fragment;
    private RadioButton rb_home;
    private RadioButton rb_call;
    private RadioButton rb_message;
    private RadioButton rb_me;
    private EditText edit_search;
    private TextView tv_mark;
    private ImageButton iv_add_menu,iv_left_menu;
    private ViewPager.OnPageChangeListener listener;
    private Bitmap photo;
    private FaceInfo faceInfo;
    private int code;


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1) {
                faceInfo = (FaceInfo) msg.obj;
                try {
                    ChangeHeadConnection changeHeadConnection = new ChangeHeadConnection(LoginActivity.myLoginInfo.id, faceInfo.t_url, 0);
                    changeHeadConnection.start();
                    changeHeadConnection.join();
                    if (code == 0) {
                        Toast.makeText(UIUtils.getContext(), "主题图片修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UIUtils.getContext(), "主题图片修改失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if (msg.what==2){
                faceInfo = (FaceInfo) msg.obj;
                try {
                    ChangeHeadConnection changeHeadConnection = new ChangeHeadConnection(LoginActivity.myLoginInfo.id, faceInfo.t_url, 0);
                    changeHeadConnection.start();
                    changeHeadConnection.join();
                    if (code == 0) {
                        Toast.makeText(UIUtils.getContext(), "主题图片修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UIUtils.getContext(), "主题图片修改失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
//        sm.setBehindOffset(400);

        viewPager = (ViewPager)findViewById(R.id.vp_content);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        rb_group = (RadioGroup)findViewById(R.id.rb_group);
        rb_home = (RadioButton)findViewById(R.id.rb_home);
        rb_call = (RadioButton)findViewById(R.id.rb_call);
        rb_message = (RadioButton)findViewById(R.id.rb_message);
        rb_me = (RadioButton)findViewById(R.id.rb_me);
        edit_search = (EditText)findViewById(R.id.edit_search);
        edit_search.setInputType(InputType.TYPE_NULL);
        edit_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SearchJobActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tv_mark = (TextView)findViewById(R.id.tv_mark);
        iv_add_menu = (ImageButton)findViewById(R.id.iv_add_menu);
        iv_add_menu.setVisibility(View.GONE);
        iv_left_menu=(ImageButton)findViewById(R.id.iv_left_menu);
        iv_left_menu.setBackgroundResource(R.drawable.img_menu);
        iv_left_menu.setVisibility(View.GONE);
//        iv_left_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggle();
//            }
//        });


        rb_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        viewPager.setCurrentItem(0, false);
                        break;
                    case R.id.rb_call:
                        viewPager.setCurrentItem(1, false);
                        break;
                    case R.id.rb_message:
                        viewPager.setCurrentItem(2, false);
                        break;
                    case R.id.rb_me:
                        viewPager.setCurrentItem(3, false);
                        break;
                }
            }
        });

        rb_home.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rb_home.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                if (listener != null && fragment != null) {
                    listener.onPageSelected(0);
                }
            }
        });
        listener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rb_home.setChecked(true);
                        edit_search.setVisibility(View.VISIBLE);
                        tv_mark.setVisibility(View.GONE);
                        iv_add_menu.setVisibility(View.GONE);
                        break;
                    case 1:
                        rb_call.setChecked(true);
                        edit_search.setVisibility(View.GONE);
                        tv_mark.setText("通讯录");
                        tv_mark.setVisibility(View.VISIBLE);
                        iv_add_menu.setVisibility(View.VISIBLE);
                        iv_add_menu.setBackgroundResource(R.drawable.add_menu);
                        break;
                    case 2:
                        rb_message.setChecked(true);
                        edit_search.setVisibility(View.GONE);
                        tv_mark.setText("消息");
                        tv_mark.setVisibility(View.VISIBLE);
                        iv_add_menu.setVisibility(View.GONE);
                        break;
                    case 3:
                        rb_me.setChecked(true);
                        edit_search.setVisibility(View.GONE);
                        tv_mark.setText("我的");
                        iv_add_menu.setVisibility(View.GONE);
                        tv_mark.setVisibility(View.VISIBLE);
                        break;
                }
                BaseFragment fragment = FactoryFragment.createFragment(position);
                fragment.loadData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        viewPager.setOnPageChangeListener(listener);
        viewPager.setOffscreenPageLimit(3);//设置缓存view 的个数（实际有3个，缓存2个+正在显示的1个）

//        LeftMenuFragment leftMenuFragment=new LeftMenuFragment();
//        FragmentManager fm=getSupportFragmentManager();
//        android.support.v4.app.FragmentTransaction tranction=fm.beginTransaction();
//        tranction.replace(R.id.fl_left, leftMenuFragment, null);
//        tranction.commit();
    }
//    public void toggle(){
//        SlidingMenu sm= MainActivity.this.getSlidingMenu();
//        sm.toggle();
//    }
    class ViewPagerAdapter extends FragmentPagerAdapter{

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int i) {
            fragment = FactoryFragment.createFragment(i);
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    //点击返回键返回桌面而不是退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    //退出环信登录
//    private void signOut(){
//        EMClient.getInstance().logout(false, new EMCallBack() {
//            @Override
//            public void onSuccess() {
//                Intent intent = new Intent(UIUtils.getContext(), LoginActivity.class);
//                startActivity(intent);
//                ActivityManager.finish();
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                Toast.makeText(UIUtils.getContext(),"退出错误",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onProgress(int i, String s) {
//
//            }
//        });
//    }
//
//    public void exit() {
//        if ((System.currentTimeMillis() - exitTime) > 3000) {
//            // 点击间隔大于两秒，做出提示
//            Toast.makeText(UIUtils.getContext(), "再按一次退出应用", Toast.LENGTH_LONG).show();
//            exitTime = System.currentTimeMillis();
//            } else {
//            // 连续点击量两次，进行应用退出的处理
//            signOut();
//            System.exit(0);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 1:
                    //打开相机处理逻辑
                    if (resultCode==RESULT_OK) {
                        if (data.getData() != null || data.getExtras() != null) { // 防止没有返回结果
                            Uri uri = data.getData();
                            if (uri != null) {
                                this.photo = BitmapFactory.decodeFile(uri.getPath()); // 拿到图片
                            }
                            if (photo == null) {
                                Bundle bundle = data.getExtras();
                                if (bundle != null) {
                                    photo = (Bitmap) bundle.get("data");
                                    FileOutputStream fileOutputStream = null;
                                    try {
// 获取 SD 卡根目录 生成图片并
                                        String saveDir = Environment
                                                .getExternalStorageDirectory()
                                                + "/dhj_Photos";
// 新建目录
                                        File dir = new File(saveDir);
                                        if (!dir.exists())
                                            dir.mkdir();
// 生成文件名
                                        SimpleDateFormat t = new SimpleDateFormat("yyyyMMddssSSS");
                                        final String filename = "MT" + (t.format(new Date())) + ".jpg";
// 新建文件
                                        final File file = new File(saveDir, filename);
// 打开文件输出流
                                        fileOutputStream = new FileOutputStream(file);
// 生成图片文件
                                        this.photo.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                                        RequestBody requestBody = new MultipartBody.Builder()
                                                .setType(MultipartBody.FORM)
                                                .addFormDataPart("deadline", String.valueOf(System.currentTimeMillis() / 1000 + 60))
                                                .addFormDataPart("aid", "1336663")
                                                .addFormDataPart("Token", "8771bfc294d04602145025ae4ff662d005d0e193:PfLRceYFkiV50_uLPiReCk8XqrU=:eyJkZWFkbGluZSI6MTUwNjMzMzU2NiwiYWN0aW9uIjoiZ2V0IiwidWlkIjoiNjAyNzA4IiwiYWlkIjoiMTMzNjY2MyIsImZyb20iOiJmaWxlIn0=")
                                                .addFormDataPart("file", file.getName(),RequestBody.create(MediaType.parse("image/*"), file)).build();
                                        Request request = new Request.Builder().
                                                url("http://up.imgapi.com/")
                                                .post(requestBody)
                                                .build();
                                        OkHttpClient client = new OkHttpClient.Builder().build();
                                        client.newCall(request).enqueue(new Callback() {
                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                                System.out.println("error:" +e.getMessage());
                                            }
                                            @Override
                                            public void onResponse(Call call, Response response) throws IOException {
                                                System.out.println("onResponse:" + response.code() + ",msg:"+response.message());
                                                if (response.isSuccessful()) {
                                                    ResponseBody body = response.body();
                                                    if (body != null) {
                                                        //System.out.println("success:" + body.string());
                                                        faceInfo= PostFaceUtils.AnalysisJson(body.string());
                                                        Message message=new Message();
                                                        message.what=2;
                                                        message.obj=faceInfo;
                                                        handler.sendMessage(message);
                                                    }
                                                }
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        if (fileOutputStream != null) {
                                            try {
                                                fileOutputStream.close();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                            Toast.makeText(UIUtils.getContext(), "正在设置...", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                case 2:
                    //打开系统相册处理逻辑
                    if (requestCode == 2 && resultCode == RESULT_OK) {
                        Uri uri = data.getData();
                        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                            File file=new File(path);
                            RequestBody requestBody = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("deadline", String.valueOf(System.currentTimeMillis() / 1000 + 60))
                                    .addFormDataPart("aid", "1301479")
                                    .addFormDataPart("Token", "5e4f240872e1e331d48905171c652ba14a6f16ab:maz_V6eRqULmclAroioZDQ5nb18=:eyJkZWFkbGluZSI6MTQ5MzYxMzE4MCwiYWN0aW9uIjoiZ2V0IiwidWlkIjoiNTkzMDAyIiwiYWlkIjoiMTMwMTQ3OSIsImZyb20iOiJmaWxlIn0=")
                                    .addFormDataPart("file", file.getName(),RequestBody.create(MediaType.parse("image/*"), file)).build();
                            Request request = new Request.Builder().
                                    url("http://up.imgapi.com/")
                                    .post(requestBody)
                                    .build();
                            OkHttpClient client = new OkHttpClient.Builder().build();
                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    System.out.println("error:" +e.getMessage());
                                }
                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    System.out.println("onResponse:" + response.code() + ",msg:"+response.message());
                                    if (response.isSuccessful()) {
                                        ResponseBody body = response.body();
                                        if (body != null) {
                                            //System.out.println("success:" + body.string());
                                            faceInfo=PostFaceUtils.AnalysisJson(body.string());
                                            Message message=new Message();
                                            message.what=1;
                                            message.obj=faceInfo;
                                            handler.sendMessage(message);
                                        }
                                    }
                                }
                            });
                        }
                        Toast.makeText(UIUtils.getContext(), "正在设置...", Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private class ChangeHeadConnection extends Thread{
        private String userId,imageUrl;
        private int type;
        public ChangeHeadConnection( String userId,String imageUrl,int type) {
            super();
            this.userId=userId;
            this.imageUrl=imageUrl;
            this.type=type;
        }
        @Override
        public void run() {
            code = new ChangeHeadProtocol(userId,imageUrl,type).getData();
        }
    }

}
