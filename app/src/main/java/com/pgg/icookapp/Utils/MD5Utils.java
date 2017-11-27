package com.pgg.icookapp.Utils;

import java.lang.Integer;import java.lang.String;import java.lang.StringBuffer;import java.lang.System;import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by PDD on 2017/7/27.
 */
public class MD5Utils {
    public static String encoder(String pwd) {
        StringBuffer sb=new StringBuffer();
        try {
            MessageDigest md= MessageDigest.getInstance("MD5");
            byte[] bs=md.digest(pwd.getBytes());
            for(byte i:bs){
                int t=i&0xff;
                String hexstring=Integer.toHexString(t);
                if(hexstring.length()<2){
                    hexstring="0"+hexstring;
                }
                sb.append(hexstring);
                sb.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
