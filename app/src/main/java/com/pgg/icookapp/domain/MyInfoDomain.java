package com.pgg.icookapp.domain;

/**
 * Created by PDD on 2017/9/27.
 */


/**
 *  "backgroudImageUrl": "http://i4.bvimg.com/602708/2e49e5518676bbe5t.jpg",
 "gender": 1,
 "icon": "http://i2.bvimg.com/593002/7cabfe5273d360c5t.jpg",
 "id": "0c773685-d4af-44a5-8209-8c2d895b8b57",
 "introduction": "鍛冨憙鍛�",
 "major": "淇＄",
 "name": "鎴戦棶闂�",
 "password": "e10adc3949ba59abbe56e057f20f883e",
 "phone": "13689105271",
 "school": "瑗块偖",
 "shares": []

 resume:
 "academic": "鏈",
 "birthYear": "",
 "birtyMonth": "",
 "discipline": "淇℃伅绠＄悊涓庝俊鎭郴缁�",
 "email": "979150046@qq.com",
 "gender": "鐢�",
 "graduateTime": "2018骞�",
 "id": "a7a60ef2-3a7c-49ac-a97e-b72ff781b9e9",
 "name": "褰腐褰�",
 "phone": "18829591066",
 "projectBeginMonth": "",
 "projectBeginYear": "",
 "projectContent": "杩欐槸涓€涓负澶у鐢熷氨涓氶毦鑰岀敓鐨勫钩鍙帮紒",
 "projectEndMonth": "",
 "projectEndYear": "鑷充粖",
 "projectName": "ICooC椤圭洰",
 "projectPosition": "Android瀹㈡埛绔紑鍙�",
 "school": "瑗垮畨閭數澶у",
 "status": "澶т笁",
 "userId": "81463116-c21b-4296-a9d9-264c7f2a4db1",
 "workBeginMonth": "",
 "workBeginYear": "",
 "workCompany": "鐧惧害",
 "workContent": "鍝堝搱",
 "workEndMonth": "",
 "workEndYear": "",
 "workPosition": "Android寮€鍙戝伐绋嬪笀",
 "workRemarks": "Android寮€鍙戜笌娴嬭瘯"
 */
public class MyInfoDomain{
    public String backgroudImageUrl;
    public String introduction;
    public int gender;
    public String icon;
    public String id;
    public String major;
    public String name;
    public String phone;
    public String password;
    public String school;
    public ResumeInfoDomain resumeInfoDomain;

    public static class ResumeInfoDomain{
        public String graduateTime;//毕业时间
        public String projectContent;//项目描述
        public String status;//当前学籍
        public String workTimeType;//工作时间类型  实习、兼职、全职
        public String expectSalary;//期望工资
        public String birthYear;//出生年
        public String expectWorkCity;//期望工作城市
        public String birtyMonth;//出生月
        public String projectEndYear;//项目结束年
        public String workEndYear;//离职结束年
        public String school;//毕业院校
        public String workPosition;//入职职位
        public String workBeginYear;//入职时间年
        public String workCompany;//入职公司
        public String name;//姓名
        public String projectName;//项目名
        public String expectPosition;//期望工作职位
        public String projectPosition;//项目职责
        public String gender;//性别
        public String email;//联系邮箱
        public String projectBeginMonth;//项目开始月
        public String phone;//联系方式
        public String projectEndMonth;//项目结束月
        public String workEndMonth;//离职时间月
        public String workContent;//入职内容
        public String workRemarks;//补充说明
        public String projectBeginYear;//项目开始年
        public String discipline;//专业
        public String workBeginMonth;//入职时间月
        public String academic;//学历
    }
}
