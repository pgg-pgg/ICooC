package com.pgg.icookapp.http.protocol;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PDD on 2017/9/24.
 */
public class ChangeHeadProtocol extends BaseProtocol<Integer> {

    private String userId,imageUrl;
    private int type;
    public ChangeHeadProtocol(String userId,String imageUrl,int type) {
        this.userId=userId;
        this.imageUrl=imageUrl;
        this.type=type;
    }

    @Override
    public String getKey() {
        return "user/changeHead.do";
    }

    @Override
    public String getParam() {
        return "userId="+userId+"&imageUrl="+imageUrl+"&type="+type;
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
