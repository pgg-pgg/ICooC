package com.pgg.icookapp.http.protocol;

import com.pgg.icookapp.Utils.StringUtils;
import com.pgg.icookapp.http.HttpHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PDD on 2017/7/28.
 */
public class CollectProtocol extends BaseProtocol<Integer> {


    //http://39.108.2.139/online/job/favorite.do?userId=5b4ea936-60c8-4466-b97b-efbf5fc64edc&jobId=46fa0403-b8eb-496c-97aa-9a7a4fde03c2
    private String userId,jobId;
    public CollectProtocol(String userId, String jobId){
        this.userId=userId;
        this.jobId=jobId;
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
        HttpHelper.HttpResult result=HttpHelper.get(HttpHelper.URL + getKey() +"?"+ getParam());
        if (result!=null){
            String json=result.getString();
            System.out.println(json);
            if (!StringUtils.isEmpty(json)){
                return json;
            }
        }
        return null;
    }

    @Override
    public String getKey() {
        return "job/favorite.do";
    }

    @Override
    public String getParam() {
        return "userId="+userId+"&jobId="+jobId;
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
