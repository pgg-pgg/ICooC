package com.pgg.icookapp.activity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.hyphenate.util.Utils;
import com.lidroid.xutils.BitmapUtils;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.BitmapHelper;
import com.pgg.icookapp.Utils.PostFaceUtils;
import com.pgg.icookapp.Utils.PostJsonUtils;
import com.pgg.icookapp.Utils.StringUtils;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.domain.AboutMyInfo;
import com.pgg.icookapp.domain.FaceInfo;
import com.pgg.icookapp.domain.MyInfoDomain;
import com.pgg.icookapp.domain.MyLoginInfo;
import com.pgg.icookapp.http.protocol.ChangeHeadProtocol;
import com.pgg.icookapp.http.protocol.GetInfoDomainProtocol;
import com.pgg.icookapp.http.protocol.LoginProtocol;
import com.pgg.icookapp.http.protocol.UpdateGenderProtocol;
import com.pgg.icookapp.http.protocol.UpdateIntroductionProtocol;
import com.pgg.icookapp.http.protocol.UpdateJobPosProtocol;
import com.pgg.icookapp.http.protocol.UpdateNameProtocol;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

/**
 * Created by PDD on 2017/7/25.
 */
public class MyInfoItemActivity extends BaseActivity {

    private String[] list_info,list_item_info;
    private int[] list_mark;
    private ArrayList<AboutMyInfo> data;
    private ImageView iv_back;
    private ListView lv_info;
    private final static int TYPE_NORMAL=0;
    private final static int TYPE_NULL=1;
    private final static int TYPE_FACE=2;
    private Bitmap photo;
    private String picPath;
    private static MyLoginInfo myLoginInfo;
    private BitmapUtils mBitmapUtils;
    private static int code;
    private static MyInfoDomain myInfo;

