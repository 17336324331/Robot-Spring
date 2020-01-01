package com.xingguang.utils;

import com.alibaba.fastjson.JSONObject;
import com.forte.qqrobot.anno.Ignore;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * @author 陈瑞扬
 * @date 2019年12月01日 14:23
 * @description
 */
public class CommandUtil {


    /**
     * @date 2019/12/1 12:53
     * @author 陈瑞扬
     * @description 校验发送的信息是否为命令
     * @param commandStr
     * @return boolean
     */
    public static boolean checkCommand(String commandStr) {
        // 非空判断
        if(StringUtils.isNotBlank(commandStr)&&commandStr.trim().length()>1){

            char c = commandStr.charAt(0);
            if (c == '.' || c == '/' || c == '。'||c == '!'|| c=='！') {
                return true;
            } else {
                return false;
            }

        }else{
            return false;
        }


    }

    /**
     * @date 2019/12/1 12:53
     * @author 陈瑞扬
     * @description 管理员校验
     * @param strQQ
     * @return boolean
     */
    @Ignore
    public static String checkAdmin(String strQQ){
        PropertiesUtil props = null;
        try {
            props = new PropertiesUtil("param.properties");
        }catch (Exception e){
            System.out.println("checkAdmin抛出异常" +e);
        }
        String strMap = props.getProperty("admin");
        JSONObject jsonObject = JSONObject.parseObject(strMap);
        Map<String,Object> adminMap = (Map<String,Object>)jsonObject;
        if (adminMap.containsKey(strQQ)){
            return adminMap.get(strQQ).toString();
        }else{
            return null;
        }

    }


}
