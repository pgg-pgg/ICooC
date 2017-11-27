package com.pgg.icookapp.Utils;
import android.util.Log;
import com.pgg.icookapp.activity.LoginActivity;
import com.pgg.icookapp.domain.ResumeInfo;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by PDD on 2017/9/13.
 */
public class PostJsonUtils {

    public static String URL = "http://39.108.2.139/online/resu/saveResume.do";
    private DefaultHttpClient httpClient;
    StringBuilder result = new StringBuilder();
    private static final int TIMEOUT = 60;
    public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");

    public static ResumeInfo initResumeInfo(){
        ResumeInfo resumeInfo=new ResumeInfo();
        resumeInfo.name=PrefUtils.getString("base_name","");
        resumeInfo.gender=PrefUtils.getString("base_sex","");
        resumeInfo.birthYear=PrefUtils.getString("base_birth_year","");
        resumeInfo.birtyMonth=PrefUtils.getString("base_birth_month","");
        resumeInfo.status=PrefUtils.getString("base_grade","");
        resumeInfo.phone=PrefUtils.getString("base_phone","");
        resumeInfo.email=PrefUtils.getString("base_email","");
        resumeInfo.projectBeginYear=PrefUtils.getString("exp_year","");
        resumeInfo.projectBeginMonth=PrefUtils.getString("exp_month","");
        resumeInfo.projectEndYear=PrefUtils.getString("exp_end","");
        resumeInfo.projectEndMonth="";
        resumeInfo.projectName=PrefUtils.getString("exp_name","");
        resumeInfo.projectPosition=PrefUtils.getString("exp_duty","");
        resumeInfo.projectContent=PrefUtils.getString("exp_des","");
        resumeInfo.graduateTime=PrefUtils.getString("teach_time","");
        resumeInfo.school=PrefUtils.getString("teach_school","");
        resumeInfo.academic=PrefUtils.getString("teach_grade","");
        resumeInfo.discipline=PrefUtils.getString("teach_major","");
        resumeInfo.workRemarks=PrefUtils.getString("teach_des","");
        resumeInfo.expectPosition=PrefUtils.getString("hopejob_name","");
        resumeInfo.workTimeType=PrefUtils.getString("hopejob_time","");
        resumeInfo.expectWorkCity=PrefUtils.getString("hopejob_city","");
        resumeInfo.expectSalary=PrefUtils.getString("hopejob_money","");
        resumeInfo.workRemarks=PrefUtils.getString("hopejob_info","");
        resumeInfo.workBeginYear=PrefUtils.getString("workBeginYear","");
        resumeInfo.workBeginMonth=PrefUtils.getString("workBeginMonth","");
        resumeInfo.workEndYear=PrefUtils.getString("workEndYear","");
        resumeInfo.workEndMonth=PrefUtils.getString("workEndMonth","");
        resumeInfo.workCompany=PrefUtils.getString("work_company","");
        resumeInfo.workPosition=PrefUtils.getString("work_position","");
        resumeInfo.workContent=PrefUtils.getString("work_content","");

        return resumeInfo;
    }

