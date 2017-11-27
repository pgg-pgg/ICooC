package com.pgg.icookapp.http.protocol;

import android.os.Message;

import com.pgg.icookapp.Utils.PostFaceUtils;
import com.pgg.icookapp.domain.ResumeInfo;

import java.io.IOException;
import java.lang.reflect.Field;

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
 * Created by PDD on 2017/11/23.
 */

public class test {
    public static void main(String[] arg0) throws IllegalAccessException {
        ResumeInfo resumeInfo = new ResumeInfo();
        resumeInfo.name=".name";
        resumeInfo.gender="gender";
        resumeInfo.birthYear="birthYear";
        Field[] fields = resumeInfo.getClass().getFields();

        //
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        builder.addFormDataPart("userId", "0df11119-3ff7-4316-b60e-c19dd1bd55a7");
        for (int i = 0; i < fields.length; i++){
            builder.addFormDataPart(fields[i].getName(), fields[i].get(resumeInfo) + "");
            System.out.println(fields[i].getName()+"        "+ fields[i].get(resumeInfo) );
        }

        RequestBody requestBody = builder.build();
        Request request = new Request.Builder().
                url("http://192.168.1.122:8080/online/resu/saveResume.do")
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

            }
        });
    }

}
