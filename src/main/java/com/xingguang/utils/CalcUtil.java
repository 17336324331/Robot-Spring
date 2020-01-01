package com.xingguang.utils;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈瑞扬
 * @date 2019年12月13日 17:15
 * @description 计算工具类
 */
public class CalcUtil {

    // 计算工具类的主入口 1d3 + 2d6 + 3d5*3- 2d6
    public static String dealData(String  strCalc){
        // 加数之和
        int a = 0;
        String strResult = "";
        // 减数之和
        int b = 0;

        // 拆分算式 得到加法因子
        String[] addArr = strCalc.split("\\+");

        // 循环加数
        for (int i = 0; i < addArr.length; i++) {
            String addFactor  =addArr[i];
            //  如果加数之中包含减号
            if (addFactor.contains("-")) {
                String[] subArr = addFactor.split("\\-");
                Map<String, String> baseResultMap = baseCalc(subArr[0]);
                a = a+Integer.valueOf(baseResultMap.get("intResult"));
                if( i == 0 ){
                    strResult = strResult + baseResultMap.get("strResult");
                }
                // 如果不是第一个加数,字符串拼上加号再拼数字
                else{
                    strResult = strResult +"+"+ baseResultMap.get("strResult");
                }
                for (int j = 1; j < subArr.length; j++) {
                    baseResultMap = baseCalc(subArr[j]);
                    b = b + Integer.valueOf(baseResultMap.get("intResult"));
                    strResult = strResult +"-"+ baseResultMap.get("strResult");
                }

            }
            // 如果加数之中不包含减号
            else{
                Map<String, String> baseResultMap = baseCalc(addFactor);
                // 如果是第一个加数,字符串直接拼数字
                if( i == 0 ){
                    strResult = strResult + baseResultMap.get("strResult");
                }
                // 如果不是第一个加数,字符串拼上加号再拼数字
                else{
                    strResult = strResult +"+"+ baseResultMap.get("strResult");
                }
                a = a +Integer.valueOf(baseResultMap.get("intResult"));
            }

        }

        return  strCalc.toUpperCase()+"="+strResult+"="+(a-b);

    }


    // 这个计算的是  1d10*10 这种基础算式   strResult intResult
    public static Map<String,String> baseCalc(String baseFactor){
        HashMap<String, String> map = new HashMap<>();
        String strResult = "";
        Integer intResult = 0;
        // 日常 trim 一下
        baseFactor = baseFactor.trim();
        // 判断baseFactor不为空
        if (StringUtils.isNotBlank(baseFactor)){

            // 如果包含 d ,即 投掷
            if (baseFactor.contains("d")){

                // 如果是这种格式  d100 就转化 为 1d100
                if (baseFactor.substring(0,1).equals("d")){
                    baseFactor = "1"+ baseFactor;
                }
                // 如果包含乘号 即 3d6 * 5
                if (baseFactor.contains("*")){
                    // 以乘号拆分  得到 [3d6,5]
                    String[] split = baseFactor.split("\\*");
                    for (int i = 0; i < split.length; i++) {
                        String factor = split[i].trim();
                        if (factor.contains("d")){
                            String[] xyArr = factor.split("d");
                            Integer x = Integer.valueOf(xyArr[0].trim());
                            Integer y = Integer.valueOf(xyArr[1].trim());
                            for (int ii = 0; ii < x; ii++) {
                                int random = getRandom(1, y);
                                if (ii == 0){
                                    strResult = strResult + random;
                                }else {
                                    strResult = strResult + "+"+random;
                                }
                                intResult+=random;
                            }
                        }
                        // 常数
                        else{
                            // 若果是3d6 * 5 这种格式
                            if (StringUtils.isNotBlank(strResult)&&intResult!=0){
                                strResult = "("+strResult+")"+"*"+factor;
                                intResult = intResult * Integer.valueOf(factor);
                            }
                            // 如果是5*3d6 ,卧槽,老子不想写,就当没有这一节
                            else{

                            }

                        }

                    }

                }
                // 如果不包含乘号 即 2d6 这种格式
                else {
                    String[] xyArr = baseFactor.split("d");
                    Integer x = Integer.valueOf(xyArr[0].trim());
                    Integer y = Integer.valueOf(xyArr[1].trim());
                    for (int i = 0; i < x; i++) {
                        int random = getRandom(1, y);
                        if (i == 0){
                            strResult = strResult + random;
                        }else {
                            strResult = strResult + "+"+random;
                        }
                        intResult+=random;
                    }
                }
            }
            // 如果不包含 d ,即 数字
            else{
                // 判断是否包含乘号

                // 如果包含乘号   10*10*10
                if (baseFactor.contains("*")){
                    intResult = 1;
                    String[] split = baseFactor.split("\\*");
                    for (int i = 0; i < split.length; i++) {
                        if (i>0){
                            strResult = strResult + "*"+split[i];
                        }else{
                            strResult = strResult+split[i];
                        }
                        intResult = intResult * Integer.valueOf(split[i].trim());
                    }
                }
                // 如果不包含乘号
                else {
                    strResult = baseFactor;
                    intResult = Integer.valueOf(baseFactor);
                }

            }
            map.put("strResult",strResult);
            map.put("intResult",intResult+"");




        }
        // 如果传进来了空
        else{
            // 逻辑上不会走这一段 ,只是为了防止异常才写
            map.put("strResult","");
            map.put("intResult","0");

        }

        return map;

    }


    // 获取指定范围的随机数
    public static int getRandom(int intBegin,int intEnd){
        Double douJrrp = Math.random() * (intEnd-intBegin-1)+1;
        int intJrrp = douJrrp.intValue();
        intJrrp+=intBegin;
        return intJrrp;

    }







}
