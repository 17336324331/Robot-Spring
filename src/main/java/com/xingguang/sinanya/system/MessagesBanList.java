package com.xingguang.sinanya.system;

import com.xingguang.sinanya.entity.EntityGroupSwitch;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author SitaNya
 * 日期: 2019-08-07
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class MessagesBanList {
    public static ArrayList<Long> qqWhiteList = new ArrayList<>();
    public static ArrayList<Long> groupWhiteList = new ArrayList<>();
    public static HashMap<String, String> qqBanList = new HashMap<>();
    public static HashMap<String, String> groupBanList = new HashMap<>();
    public static ArrayList<String> allBotList = new ArrayList<>();
    public static HashMap<String, ArrayList<Long>> frequentnessForGroupList = new HashMap<>();
    public static HashMap<Long, EntityGroupSwitch> groupSwitchHashMap = new HashMap<>();
}
