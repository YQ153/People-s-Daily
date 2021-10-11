package com.yanqi.rewrite.post;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * @author YQQ
 * @program: People's Daily
 * @description: 请求网络，接收数据
 * @date 2021-09-30 19:22:24
 */
public class NetController {
    private String url;

    public static CloseableHttpResponse getResponse(String url){
        // 1.打开游览器,创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 2.输入网址,发起get请求创建HttpGet对象
        HttpGet httpGet = new HttpGet(url);
        // 3.按回车发起请求,返回响应,使用HttpClient对象发起请求
        try {
            return httpClient.execute(httpGet);
        } catch (IOException e) {
            return null;

        }
    }


}