    public static String resumeJson(){
        ResumeInfo resumeInfo=initResumeInfo();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("name",resumeInfo.name);
            jsonObject.put("gender",resumeInfo.gender);
            jsonObject.put("birthYear",resumeInfo.birthYear);
            jsonObject.put("birtyMonth",resumeInfo.birtyMonth);
            jsonObject.put("status",resumeInfo.status);
            jsonObject.put("phone",resumeInfo.phone);
            jsonObject.put("email",resumeInfo.email);
            jsonObject.put("projectBeginYear",resumeInfo.projectBeginYear);
            jsonObject.put("projectBeginMonth",resumeInfo.projectBeginMonth);
            jsonObject.put("projectEndYear",resumeInfo.projectEndYear);
            jsonObject.put("projectEndMonth",resumeInfo.projectEndMonth);
            jsonObject.put("projectName",resumeInfo.projectName);
            jsonObject.put("projectPosition",resumeInfo.projectPosition);
            jsonObject.put("projectContent",resumeInfo.projectContent);
            jsonObject.put("graduateTime", resumeInfo.graduateTime);
            jsonObject.put("school", resumeInfo.school);
            jsonObject.put("academic", resumeInfo.academic);
            jsonObject.put("discipline", resumeInfo.discipline);
            jsonObject.put("workRemarks", resumeInfo.workRemarks);
            jsonObject.put("workBeginYear", resumeInfo.workBeginYear);
            jsonObject.put("workBeginMonth",resumeInfo.workBeginMonth);
            jsonObject.put("workEndYear",resumeInfo.workEndYear);
            jsonObject.put("workEndMonth", resumeInfo.workEndMonth);
            jsonObject.put("workCompany", resumeInfo.workCompany);
            jsonObject.put("workPosition", resumeInfo.workPosition);
            jsonObject.put("workContent",resumeInfo.workContent);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    //http://39.108.2.139/online/resu/saveResume.do

    public String submitPost(){
//        try {
////            URL url=new URL("http://39.108.2.139/online/resu/saveResume.do");
////            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
////            connection.setDoOutput(true);
////            connection.setDoInput(true);
////            connection.setRequestMethod("POST");
////            connection.setUseCaches(false);
////            connection.setInstanceFollowRedirects(true);
//////            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
////            connection.connect();
////            DataOutputStream dataout=new DataOutputStream(connection.getOutputStream());
//////            String parm ="userId="+ URLEncoder.encode(LoginActivity.myLoginInfo.id,"utf-8")+"&resume="+URLEncoder.encode(resumeJson(),"utf-8");
////            String parm = URLEncoder.encode("userId=" + LoginActivity.myLoginInfo.id +"&resume=" + resumeJson(),"utf-8");
////            dataout.write(parm.getBytes());
////            dataout.flush();
////            dataout.close();
//
//            URL url=new URL("http://39.108.2.139/online/resu/saveResume.do");
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            //通过setRequestMethod将conn设置成POST方法
//            conn.setRequestMethod("POST");
//            //调用conn.setDoOutput()方法以显式开启请求体
//            conn.setDoOutput(true);
//            DataOutputStream dataout=new DataOutputStream(conn.getOutputStream());
////            String parm ="userId="+ URLEncoder.encode(LoginActivity.myLoginInfo.id,"utf-8")+"&resume="+URLEncoder.encode(resumeJson(),"utf-8");
//            String parm = URLEncoder.encode("userId=" + LoginActivity.myLoginInfo.id +"&resume=" + resumeJson(),"utf-8");
//            dataout.write(parm.getBytes());
//            InputStream is = conn.getInputStream();
//            StringBuilder sb = new StringBuilder();
//            int ch = 0;
//            byte[] buf = new byte[1024];
//            while ((ch = is.read()) > 0){
//                sb.append(new String(buf, 0, ch, "utf-8"));
//            }
//            is.close();
//
////            BufferedReader bf=new BufferedReader(new InputStreamReader(connection.getInputStream()));
////            String line;
////            StringBuilder sb = new StringBuilder();
////            while ((line=bf.readLine())!=null){
////                sb.append(bf.readLine());
////            }
////            bf.close();
////            connection.disconnect();
//            Log.e("eafefeaf",sb.toString());
//            return sb.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";

        HttpParams paramsw = createHttpParams();
        httpClient = new DefaultHttpClient(paramsw);
        HttpPost post = new HttpPost( "http://39.108.2.139/online/resu/saveResume.do");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userId", LoginActivity.myLoginInfo.id));
        try { //向服务器写json
            StringEntity se = new StringEntity("resume" + resumeJson().toString());
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(se);
            post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse httpResponse = httpClient.execute(post);
            int httpCode = httpResponse.getStatusLine().getStatusCode();
            if (httpCode == HttpURLConnection.HTTP_OK && httpResponse != null) {
                Header[] headers = httpResponse.getAllHeaders();
                HttpEntity entity = httpResponse.getEntity();
                Header header = httpResponse.getFirstHeader("content-type");
                //读取服务器返回的json数据（接受json服务器数据）
                InputStream inputStream = entity.getContent();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);// 读字符串用的。
                String s;
                while (((s = reader.readLine()) != null)) {
                    result.append(s);
                }
                reader.close();// 关闭输入流
            }else {
                Log.e("erafat",result.toString());
            }
            Log.e("dafafasd",result.toString());
            return result.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public int String2Json() {
        try {
            JSONObject jo=new JSONObject(submitPost());
            if (jo.has("code")){
                return jo.getInt("code");
            }else {
                return -1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static final HttpParams createHttpParams() {

        final HttpParams params = new BasicHttpParams();

        HttpConnectionParams.setStaleCheckingEnabled(params, false);

        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT * 1000);

        HttpConnectionParams.setSoTimeout(params, TIMEOUT * 1000);

        HttpConnectionParams.setSocketBufferSize(params, 8192 * 5);

        return params;

    }

    private void postJson() {
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        //json为String类型的json数据
        RequestBody requestBody = RequestBody.create(JSON, resumeJson());
        //创建一个请求对象
        Request request = new Request.Builder()
                .url("http://192.168.0.102:8080/TestProject/JsonServlet")
                .post(requestBody)
                .build();
        //发送请求获取响应
        try {
            Response response=okHttpClient.newCall(request).execute();
            //判断请求是否成功
            if(response.isSuccessful()){
                //打印服务端返回结果
                Log.i("PGG",response.body().string());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
