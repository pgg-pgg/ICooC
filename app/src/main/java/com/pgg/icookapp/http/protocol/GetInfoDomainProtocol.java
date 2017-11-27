package com.pgg.icookapp.http.protocol;

import com.pgg.icookapp.domain.MyInfoDomain;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PDD on 2017/7/29.
 */
public class GetInfoDomainProtocol extends BaseProtocol<MyInfoDomain> {

    private String userId;
    public GetInfoDomainProtocol(String userId){
        this.userId=userId;
    }
    public String getKey() {
        return "user/getMyInfo.do";
    }

    public String getParam() {
        return "userId="+userId;
    }

    public MyInfoDomain parseJson(String data) {
        try {
            MyInfoDomain myInfo=null;
            JSONObject jo=new JSONObject(data);
            if (jo.has("data")){
                JSONObject jo1=jo.getJSONObject("data");
                myInfo = new MyInfoDomain();
                if (jo1.has("backgroudImageUrl")){
                    myInfo.backgroudImageUrl=jo1.getString("backgroudImageUrl");
                }else {
                    myInfo.backgroudImageUrl="";
                }
                if (jo1.has("introduction")){
                    myInfo.introduction=jo1.getString("introduction");
                }else {
                    myInfo.introduction="";
                }
                if (jo1.has("icon")){
                    myInfo.icon=jo1.getString("icon");
                }else {
                    myInfo.icon="";
                }
                myInfo.gender=jo1.getInt("gender");
                myInfo.id=jo1.getString("id");
                myInfo.major=jo1.getString("major");
                myInfo.name=jo1.getString("name");
                myInfo.phone=jo1.getString("phone");
                myInfo.password=jo1.getString("password");
                myInfo.school=jo1.getString("school");
            }
            if (jo.has("resume")){
                JSONObject jo2=jo.getJSONObject("resume");
                MyInfoDomain.ResumeInfoDomain resumeInfoDomain=new MyInfoDomain.ResumeInfoDomain();
                resumeInfoDomain.academic=jo2.getString("academic");
                resumeInfoDomain.birthYear=jo2.getString("birthYear");
                resumeInfoDomain.birtyMonth=jo2.getString("birtyMonth");
                resumeInfoDomain.discipline=jo2.getString("discipline");
                resumeInfoDomain.email=jo2.getString("email");
                resumeInfoDomain.gender=jo2.getString("gender");
                resumeInfoDomain.graduateTime=jo2.getString("graduateTime");
                resumeInfoDomain.name=jo2.getString("name");
                resumeInfoDomain.phone=jo2.getString("phone");
                resumeInfoDomain.projectBeginMonth=jo2.getString("projectBeginMonth");
                resumeInfoDomain.projectBeginYear=jo2.getString("projectBeginYear");
                resumeInfoDomain.projectContent=jo2.getString("projectContent");
                resumeInfoDomain.projectEndMonth=jo2.getString("projectEndMonth");
                resumeInfoDomain.projectEndYear=jo2.getString("projectEndYear");
                resumeInfoDomain.projectName=jo2.getString("projectName");
                resumeInfoDomain.projectPosition=jo2.getString("projectPosition");
                resumeInfoDomain.school=jo2.getString("school");
                resumeInfoDomain.status=jo2.getString("status");
                resumeInfoDomain.workBeginMonth=jo2.getString("workBeginMonth");
                resumeInfoDomain.workBeginYear=jo2.getString("workBeginYear");
                resumeInfoDomain.workContent=jo2.getString("workContent");
                resumeInfoDomain.workCompany=jo2.getString("workCompany");
                resumeInfoDomain.workEndMonth=jo2.getString("workEndMonth");
                resumeInfoDomain.workEndYear=jo2.getString("workEndYear");
                resumeInfoDomain.workPosition=jo2.getString("workPosition");
                resumeInfoDomain.workRemarks=jo2.getString("workRemarks");
                myInfo.resumeInfoDomain=resumeInfoDomain;
            }
            return myInfo;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
