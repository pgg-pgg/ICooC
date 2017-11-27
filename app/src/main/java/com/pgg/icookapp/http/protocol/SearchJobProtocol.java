package com.pgg.icookapp.http.protocol;

import com.pgg.icookapp.domain.JobInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by PDD on 2017/8/4.
 *
 *    http://39.108.2.139/online/job/searchJob.do?size=2&page=1&key=IOS&userId=5b4ea936-60c8-4466-b97b-efbf5fc64edc
 *
 * &userId=81463116-c21b-4296-a9d9-264c7f2a4db1
 */
public class SearchJobProtocol extends BaseProtocol<ArrayList<JobInfo>> {
    private int page;
    private String key,userId;
    public SearchJobProtocol(int page,String key,String userId){
        this.page=page;
        this.key=key;
        this.userId=userId;
    }
    @Override
    public String getKey() {
        return "job/searchJob.do";
    }

    @Override
    public String getParam() {
        return "size=3&page="+page+"&key="+key+"&userId="+userId;
    }

    @Override
    public ArrayList<JobInfo> parseJson(String data) {
        ArrayList<JobInfo> list=new ArrayList<>();
        try {
            JSONObject jo=new JSONObject(data);
            if (jo.has("data")){
                JSONArray ja=jo.getJSONArray("data");
                for (int i=0;i<ja.length();i++){
                    JobInfo jobInfo=new JobInfo();
                    JobInfo.compId compId=new JobInfo.compId();
                    JSONObject jo1=(JSONObject)ja.get(i);
                    JSONObject jo2=jo1.getJSONObject("compId");
                    compId.certify=jo2.getBoolean("certify");
                    compId.compCity=jo2.getString("compCity");
                    compId.compLocation=jo2.getString("compLocation");
                    compId.compLogoUrl=jo2.getString("compLogoUrl");
                    compId.compName=jo2.getString("compName");
                    compId.financingType=jo2.getString("financingType");
                    compId.id=jo2.getString("id");
                    compId.introduction=jo2.getString("introduction");
                    compId.scale=jo2.getString("scale");
                    compId.type=jo2.getString("type");
                    compId.welfare=jo2.getString("welfare");
                    jobInfo.compId=compId;
                    jobInfo.detailPlace=jo1.getString("detailPlace");
                    jobInfo.education=jo1.getString("education");
                    jobInfo.experience=jo1.getString("experience");
                    jobInfo.id=jo1.getString("id");
                    jobInfo.interviewerid=jo1.getString("interviewerid");
                    jobInfo.isFavorite=jo1.getInt("isFavorite");
                    jobInfo.jobName=jo1.getString("jobName");
                    JobInfo.jobTag jobTag=new JobInfo.jobTag();
                    JSONObject jo3=jo1.getJSONObject("jobTag");
                    jobTag.id=jo3.getString("id");
                    jobTag.name=jo3.getString("name");
                    jobInfo.jobTag=jobTag;
                    JSONObject jo4=jo3.getJSONObject("jobType");
                    JobInfo.jobType jobType=new JobInfo.jobType();
                    jobType.id=jo4.getString("id");
                    jobType.name=jo4.getString("name");
                    jobInfo.jobType=jobType;
                    jobInfo.modified=jo1.getString("modified");
                    jobInfo.phone=jo1.getString("phone");
                    jobInfo.place=jo1.getString("place");
                    jobInfo.req=jo1.getString("req");
                    jobInfo.salary=jo1.getString("salary");
                    jobInfo.shortDesc=jo1.getString("shortDesc");
                    jobInfo.workType=jo1.getInt("workType");

                    list.add(jobInfo);
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
