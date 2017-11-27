package com.pgg.icookapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by PDD on 2017/8/4.
 */
public class JobInfo implements Serializable{

    public String detailPlace;//详细地址
    public String education;//学历
    public String experience;//工作经验
    public String id;//求职者id
    public String interviewerid;//面试官id
    public int isFavorite;//是否收藏,0代表未收藏,1代表收藏
    public String jobName;//工作名称
    public String modified;//修改时间
    public String phone;//联系电话
    public String place;//工作地点
    public String req;//工作要求
    public String salary;//薪水
    public String shortDesc;//简短描述
    public int workType;//工作类型
    public compId compId;
    public jobTag jobTag;
    public jobType jobType;
    public int count;

    //关于发布职位的公司信息
    public static class compId implements Serializable {
        public boolean certify;//是否权威
        public String compCity;//公司所在城市
        public String compLocation;//公司所在地详细地址
        public String compLogoUrl;//公司logo
        public String compName;//公司名称
        public String financingType;//公司类型
        public String id;//公司id
        public String introduction;//公司简介
        public String scale;//公司规模
        public String type;//公司的经营方向
        public String welfare;//公司福利

    }

    //工作标签
    public static class jobTag implements Serializable{
        public String id;//这一份工作的id
        public String name;//这一份工作的具体名称
    }
    public static class jobType implements Serializable{
        public String id;//这是工作类型的id
        public String name;//这是工作类型的名称
    }
}
