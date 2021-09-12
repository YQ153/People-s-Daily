package com.yanqi;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

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

        try {
            getPD(format, dataOnlyNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getPD(String dataByFormat,String dataOnlyNum) throws IOException {
        String urlHead = "http://paper.people.com.cn/rmrb/images/";
        String nowUrl;
        String[] fileName = {
//            "01版要闻","02版要闻","03版要闻","04版要闻","05版要闻",
//            "06版要闻","07版评论","08版广告","09版理论","10版经济",
//            "11版政治","12版文化","13版生态","14版体育","15版综合",
//            "16版广告","17版国际","18版民主政治","19版法治","20版副刊"
                "01版","02版","03版","04版","05版",
                "06版","07版","08版","09版","10版",
                "11版","12版","13版","14版","15版",
                "16版","17版","18版","19版","20版"
        };
        File file1 = new File("C:\\Users\\YQQ\\Desktop\\" + dataOnlyNum);
        if (!file1.isDirectory()) {
            file1.mkdirs();
        }
        for (int i = 1; i <= 20; i++){
            nowUrl = urlHead + dataByFormat + "/" + (i >= 10 ? i : ("0" + i)) + "/" + "rmrb" + dataOnlyNum + (i >= 10 ? i : ("0" + i)) + ".pdf";
            System.out.println(nowUrl);
            System.out.println("C:\\Users\\YQQ\\Desktop\\" + dataOnlyNum + "\\" + fileName[i-1] + ".pdf");
            // 1.打开游览器,创建HttpClient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 2.输入网址,发起get请求创建HttpGet对象
            HttpGet httpGet = new HttpGet(nowUrl);
            // 3.按回车发起请求,返回响应,使用HttpClient对象发起请求
            CloseableHttpResponse response = httpClient.execute(httpGet);
            // 4.解析响应,获取数据
            // 判断状态码是否是200
            if (response.getStatusLine().getStatusCode() == 200) {
                //获取网页源代码的用法
                HttpEntity entity = response.getEntity();
                InputStream stream = entity.getContent();
                File file = new File("C:\\Users\\YQQ\\Desktop\\" + dataOnlyNum + "\\" + fileName[i-1] + ".pdf");
                FileOutputStream fileOutputStream = new FileOutputStream(file,true);
                int len = 0;
                while ((len = stream.read()) != -1) {
                    fileOutputStream.write(len);
                }
                stream.close();
                fileOutputStream.close();
                httpClient.close();
            }else {
                System.out.println("没有请求回数据哟！");
            }
        }
    }
}


