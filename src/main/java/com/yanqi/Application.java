package com.yanqi;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author YQQ
 * @program: People's Daily
 * @description: Download People's Daily automatically
 * @date 2021-09-02 22:20:53
 */
public class Application {
    /**
     * 程序入口
     * @param args
     */
    public static void main(String[] args){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM/dd");
        SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(date);
        String dataOnlyNum = yyyymmdd.format(date);

        errorHandler(format,dataOnlyNum);
    }

    private static int time = 0;
    private static void errorHandler(String dataByFormat,String dataOnlyNum){
        try {
            getPD(dataByFormat, dataOnlyNum);
        } catch (IOException e) {
//            错误出现三次后报错
            if (time<3){
                time++;
                System.out.println("错误：" + time);
                Application.errorHandler(dataByFormat,dataOnlyNum);
            }
            e.printStackTrace();
        }
    }

    public static void getPD(String dataByFormat,String dataOnlyNum) throws IOException {
        String urlHead = "http://paper.people.com.cn/rmrb/images/";
        String urlNameOfPage = "http://paper.people.com.cn/rmrb/html/"+ dataByFormat +"/nbs.D110000renmrb_01.htm";
        String nowUrl;
        String[] fileName = getNameOfPage(urlNameOfPage);
        File file1 = new File("C:\\Users\\YQQ\\Desktop\\" + dataOnlyNum);
        if (!file1.isDirectory()) {
            file1.mkdirs();
        }
        for (int i = 1; i <= fileName.length; i++){
            File file = new File("C:\\Users\\YQQ\\Desktop\\" + dataOnlyNum + "\\" + fileName[i-1] + ".pdf");
            if(file.exists()) {
                continue;
            }
            nowUrl = urlHead + dataByFormat + "/" + (i >= 10 ? i : ("0" + i)) + "/" + "rmrb" + dataOnlyNum + (i >= 10 ? i : ("0" + i)) + ".pdf";
            System.out.println(nowUrl);
            CloseableHttpResponse response = getResponse(nowUrl);
            // 4.解析响应,获取数据
            // 判断状态码是否是200
            if (response.getStatusLine().getStatusCode() == 200) {
                //获取网页源代码的用法
                InputStream stream = response.getEntity().getContent();

                FileOutputStream fileOutputStream = new FileOutputStream(file,true);
                byte[] bytes = new byte[1024*1024];
                int len = 0;
                while ((len = stream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes,0,len);
                }
                stream.close();
                fileOutputStream.close();
            }else {
                System.out.println("没有请求回数据哟！");
            }
        }
    }

    public static String[] getNameOfPage(String url){
        CloseableHttpResponse response = getResponse(url);
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = response.getEntity();
            Document html = null;
            try {
                html = Jsoup.parse(EntityUtils.toString(httpEntity, "utf8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements nameOfPage = html.getElementsByClass("swiper-slide");
            String[] res = new String[nameOfPage.size()];
            int p = 0;
            for (Element item : nameOfPage){
                res[p] = item.getElementById("pageLink").html();
                p++;
            }
            return res;
        }
        return null;
    }

    public static CloseableHttpResponse getResponse(String url){
        // 1.打开游览器,创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 2.输入网址,发起get请求创建HttpGet对象
        HttpGet httpGet = new HttpGet(url);
        // 3.按回车发起请求,返回响应,使用HttpClient对象发起请求
        try {
            return httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


