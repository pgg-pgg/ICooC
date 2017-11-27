package com.pgg.icookapp.http.protocol;

import com.pgg.icookapp.Utils.StringUtils;
import com.pgg.icookapp.http.HttpHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PDD on 2017/7/28.
 */
public class RegisterProtocol extends BaseProtocol<Integer> {

    private String phone,password,school,major;
    public RegisterProtocol(String phone, String password, String school, String  major){
        this.phone=phone;
        this.password=password;
        this.school=school;
        this.major=major;
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
        return "user/register.do";
    }

    @Override
    public String getParam() {
        return "phone="+phone+"&password="+password+"&school="+school+"&major="+major;
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
