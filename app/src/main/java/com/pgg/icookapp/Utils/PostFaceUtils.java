package com.pgg.icookapp.Utils;

import com.pgg.icookapp.domain.FaceInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PDD on 2017/9/23.
 */
public class PostFaceUtils {
    public static FaceInfo AnalysisJson(String json){
        FaceInfo faceInfo;
        try {
            JSONObject jo=new JSONObject(json);
            faceInfo=new FaceInfo();
            faceInfo.height=jo.getInt("height");
            faceInfo.width=jo.getInt("width");
            faceInfo.findurl=jo.getString("findurl");
            faceInfo.htmlurl=jo.getString("htmlurl");
            faceInfo.linkurl=jo.getString("linkurl");
            faceInfo.markdown=jo.getString("markdown");
            faceInfo.s_url=jo.getString("s_url");
            faceInfo.t_url=jo.getString("t_url");
            faceInfo.type=jo.getString("type");
            faceInfo.ubburl=jo.getString("ubburl");
            faceInfo.size=jo.getInt("size");
            return faceInfo;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
