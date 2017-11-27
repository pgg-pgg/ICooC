package com.pgg.icookapp.domain;

import org.json.JSONArray;

/**
 * Created by PDD on 2017/7/27.
 */
public class MyLoginInfo extends MyInfoDomain {
    public String backgroudImageUrl;//背景图片的Url
    public String company;//公司名称
    public String email;//邮箱
    public int gender;//性别，-1代表男，0代表女
    public String icon;//头像
    public String id;//用户id
    public String introduction;//个性签名
    public String major;//专业
    public String name;//姓名，昵称
    public String occupation;//职业
    public String password;//密码，MD5
    public String phone;//注册手机号
    public String province;//省份
    public String salary;//工资
    public String school;//学校
    //public JSONArray shares;//简历工作分享
    public String tJobType;//工作性质
    public String tPlace;//工作地点
    public String tPosition;
    public String university;
    public int workType;

    public int code;//返回码
    public String message;//提示信息
}
