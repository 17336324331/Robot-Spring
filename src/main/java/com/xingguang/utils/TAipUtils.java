package com.xingguang.utils;

import cn.xsshome.taip.nlp.TAipNlp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

public class TAipUtils {
    /**
     * 腾讯ai的id和key
     */
    private static final String APP_ID = "2121061546";
    private static final String APP_KEY = "hTAA8RaUJj0qipEA";

    /**
     * 基于腾讯ai的基础闲聊
     */
    private static final TAipNlp TAIP = new TAipNlp(APP_ID, APP_KEY);
    public static final String SESSION = System.currentTimeMillis()/1000+"";

    private TAipUtils() {
    }

    public static TAipNlp getTAIP() {
        return TAIP;
    }

    public static String getSession(){
        //session是秒级的时间戳
        return SESSION;
    }

    public static String getAnswer(String result){
        //转换成json，拿到ai的回答
        JSONObject json = JSON.parseObject(result);
        JSONObject data = (JSONObject) json.get("data");
        return (String) data.get("answer");
    }

}
