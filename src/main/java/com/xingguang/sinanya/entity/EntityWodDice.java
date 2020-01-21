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
public class EntityWodDice {
    int count;
    String diceList;
    String simple;

    public EntityWodDice(int count, String diceList, String simple) {
        this.count = count;
        this.diceList = diceList;
        this.simple = simple;
    }

    public int getCount() {
        return count;
    }

    public String getDiceList() {
        return diceList;
    }

    public String getSimple() {
        return simple;
    }
}
