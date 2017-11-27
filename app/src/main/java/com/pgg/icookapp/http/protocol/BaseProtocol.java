package com.pgg.icookapp.http.protocol;

import com.pgg.icookapp.Utils.IOUtils;
import com.pgg.icookapp.Utils.StringUtils;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.http.HttpHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by PDD on 2017/7/27.
 */
public abstract class BaseProtocol<T> {

    public T getData(){
        String data="";
        if (StringUtils.isEmpty(data)||data.equals("{}")){
            data=getDataFromServer();
        }
        if (!StringUtils.isEmpty(data)){
            return parseJson(data);
        }
        return null;
    }

    public String getDataFromServer() {
        HttpHelper.HttpResult result=HttpHelper.get(HttpHelper.URL + getKey() +"?"+ getParam());
        if (result!=null){
            String json=result.getString();
            System.out.println(json);
            if (!StringUtils.isEmpty(json)){
               return json;
            }
        }
        return null;
    }

    //写缓存
    private void setCache(String result){
        //url作为文件名，Json作为文件内容
        File FileDir= UIUtils.getContext().getCacheDir();
        FileWriter writer=null;
        long deadline=System.currentTimeMillis()+30*60*1000;
        File file_cache=new File(FileDir,"app"+"?"+ getParam());
        try {
            writer = new FileWriter(file_cache);
            writer.write(deadline+"\n");
            writer.write(result);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(writer);
        }
    }
    //读缓存
    private String getCache(){
        File FileDir= UIUtils.getContext().getCacheDir();
        BufferedReader bf=null;
        File file_cache=new File(FileDir,"app" + "?" + getParam());
        if (file_cache.exists()){
            try {
                bf =new BufferedReader(new FileReader(file_cache));
                StringBuffer sb=new StringBuffer();
                long deadline=Long.parseLong(bf.readLine());
                String line=null;
                if (deadline>System.currentTimeMillis()){
                    while((line=bf.readLine())!=null){
                        sb.append(line);
                    }
                    return sb.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                IOUtils.close(bf);
            }
        }
        return null;
    }
    public abstract String getKey();
    public abstract String getParam();
    public abstract T parseJson(String data);
}
