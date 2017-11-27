package com.pgg.icookapp.http.protocol;

import com.pgg.icookapp.Utils.PrefUtils;
import com.pgg.icookapp.Utils.StringUtils;
import com.pgg.icookapp.domain.JobInfo;
import com.pgg.icookapp.http.HttpHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by PDD on 2017/8/4.
 *
 *
 * &userId=81463116-c21b-4296-a9d9-264c7f2a4db1
 */
public class GetJobListProtocol extends BaseProtocol<ArrayList<JobInfo>> {
    private int page;
    private String userId;
    public GetJobListProtocol(int page,String userId){
        this.page=page;
        this.userId=userId;
    }

    @Override
    public ArrayList<JobInfo> getData() {
        String data=getDataFromServer();
        if (!StringUtils.isEmpty(data)){
            return parseJson(data);
        }else {
            return null;
        }
    }

    @Override
    public String getDataFromServer() {
        HttpHelper.HttpResult result=HttpHelper.get(HttpHelper.URL + getKey() + "?" + getParam());
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
        return "job/getJobInfoList.do";
    }

    @Override
    public String getParam() {
        return "size=3&page="+page+"&userId="+userId+"&type=0";
    }

    @Override
    public ArrayList<JobInfo> parseJson(String data) {
        ArrayList<JobInfo> list=new ArrayList<>();
        try {
            JSONObject jo=new JSONObject(data);
            int count=jo.getInt("count");
            PrefUtils.putInt("count",count);
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
