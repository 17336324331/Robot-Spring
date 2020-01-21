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
public class EntityWodParam {
    int NTime = 5;
    int n = 8;
    int a = 10;
    int b = 0;
    int m = a;

    public EntityWodParam(int n, int n1, int a, int b, int m) {
        this.NTime = n;
        this.n = n1;
        this.a = a;
        this.b = b;
        this.m = m;
    }

    public EntityWodParam() {
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getNTime() {
        return this.NTime;
    }

    public void setNTime(int NTime) {
        this.NTime = NTime;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
