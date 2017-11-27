package com.pgg.icookapp.http.protocol;

import com.pgg.icookapp.Utils.StringUtils;
import com.pgg.icookapp.http.HttpHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PDD on 2017/7/29.
 */
public class UpdateNameProtocol extends BaseProtocol<Integer> {

    private String userId,newUserName;
    public UpdateNameProtocol(String userId, String newUserName){
        this.userId=userId;
        this.newUserName=newUserName;
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

    //http://39.108.2.139/online/user/changeName.do?apptype=0&userId=6f58ed20-35f1-4445-b1e0-dade7cbe53de&newUserName=ds
    @Override
    public String getKey() {
        return "user/changeName.do";
    }

    @Override
    public String getParam() {
        return "apptype=0&userId="+userId+"&newUserName="+newUserName;
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
