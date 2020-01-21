package com.xingguang.sinanya.entity;

import java.util.ArrayList;

/**
 * @author SitaNya
 * @date 2019/11/11
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public class EntityCoc7CardExcel {
    String info;
    ArrayList<Integer> excel;

    public EntityCoc7CardExcel(String info, ArrayList<Integer> excel) {
        this.info = info;
        this.excel = excel;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ArrayList<Integer> getExcel() {
        return excel;
    }

    public void setExcel(ArrayList<Integer> excel) {
        this.excel = excel;
    }
}
