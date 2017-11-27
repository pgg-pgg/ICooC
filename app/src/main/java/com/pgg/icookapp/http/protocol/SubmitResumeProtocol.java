package com.pgg.icookapp.http.protocol;

import android.util.Log;

import com.pgg.icookapp.Utils.StringUtils;
import com.pgg.icookapp.http.HttpHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PDD on 2017/7/28.
 */
public class SubmitResumeProtocol extends BaseProtocol<Integer> {

    private String userId,resume;
    public SubmitResumeProtocol(String userId,String resume){
        this.userId=userId;
        this.resume=resume;
    }
    @Override
    public Integer getData() {
        String data=getDataFromServer();
        if (!StringUtils.isEmpty(data)){
            return parseJson(data);
        }else {
            return -1;
        }
    }

    @Override
    public String getDataFromServer() {
        HttpHelper.HttpResult result=HttpHelper.post(HttpHelper.URL + getKey() + "?" + getParam(), resume.getBytes());
        if (result!=null){
            String json=result.getString();
            Log.e("dasdrawrawewwa",json);
            if (!StringUtils.isEmpty(json)){
                return json;
            }
        }
        return null;
    }

    @Override
    public String getKey() {
        return "resu/saveResume.do";
    }

    @Override
    public String getParam() {
        return "userId="+userId+"&resume="+resume;
    }

    @Override
    public Integer parseJson(String data) {
        try {
            JSONObject jo=new JSONObject(data);
            return jo.getInt("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
