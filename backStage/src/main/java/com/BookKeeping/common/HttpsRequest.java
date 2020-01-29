package com.BookKeeping.common;

import com.alibaba.fastjson.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Https数据获取
 */

public class HttpsRequest{
    public String request (String url){

        //String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx4d29257ff29de851&secret=1e17bdcea1a00407a083b74de2395e83&js_code=021hhT302qrunW0pY6502FJP302hhT3Y&grant_type=authorization_code";
        try{
            SSLContext sc = createSslContext();
            HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier((s, sslSession) -> true);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();
            StringBuilder result = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while (null != (line = br.readLine())) {
                    result.append(line).append("\n");
                }
            }
            conn.disconnect();

            //System.out.printf(result.toString());
            return result.toString();
        }catch (KeyManagementException ke){
            System.out.println("KeyManagementException");
        }catch (NoSuchAlgorithmException ne){
            System.out.println("NoSuchAlgorithmException");
        }catch (IOException ie){
            System.out.println("IOException");
        }
        return "ERROR";
    }

    private static SSLContext createSslContext() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSL");

        sc.init(null, new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s){

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s){

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, new java.security.SecureRandom());

        return sc;
    }

    public JSONObject domain(String type,String code){
        JSONObject jo = JSONObject.parseObject("{'message':'Error',}");
        if(type.equals("getSession_key")){
            System.out.println("获取session和openid");
            String result=request("https://api.weixin.qq.com/sns/jscode2session?appid=wx4d29257ff29de851&secret=1e17bdcea1a00407a083b74de2395e83&js_code="+code+"&grant_type=authorization_code");
            jo = JSONObject.parseObject(result);
            return jo;
        }else if(type.equals("getUserData")){
            System.out.println("获取用户信息");
        }
        return jo;
    }
}
