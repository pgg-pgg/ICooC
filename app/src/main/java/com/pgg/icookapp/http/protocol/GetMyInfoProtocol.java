package com.pgg.icookapp.http.protocol;

import com.pgg.icookapp.Utils.StringUtils;
import com.pgg.icookapp.domain.MyInfo;
import com.pgg.icookapp.http.HttpHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PDD on 2017/7/29.
 */
public class GetMyInfoProtocol extends BaseProtocol<MyInfo> {

    private String userId;
    public GetMyInfoProtocol(String userId){
        this.userId=userId;
    }
    public String getKey() {
        return "user/getMyInfo.do";
    }

    public String getParam() {
        return "userId="+userId;
    }

    public MyInfo parseJson(String data) {
        try {
            MyInfo myInfo=null;
            JSONObject jo=new JSONObject(data);
            if (jo.has("data")){
                JSONObject jo1=jo.getJSONObject("data");
                myInfo = new MyInfo();
                myInfo.backgroudImageUrl=jo1.getString("backgroudImageUrl");
                myInfo.company=jo1.getString("company");
                myInfo.email=jo1.getString("email");
                myInfo.gender=jo1.getInt("gender");
                myInfo.icon=jo1.getString("icon");
                myInfo.id=jo1.getString("id");
                myInfo.major=jo1.getString("major");
                myInfo.name=jo1.getString("name");
                myInfo.phone=jo1.getString("phone");
                myInfo.password=jo1.getString("password");
                myInfo.province=jo1.getString("province");
                myInfo.school=jo1.getString("school");
                myInfo.workType=jo1.getInt("workType");
            }
            return myInfo;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
