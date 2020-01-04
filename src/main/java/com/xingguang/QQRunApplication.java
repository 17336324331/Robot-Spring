package com.xingguang;

import com.forte.component.forcoolqhttpapi.CoolQHttpApp;
import com.forte.component.forcoolqhttpapi.CoolQHttpConfiguration;
import com.forte.qqrobot.beans.messages.result.LoginQQInfo;
import com.forte.qqrobot.depend.DependGetter;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.xingguang.utils.SystemParam;

/**
 * 这个是simple-robot框架的启动器类
 * 使用HTTP API组件进行示例, 实现{@link CoolQHttpApp}接口并进行相应的配置
 * 由于需要整合Spring的依赖管理系统，所以需要Spring的依赖工厂来自定义依赖获取方式
 * 所以自定义依赖获取由构造方法进行提供
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class QQRunApplication implements CoolQHttpApp {

    /**
     * 自定义的依赖获取器
     */
    private DependGetter dependGetter;

    private String strIp;

    private int intJavaPort;

    private String strServerPath;

    private int intServerPort;

    /**
     * 构造，需要提供一个自定义的依赖获取器
     */
    public QQRunApplication(DependGetter dependGetter,String strIp,int intJavaPort,String strServerPath,int intServerPort) {
        this.dependGetter = dependGetter;
        this.strIp = strIp;
        this.intJavaPort= intJavaPort;
        this.strServerPath = strServerPath;
        this.intServerPort = intServerPort;
    }

    @Override
    public void before(CoolQHttpConfiguration configuration) {
        //启动之前，进行配置
        configuration.setIp(strIp)
                .setJavaPort(intJavaPort)
                .setServerPath(strServerPath)
                .setServerPort(intServerPort)
                //配置自定义的依赖获取器
                .setDependGetter(dependGetter);

        /*
            关于配置的详细信息请查看普通的demo项目或者文档
         */

    }

    @Override
    public void after(CQCodeUtil cqCodeUtil, MsgSender sender) {

        LoginQQInfo loginQQInfo = sender.GETTER.getLoginQQInfo();
        SystemParam.strCurrentQQ = loginQQInfo.getQQ();

        System.out.println(SystemParam.strCurrentQQ +"启动成功 ");
    }
}
