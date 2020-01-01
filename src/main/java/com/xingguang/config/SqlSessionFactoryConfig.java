package com.xingguang.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 16:35
 * @description
 */
public class SqlSessionFactoryConfig {

    @Autowired
    DataSource dataSource;

//    @Bean
//    public SqlSessionFactory getSqlSessionFactory() throws Exception{
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        return sqlSessionFactoryBean.getObject();
//    }
}
