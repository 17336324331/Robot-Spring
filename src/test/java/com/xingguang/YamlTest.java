package com.xingguang;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 15:53
 * @description
 * SpringBoot单元测试
 * 可以在测试期间很方便的类似编码一样进行自动注入等容器的功能
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class YamlTest {


    @Test
    public void contextLoads() {
        System.out.println("------------------------------********************--------------------------------------------");
        System.out.println("======================" );
    }
}
