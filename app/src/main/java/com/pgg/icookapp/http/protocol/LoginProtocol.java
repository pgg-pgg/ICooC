package com.pgg.icookapp.http.protocol;

import com.pgg.icookapp.Utils.StringUtils;
import com.pgg.icookapp.domain.MyLoginInfo;
import com.pgg.icookapp.http.HttpHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by PDD on 2017/7/27.
 */
public class LoginProtocol extends BaseProtocol<MyLoginInfo> {

    private String phone,password;
    public LoginProtocol(String phone,String passward){
        this.phone=phone;
        this.password=passward;
    }

    @Override
    public MyLoginInfo getData() {
        String data=getDataFromServer();
        if (!StringUtils.isEmpty(data)){
            return parseJson(data);
        }else {
            return null;
        }
    }

    @Override
    public String getDataFromServer() {
        HttpHelper.HttpResult result=HttpHelper.get(HttpHelper.URL + getKey() +"?"+ getParam());
        if (result!=null){
            String json=result.getString();
    //        System.out.println(json);
            if (!StringUtils.isEmpty(json)){
                return json;
            }
        }
        return null;
    }

    @Override
    public String getKey() {
        return "user/login.do";
    }

    @Override
    public String getParam() {
        return "userid=0&phone="+phone+"&password="+password;
    }

    @Override
    public MyLoginInfo parseJson(String data) {
        try {
            if (data!=null){
                JSONObject jo=new JSONObject(data);
                int a=jo.getInt("code");
                MyLoginInfo myLoginInfo=new MyLoginInfo();
                myLoginInfo.code=a;
                if (a==0){
                    JSONObject jo2=jo.getJSONObject("data");
                    myLoginInfo.backgroudImageUrl=jo2.getString("backgroudImageUrl");
                    myLoginInfo.company=jo2.getString("company");
                    myLoginInfo.email=jo2.getString("email");
                    myLoginInfo.gender=jo2.getInt("gender");
                    myLoginInfo.icon=jo2.getString("icon");
                    myLoginInfo.id=jo2.getString("id");
                    myLoginInfo.introduction=jo2.getString("introduction");
                    myLoginInfo.major=jo2.getString("major");
                    myLoginInfo.name=jo2.getString("name");
                    myLoginInfo.occupation=jo2.getString("occupation");
                    myLoginInfo.password=jo2.getString("password");
                    myLoginInfo.phone=jo2.getString("phone");
                    myLoginInfo.province=jo2.getString("province");
                    myLoginInfo.salary=jo2.getString("salary");
                    myLoginInfo.school=jo2.getString("school");

                    JSONArray ja=jo2.getJSONArray("shares");

                    myLoginInfo.tJobType=jo2.getString("tJobType");
                    myLoginInfo.tPlace=jo2.getString("tPlace");
                    myLoginInfo.tPosition=jo2.getString("tPosition");
                    myLoginInfo.university=jo2.getString("university");
                    myLoginInfo.workType=jo2.getInt("workType");

                    return myLoginInfo;
                }else {
                    myLoginInfo.message="登录失败";
                    return myLoginInfo;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