    private static String userId=LoginActivity.myLoginInfo.id;
    private static MyInfoListAdapter adapter;
    private FaceInfo faceInfo;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1) {
                faceInfo = (FaceInfo) msg.obj;
                try {
                    ChangeHeadConnection changeHeadConnection = new ChangeHeadConnection(myLoginInfo.id, faceInfo.t_url, 1);
                    changeHeadConnection.start();
                    changeHeadConnection.join();
                    if (code == 0) {
                        Toast.makeText(UIUtils.getContext(), "头像修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UIUtils.getContext(), "头像修改失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ImageView iv_face=(ImageView)lv_info.getChildAt(1).findViewById(R.id.iv_face);
                mBitmapUtils.display(iv_face,faceInfo.t_url);
            }else if (msg.what==2){
                faceInfo = (FaceInfo) msg.obj;
                try {
                    ChangeHeadConnection changeHeadConnection = new ChangeHeadConnection(myLoginInfo.id, faceInfo.t_url, 1);
                    changeHeadConnection.start();
                    changeHeadConnection.join();
                    if (code == 0) {
                        Toast.makeText(UIUtils.getContext(), "头像修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UIUtils.getContext(), "头像修改失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ImageView iv_face=(ImageView)lv_info.getChildAt(1).findViewById(R.id.iv_face);
                mBitmapUtils.display(iv_face,faceInfo.t_url);
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
//        SlidingMenu sm=getSlidingMenu();
//        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        iv_back = (ImageView)findViewById(R.id.iv_back);
        lv_info = (ListView)findViewById(R.id.lv_info);
        lv_info.setSelector(new ColorDrawable());
        lv_info.setDivider(null);
        lv_info.setCacheColorHint(Color.TRANSPARENT);
        adapter = new MyInfoListAdapter();
        lv_info.setAdapter(adapter);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        //头像Item点击事件
                        //实现打开相机功能
                        showDialogUpdate_face();
                        break;
                    case 2:
                        showDialogUpdate(position);
                        break;
                    case 7:
                        showDialogUpdate_sex();
                        break;
                    case 8:
                        Intent intent = new Intent(UIUtils.getContext(), UpdateHopeJobActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 9:
                        showDialogUpdate(position);
                        break;
                    default:
                        break;
                }
            }
        });
        mBitmapUtils = BitmapHelper.getmBitmapUtils();
    }

    private void showDialogUpdate(final int pos){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        View view=UIUtils.getView(R.layout.dialog_show);
        final EditText edit_new_name=(EditText)view.findViewById(R.id.edit_new_name);
        TextView tv_des_dialog=(TextView)view.findViewById(R.id.tv_des_dialog);
        TextView tv_title_dialog=(TextView)view.findViewById(R.id.tv_title_dialog);
        Button btn_save=(Button)view.findViewById(R.id.btn_save_dialog);
        Button btn_cancel=(Button)view.findViewById(R.id.btn_cancel_dialog);
        switch (pos){
            case 2:
                tv_title_dialog.setText("更改昵称");
                tv_des_dialog.setText("好名字可以让你的朋友更容易记住你");
                edit_new_name.setHint("请输入新昵称");
                break;
            case 9:
                tv_title_dialog.setText("更改个性签名");
                tv_des_dialog.setText("完整的个人信息有助于我们更好地为你匹配");
                edit_new_name.setHint("请输入个性签名");
                break;
        }
        alertDialog.setView(view);
        alertDialog.show();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s;
                switch (pos) {
                    case 2:
                        s = edit_new_name.getText().toString();
                        if (!s.equals("")) {
                            myLoginInfo.name = s;
                            //将数据传给服务器
                            try {
                                UpdateNameConnection updateNameConnection = new UpdateNameConnection(s);
                                updateNameConnection.start();
                                updateNameConnection.join();
                                if (code == 0) {
                                    Toast.makeText(UIUtils.getContext(), "昵称修改成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(UIUtils.getContext(), "昵称修改失败，请重试", Toast.LENGTH_SHORT).show();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            MyInfoListAdapter adapter1 = new MyInfoListAdapter();
                            lv_info.setAdapter(adapter1);
                        }
                        break;
                    case 9:
                        s = edit_new_name.getText().toString();
                        if (!s.equals("")) {
                            myLoginInfo.introduction = s;
                            //将数据传给服务器
                            try {
                                UpdateIntroductionConnection updateIntroductionConnection = new UpdateIntroductionConnection(s);
                                updateIntroductionConnection.start();
                                updateIntroductionConnection.join();
                                if (code == 0) {
                                    Toast.makeText(UIUtils.getContext(), "个性签名修改成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(UIUtils.getContext(), "个性签名修改失败，请重试", Toast.LENGTH_SHORT).show();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            MyInfoListAdapter adapter1 = new MyInfoListAdapter();
                            lv_info.setAdapter(adapter1);
                        }
                        break;
                    default:
                        break;
                }
                alertDialog.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
    private void showDialogUpdate_sex(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        View view=UIUtils.getView(R.layout.dialog_show_sex);
        RadioGroup rg_sex=(RadioGroup)view.findViewById(R.id.rg_sex);
        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_man:
                        myLoginInfo.gender = 1;
                        //将数据传给服务器
                        try {
                            UpdateGenderConnection updateGenderConnection = new UpdateGenderConnection(0);
                            updateGenderConnection.start();
                            updateGenderConnection.join();
                            if (code == 0) {
                                Toast.makeText(UIUtils.getContext(), "性别修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UIUtils.getContext(), "性别修改失败，请重试", Toast.LENGTH_SHORT).show();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        MyInfoListAdapter adapter1 = new MyInfoListAdapter();
                        lv_info.setAdapter(adapter1);
                        alertDialog.dismiss();
                        break;
                    case R.id.rb_women:
                        myLoginInfo.gender = 0;
                        //将数据传给服务器
                        try {
                            UpdateGenderConnection updateGenderConnection = new UpdateGenderConnection(1);
                            updateGenderConnection.start();
                            updateGenderConnection.join();
                            if (code == 0) {
                                Toast.makeText(UIUtils.getContext(), "性别修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UIUtils.getContext(), "性别修改失败，请重试", Toast.LENGTH_SHORT).show();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        MyInfoListAdapter adapter2 = new MyInfoListAdapter();
                        lv_info.setAdapter(adapter2);
                        alertDialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void showDialogUpdate_face(){
        //8771bfc294d04602145025ae4ff662d005d0e193:_RZ6Pl9EVk1sBOLzjCFAxzxvnMo=:eyJkZWFkbGluZSI6MTUwMTQwMjU5NiwiYWN0aW9uIjoiZ2V0IiwidWlkIjoiNjAyNzA4IiwiYWlkIjoiMTMzNjY2MyIsImZyb20iOiJmaWxlIn0=
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                    startActivityForResult(cameraIntent, 1);
                } else {
// 不可用
                    Toast.makeText(MyInfoItemActivity.this, "内存不可用", Toast.LENGTH_LONG).show();
                }
                alertDialog.dismiss();
            }
        });

        tv_open_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开相册
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
                alertDialog.dismiss();
            }
        });

    }

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
                                    Drawable drawable = new BitmapDrawable(photo);
                                    ImageView iv_face = (ImageView) lv_info.getChildAt(1).findViewById(R.id.iv_face);
                                    iv_face.setImageDrawable(drawable);
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
// 相片的完整路径
                                        this.picPath = file.getPath();
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
                                                        faceInfo=PostFaceUtils.AnalysisJson(body.string());
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
                            Toast.makeText(UIUtils.getContext(), "正在设置,请稍等...", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(UIUtils.getContext(), "正在设置,请稍等...", Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        }
    }

//    private Bitmap getImageThumbnail(String imagePath, int width, int height) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true; //关于inJustDecodeBounds的作用将在下文叙述
//        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
//        int h = options.outHeight;//获取图片高度
//        int w = options.outWidth;//获取图片宽度
//        int scaleWidth = w / width; //计算宽度缩放比
//        int scaleHeight = h / height; //计算高度缩放比
//        int scale = 1;//初始缩放比
//        if (scaleWidth < scaleHeight) {//选择合适的缩放比
//            scale = scaleWidth;
//        } else {
//            scale = scaleHeight;
//        }
//        if (scale <= 0) {//判断缩放比是否符合条件
//            scale = 1;
//        }
//        options.inSampleSize = scale;
//        // 重新读入图片，读取缩放后的bitmap，注意这次要把inJustDecodeBounds 设为 false
//        options.inJustDecodeBounds = false;
//        bitmap = BitmapFactory.decodeFile(imagePath, options);
//        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
//        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
//        return bitmap;
//    }
    /**
     * ListView适配器
     */
    class MyInfoListAdapter extends BaseAdapter{
        public MyInfoListAdapter() {
            super();
            GetMyInfoDomainConn myInfoDomainConn=new GetMyInfoDomainConn(LoginActivity.myLoginInfo.id);
            myInfoDomainConn.start();
            try {
                myInfoDomainConn.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myLoginInfo=LoginActivity.myLoginInfo;
            list_info= UIUtils.getStringArray(R.array.myinfo);
            list_mark=new int[]{R.drawable.item_face,R.drawable.item_name,R.drawable.item_phonenum,R.drawable.item_school,
            R.drawable.item_profess,R.drawable.item_sex,R.drawable.item_hope,R.drawable.item_text};
            String sex;
            if (myInfo.gender==1){
                sex="男";
            }else {
                sex="女";
            }
            list_item_info=new String[]{myInfo.icon,myInfo.name,myInfo.phone,myInfo.school
                    ,myInfo.major,sex,getIntent().getStringExtra("hopejobname"),myInfo.introduction};

            data=new ArrayList<>();
            for (int i=0;i<list_mark.length;i++){
                AboutMyInfo info=new AboutMyInfo();
                info.my_image=list_mark[i];
                info.my_text=list_info[i];
                info.my_item_text=list_item_info[i];
                data.add(info);
            }
        }
        @Override
        public int getItemViewType(int position) {
            if (position==0||position==6){
                return TYPE_NULL;
            }else if (position==1){
                return TYPE_FACE;
            }else {
                return TYPE_NORMAL;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public int getCount() {
            return data.size()+2;
        }

        @Override
        public AboutMyInfo getItem(int position) {
            if (position==0||position==6){
                return null;
            }else if (position<=5){
                return data.get(position-1);
            }else {
                return data.get(position-2);
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type=getItemViewType(position);
            ViewHolder1 viewHolder1;
            ViewHolder2 viewHolder2;
            if (type==TYPE_NORMAL){
                if (convertView==null){
                    convertView=UIUtils.getView(R.layout.list_item_myinfo);
                    viewHolder1=new ViewHolder1();
                    viewHolder1.iv_myinfo_mark=(ImageView)convertView.findViewById(R.id.iv_myinfo_mark);
                    viewHolder1.tv_item_mark=(TextView)convertView.findViewById(R.id.tv_item_mark);
                    viewHolder1.tv_myinfo=(TextView)convertView.findViewById(R.id.tv_myinfo);
                    convertView.setTag(viewHolder1);
                }else {
                    viewHolder1=(ViewHolder1)convertView.getTag();
                }
                viewHolder1.tv_item_mark.setText(getItem(position).my_text);
                viewHolder1.iv_myinfo_mark.setImageResource(getItem(position).my_image);
                viewHolder1.tv_myinfo.setText(getItem(position).my_item_text);
            }else if (type==TYPE_NULL){
                convertView=UIUtils.getView(R.layout.list_item_me1);
            }else {
                if (convertView==null){
                    convertView=UIUtils.getView(R.layout.list_item_myinfo1);
                    viewHolder2=new ViewHolder2();
                    viewHolder2.iv_myinfo_mark=(ImageView)convertView.findViewById(R.id.iv_myinfo_mark1);
                    viewHolder2.tv_item_mark=(TextView)convertView.findViewById(R.id.tv_item_mark1);
                    viewHolder2.iv_face=(ImageView)convertView.findViewById(R.id.iv_face);
                    convertView.setTag(viewHolder2);
                }else {
                    viewHolder2=(ViewHolder2)convertView.getTag();
                }
                viewHolder2.tv_item_mark.setText(getItem(position).my_text);
                viewHolder2.iv_myinfo_mark.setImageResource(getItem(position).my_image);
                if (StringUtils.isEmpty(getItem(position).my_item_text)){
                    viewHolder2.iv_face.setImageResource(R.drawable.face_normal);
                }else {
                    mBitmapUtils.display(viewHolder2.iv_face,getItem(position).my_item_text);
                }
            }
            return convertView;
        }
    }
    static class ViewHolder1 {
        ImageView iv_myinfo_mark;
        TextView tv_item_mark;
        TextView tv_myinfo;
    }
    static class ViewHolder2 {
        ImageView iv_myinfo_mark;
        TextView tv_item_mark;
        ImageView iv_face;
    }

    private class UpdateNameConnection extends Thread{
        private String newUserName;
        public UpdateNameConnection( final String newUserName) {
            super();
            this.newUserName=newUserName;
        }
        @Override
        public void run() {
            code=new UpdateNameProtocol(userId,newUserName).getData();
        }
    }

    private class UpdateGenderConnection extends Thread{
        private int gender;
        public UpdateGenderConnection( final int gender) {
            super();
            this.gender=gender;
        }
        @Override
        public void run() {
            code=new UpdateGenderProtocol(userId,gender).getData();
        }
    }

    private class UpdateIntroductionConnection extends Thread{
        private String introduction;
        public UpdateIntroductionConnection( final String introduction) {
            super();
            this.introduction=introduction;
        }
        @Override
        public void run() {
            code=new UpdateIntroductionProtocol(userId,introduction).getData();
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

    private class GetMyInfoDomainConn extends Thread{
        private String userId;
        public GetMyInfoDomainConn(final String userId) {
            this.userId=userId;
        }
        @Override
        public void run() {
            myInfo=new GetInfoDomainProtocol(userId).getData();
        }
    }


}
