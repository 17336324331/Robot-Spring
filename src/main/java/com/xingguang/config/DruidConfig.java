package com.xingguang.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 14:50
 * @description  配置druid连接池
 */
@Configuration
public class DruidConfig {


    @Bean(name = "datasource",initMethod = "getConnection")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();

        return  druidDataSource;

    }

    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String,String> initParam = new HashMap<>();
        initParam.put("loginUsername","chenruiyang");
        initParam.put("loginPassword","123456");
        initParam.put("allow","");//默认哪些地址可以访问
        initParam.put("deny","");//配置哪些地址不可以访问

        bean.setInitParameters(initParam);
        return bean;
    }

    //配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String,Object> initParam = new HashMap<>();
        initParam.put("exclusions","*.js,*.css,/druid/*");

        bean.setInitParameters(initParam);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }



}
