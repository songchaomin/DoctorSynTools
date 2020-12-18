package com.kuka.utils;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.kuka.config.YamlPropertySourceFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Service
@PropertySource(value = {"classpath:application-${spring.profiles.active}.yml"},factory = YamlPropertySourceFactory.class)
public class Md5Utils {
    @Value("${dubhe.appKey}")
    private  String appKey;
    @Value("${dubhe.secret}")
    private  String secret;
    @Value("${dubhe.dubheUrl}")
    private  String dubheUrl;
    @Value("${dubhe.tenantCode}")
    private  String tenantCode;
    private static final String version="1.0";
    private static final String sign_method="md5";
    private static final String format="json";

    private  String getSign(TreeMap<String,Object> sortMap,String requestBody){
        //1、转换sortMap转成String对象
        StringBuilder sb=new StringBuilder();
        sb.append(secret);
        for (Map.Entry<String, Object> entry : sortMap.entrySet()){
            sb.append(entry.getKey()).append(entry.getValue());
        }
        sb.append(requestBody);
        sb.append(secret);
        //2、生成MD5码
        String md5 = md5(sb.toString().getBytes());
        return md5;
    }

    public  String getSingnUrl(String apiMethod,String requestBody) throws UnsupportedEncodingException {
        TreeMap<String, Object> sortMap = builderRequestParamMap(apiMethod);
        String md5=getSign(sortMap,requestBody);
        StringBuilder sb=new StringBuilder();
        sb.append(dubheUrl).append("?");
        for (Map.Entry<String, Object> entry : sortMap.entrySet()){
            sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(),"utf-8")).append("&");
        }
        sb.append("sign").append("=").append(md5);
        String responseUrl=sb.toString();
        return responseUrl;
    }

    private  TreeMap<String,Object> builderRequestParamMap(String apiMethod) {
        TreeMap<String,Object> sortMap=new TreeMap<String,Object>();
        sortMap.put("app_key",appKey);
        sortMap.put("method",apiMethod);
        sortMap.put("format",format);
        sortMap.put("v",version);
        sortMap.put("sign_method",sign_method);
        sortMap.put("timestamp", DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        return sortMap;
    }

    private  String md5(byte[] b) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(b);
            byte[] hash = md.digest();
            StringBuffer outStrBuf = new StringBuffer(32);
            for (int i = 0; i < hash.length; i++) {
                int v = hash[i] & 0xFF;
                if (v < 16) {
                    outStrBuf.append('0');
                }
                outStrBuf.append(Integer.toString(v, 16).toUpperCase());
            }
            return outStrBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new String(b);
        }
    }

    /**
     * post请求（用于请求json格式的参数）
     * @param url
     * @param params
     * @return
     */
    public  String doPost(String url, String params) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;

        try {
            response = httpclient.execute(httpPost);
            //MailUtils.sendMail(JSONObject.toJSONString(response.getLocale()));
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                return jsonString;
            } else{
                log.error("请求返回:"+state+"("+url+")");
            }
        }
        finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
