package com.xingguang.sinanya.db.tools;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 数据库连接池定义类，没有特殊必要不需要改动
 */
class DbPool {
    private static final Logger log = LoggerFactory.getLogger(DbPool.class.getName());

    private static DbPool instance;

    static {
        instance = new DbPool();
    }

    private ComboPooledDataSource dataSource;

    /**
     * 初始化信息类，这里声明了驱动、用户名、密码等各种信息，其中密码是从配置文件中取得的
     */
    private DbPool() {
        try {
            dataSource = new ComboPooledDataSource();

            dataSource.setDriverClass("com.mysql.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://bj-cdb-0mud5lvw.sql.tencentcdb.com:61922/test?useUnicode=true&characterEncoding=gbk&zeroDateTimeBehavior=convertToNull&useSSL=false&autoReconnect=true");
            dataSource.setUser("root");
            dataSource.setPassword("Lfdy1973L");
            dataSource.setInitialPoolSize(5); // 初始化时获取连接数，取值应在minPoolSize与maxPoolSize之间。Default: 3
            dataSource.setMinPoolSize(1);  //  连接池中保留的最小连接数
            dataSource.setMaxPoolSize(10); // 连接池中保留的最大连接数。Default: 15

            dataSource.setMaxStatements(50);//最长等待时间

            dataSource.setMaxIdleTime(60);//最大空闲时间,60秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0
            dataSource.setAcquireIncrement(3);// 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3
            dataSource.setAcquireRetryAttempts(30);// 定义在从数据库获取新连接失败后重复尝试的次数。Default: 30
        } catch (PropertyVetoException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * @return 返回连接池
     */
    static DbPool getInstance() {
        return instance;
    }

    /**
     * @return 返回连接
     */
    Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            log.debug("数据库获取连接");
        } catch (SQLException e) {
            log.debug("数据库数据源" + dataSource.toString());
            log.error(e.getMessage(), e);
        }
        try {
            Exception e = new Exception("线程池调用流程为");
            log.debug("当前线程池中链接数 " + dataSource.getNumConnections());
            log.debug(e.getMessage(), e);
        } catch (SQLException e) {
            log.debug(e.getMessage(), e);
        }
        return conn;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
