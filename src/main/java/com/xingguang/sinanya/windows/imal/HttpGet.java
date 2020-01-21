package com.xingguang.sinanya.windows.imal;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * @author SitaNya
 * 日期: 2019-08-21
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class HttpGet {
    private static final Logger log = LoggerFactory.getLogger(HttpGet.class.getName());


    public static String sendGet(String url) {
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result.toString();
    }

    public static boolean checkHttpFileExist(String url) {
        try {
            URL pathUrl = new URL(url);
            HttpURLConnection urlcon = (HttpURLConnection) pathUrl.openConnection();
            return urlcon.getResponseCode() < 400;
        } catch (Exception e) {
            return false;
        }
    }


}
