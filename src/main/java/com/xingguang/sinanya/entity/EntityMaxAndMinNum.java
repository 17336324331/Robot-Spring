package com.xingguang.sinanya.entity;

import java.util.ArrayList;

public class EntityMaxAndMinNum {
    String function;
    ArrayList<Integer> list;

    public EntityMaxAndMinNum(String function, ArrayList<Integer> list) {
        this.function = function;
        this.list = list;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public ArrayList<Integer> getList() {
        return list;
    }

    public void setList(ArrayList<Integer> list) {
        this.list = list;
    }
}
