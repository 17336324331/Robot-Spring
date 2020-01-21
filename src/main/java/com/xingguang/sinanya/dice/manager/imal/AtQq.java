package com.xingguang.sinanya.dice.manager.imal;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.tools.getinfo.Team;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 接口说明: at接口，实现此接口则可以通过getAtQqList方法获取到所有被at到的人的QQ号列表
 */
public interface AtQq {

    /**
     * 从一个传入信息中匹配得到所有被at的人的QQ号
     *
     * @param msg 传入信息
     * @return 传入信息中at人员的列表
     */
    default ArrayList<String> getAtQqList(EntityTypeMessages entityTypeMessages, String msg) {
        if (msg.contains("all")) {
            return (ArrayList<String>) Team.queryTeam(entityTypeMessages.getFromGroupString());
        }
        return getAtQqList(msg);
    }

    /**
     * 从一个传入信息中匹配得到所有被at的人的QQ号
     *
     * @param msg 传入信息
     * @return 传入信息中at人员的列表
     */
    default ArrayList<String> getAtQqList(String msg) {
        String regex = "\\[cq:at,qq=([0-9]+)]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(msg);

        ArrayList<String> qqList = new ArrayList<>();
        while (matcher.find()) {
            qqList.add(matcher.group(1));
        }
        return qqList;
    }
}
