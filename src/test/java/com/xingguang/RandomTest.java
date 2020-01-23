package com.xingguang;

import java.util.Random;

/**
 * @author 陈瑞扬
 * @date 2020年01月22日 20:54
 * @description
 */
public class RandomTest {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(new Random().nextInt(2)+1);
        }

    }
}
