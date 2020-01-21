package com.xingguang.sinanya.entity;

/**
 * @author SitaNya
 * @date 2019/9/29
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public class EntityWodDiceStr {
    String result;
    int count;
    int retrunCount;
    Integer[] descThis;

    public EntityWodDiceStr(String result, int count, int retrunCount, Integer[] descThis) {
        this.result = result;
        this.count = count;
        this.retrunCount = retrunCount;
        this.descThis = descThis;
    }

    public String getResult() {
        return result;
    }

    public int getCount() {
        return count;
    }

    public int getRetrunCount() {
        return retrunCount;
    }

    public Integer[] getDescThis() {
        return descThis;
    }
}
