package com.xingguang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * 这个是Spring的启动器, 或者说是Springboot的启动器。
 * 但是我并没有用到web、tomcat之类的东西，只是借用了一下Spring和Mybatis的整合
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@SpringBootApplication
public class SpringRunApplication {

    public static void main(String[] args) {
        //KeywordMatchTypeFactory.registerType("SINANYA_REGEX", (msg, regex) -> Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(fullWidth2halfWidth(msg.trim().toLowerCase())).find());

        //首先，启动Spring
        SpringApplication.run(SpringRunApplication.class, args);

        // 没有然后了。与robot框架进行整合的代码存在于 config包下的QQConfig类中。

        // 可以看一下controller.TestController类。这个类是SpringBoot中最基础的一个Controller类。
        // 只要没有报错，就代表启动并注入成功了。

    }

}
