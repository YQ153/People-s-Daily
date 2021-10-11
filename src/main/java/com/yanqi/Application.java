package com.yanqi;

import com.yanqi.myEnum.DateFormatEnum;
import com.yanqi.utils.DateUtils;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author YQQ
 * @program: People's Daily
 * @description: Download People's Daily automatically
 * @date 2021-09-02 22:20:53
 */
public class Application {
    /**
     * 程序入口
     */
    public static void main(String[] args) throws IOException {


        /**
         * 判断是否请求哪日的报纸
         */


//        测试运行时间
        long startTime=System.currentTimeMillis();
        DateUtils dateUtils = new DateUtils(DateFormatEnum.Format_ONE);
        String dataByFormat = dateUtils.getDateWithString();
        dateUtils.setDateFormatEnum(DateFormatEnum.Format_TWE);
        String dataOnlyNum = dateUtils.getDateWithString();

        errorHandler(dataByFormat,dataOnlyNum);

        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
    }

    private static int time = 0;
    private static void errorHandler(String dataByFormat,String dataOnlyNum){
        try {
            getPD(dataByFormat, dataOnlyNum);
        } catch (IOException e) {
//            错误出现三次后报错
            if (time < 3){
                time++;
                System.out.println("错误：" + time);
                Application.errorHandler(dataByFormat,dataOnlyNum);
            }
            e.printStackTrace();
        }
    }

    public static void getPD(String dataByFormat, String dataOnlyNum) throws IOException {
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
            long getDataStartTime=System.currentTimeMillis();
            CloseableHttpResponse response = getResponse(nowUrl);
            long getDataEndTime=System.currentTimeMillis();
            System.out.println("获取数据用时： "+(getDataEndTime-getDataEndTime)+"ms");
            // 4.解析响应,获取数据
            // 判断状态码是否是200
            if (response.getStatusLine().getStatusCode() == 200) {
                //获取网页源代码的用法
                InputStream stream = response.getEntity().getContent();

                /**
                 * IOThread ioThread = new IOThread(file, stream);
                 * ioThread.run();
                 */

                Thread thread = new Thread(new IOThread(file, stream));
                thread.start();
            }else {
                System.out.println("没有请求回数据哟！");
            }
        }
    }

    private static int timeOne = 0;
    public static String[] getNameOfPage(String url){
        CloseableHttpResponse response = getResponse(url);
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = null;
            try{
                httpEntity = response.getEntity();
            }catch (Exception e){
                if (timeOne < 3){
                    getNameOfPage(url);
                    timeOne++;
                    System.out.println("执行了：" + (timeOne + 1) + "次");
                }else {
                    e.printStackTrace();
                }
            }
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
