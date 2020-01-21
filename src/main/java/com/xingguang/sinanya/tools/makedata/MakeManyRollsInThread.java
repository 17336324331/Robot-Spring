package com.xingguang.sinanya.tools.makedata;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: ral、rcl使用的多次骰点多线程类
 */
class MakeManyRollsInThread {

    private int maxValue;

    MakeManyRollsInThread(int maxValue) {
        this.maxValue = maxValue;
    }

    Integer call() {
        return RandomInt.random(1, maxValue);
    }
}
