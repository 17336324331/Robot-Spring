package com.xingguang.sinanya.dice.get.imal;

import com.xingguang.sinanya.entity.EntityCoc6CardInfo;
import com.xingguang.sinanya.entity.EntityCoc7CardExcel;
import com.xingguang.sinanya.entity.EntityCoc7CardInfo;
import com.xingguang.sinanya.tools.checkdata.CheckIsNumbers;

import java.util.ArrayList;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 接口说明: 生成COC、DND人物卡接口
 */
public interface MakeCard {


    /**
     * 获取生成卡片个数
     *
     * @param msg 输入值
     * @return 返回次数
     */
    default int getTime(String msg) {
        int times = 1;
        if (CheckIsNumbers.isNumeric(msg)) {
            times = Integer.parseInt(msg);
        }
        return times;
    }

    /**
     * 获取Coc6简易人物卡字符串
     *
     * @return 返回字符串结果
     */
    default String getCoc6CardInfo() {
        EntityCoc6CardInfo coc6CardInfo = new EntityCoc6CardInfo();

        return "力量:" +
                coc6CardInfo.getStr() +
                " " +
                "体质:" +
                coc6CardInfo.getCon() +
                " " +
                "体型:" +
                coc6CardInfo.getSiz() +
                " " +
                "敏捷:" +
                coc6CardInfo.getDex() +
                " " +
                "魅力:" +
                coc6CardInfo.getApp() +
                " " +
                "智力:" +
                coc6CardInfo.getInt() +
                " " +
                "意志:" +
                coc6CardInfo.getPow() +
                " " +
                "教育:" +
                coc6CardInfo.getEdu() +
                " " +
                "血量:" +
                coc6CardInfo.getHp() +
                " " +
                "理智:" +
                coc6CardInfo.getSan() +
                " " +
                "灵感:" +
                coc6CardInfo.getIdea() +
                " " +
                "幸运:" +
                coc6CardInfo.getLuck() +
                " " +
                "知识:" +
                coc6CardInfo.getKnow() +
                " " +
                "DB:" +
                coc6CardInfo.getDb() +
                "\n总值:" +
                coc6CardInfo.getNotLuck() +
                " " +
                "占总点数的:" +
                String.valueOf((coc6CardInfo.getNotLuck() * 1.0 / 147) * 100).substring(0, 5) + "%";
    }

    /**
     * 获取Coc7简易人物卡字符串
     *
     * @return 返回字符串结果
     */
    default EntityCoc7CardExcel getCoc7CardInfo() {
        EntityCoc7CardInfo coc7CardInfo = new EntityCoc7CardInfo();

        String luckString = String.valueOf((coc7CardInfo.getHasLuck() * 1.0 / 810) * 100);
        if (luckString.length() >= 5) {
            luckString = luckString.substring(0, 5) + "%";
        }

        ArrayList<Integer> cocList = new ArrayList<>();

        cocList.add(coc7CardInfo.getStr());
        cocList.add(coc7CardInfo.getDex());
        cocList.add(coc7CardInfo.getPow());
        cocList.add(coc7CardInfo.getCon());
        cocList.add(coc7CardInfo.getApp());
        cocList.add(coc7CardInfo.getEdu());
        cocList.add(coc7CardInfo.getSiz());
        cocList.add(coc7CardInfo.getInt());
        cocList.add(coc7CardInfo.getLuck());


//        力量 敏捷 意志
//        体质 魅力 教育
//        体型 智力 幸运

        return new EntityCoc7CardExcel("力量:" +
                coc7CardInfo.getStr() +
                " " +
                "敏捷:" +
                coc7CardInfo.getDex() +
                " " +
                "意志:" +
                coc7CardInfo.getPow() +
                "\n" +
                "体质:" +
                coc7CardInfo.getCon() +
                " " +
                "魅力:" +
                coc7CardInfo.getApp() +
                " " +
                "教育:" +
                coc7CardInfo.getEdu() +
                "\n" +
                "体型:" +
                coc7CardInfo.getSiz() +
                " " +
                "智力:" +
                coc7CardInfo.getInt() +
                " " +
                "幸运:" +
                coc7CardInfo.getLuck() +
                "\n" +
                "血量:" +
                coc7CardInfo.getHp() +
                " " +
                "DB:" +
                coc7CardInfo.getDb() +
                "\n" +
                "总值:" +
                coc7CardInfo.getNotLuck() +
                "/" +
                coc7CardInfo.getHasLuck() +
                " " +
                "占总点数的:" + luckString, cocList);

    }

}
