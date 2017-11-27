package com.pgg.icookapp.http.protocol;

import com.pgg.icookapp.domain.ImageInfo;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by PDD on 2017/7/31.
 */
public class ImageResProtocol extends BaseProtocol<ArrayList<ImageInfo>> {


    @Override
    public String getKey() {
        return "res/homeImages.do";
    }

    @Override
    public String getParam() {
        return "";
    }

    @Override
    public ArrayList<ImageInfo> parseJson(String data) {
        System.out.println("======data======"+data);
        ArrayList<ImageInfo> list=new ArrayList<>();
        try {
            JSONObject jo=new JSONObject(data);
            if (jo.has("data")){
                JSONArray ja=jo.getJSONArray("data");
                for (int i=0;i<ja.length();i++){
                    JSONObject jo1=(JSONObject)ja.get(i);
                    ImageInfo imageInfo=new ImageInfo();
                    imageInfo.id=jo1.getString("id");
                    imageInfo.imgeUrl=jo1.getString("imgeUrl");
                    imageInfo.targetUrl=jo1.getString("targetUrl");
                    list.add(imageInfo);
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
