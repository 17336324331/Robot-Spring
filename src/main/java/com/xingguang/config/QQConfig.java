package com.xingguang.config;

import com.xingguang.QQRunApplication;
import com.forte.qqrobot.component.forhttpapi.HttpApplication;
import com.forte.qqrobot.depend.DependGetter;
import com.forte.qqrobot.sender.MsgSender;
import com.xingguang.model.BotModel;
import com.xingguang.service.BotService;
import com.xingguang.service.SystemParamService;
import com.xingguang.utils.SystemParam;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 14:50
 * @description  SpringBoot和QQebot整合
 */
@Configuration
public class QQConfig {

    /**
     * 通过注入的方式获取Spring的依赖工厂
     */
    @Autowired
    private BeanFactory factory;

    @Value("${QQConfig.ip}")
    private String ip;

    @Value("${QQConfig.javaPort}")
    private int javaPort;

    @Value("${QQConfig.serverPath}")
    private String serverPath;

    @Value("${QQConfig.serverPort}")
    private int serverPort;

    /**
     * 注入系统参数Service
     */
    @Autowired
    private SystemParamService systemParamService;

    /**
     * 注入开关Service
     */
    @Autowired
    private BotService botService;

    private void initSystemParam(){
        //  1.master初始化
        String master = systemParamService.getSytemParam("master");
        SystemParam.master = master;

        // 2.bot初始化
        List<BotModel> botList = botService.getBotList();
        SystemParam.botList = botList;
    }


    /**
     *
     * 通过@Bean的方式启动并将HttpApi启动器注入SpringBoot容器
     * 通过此方法即可实现双向注入
     */
    @Bean
    public HttpApplication httpApplication(){
        HttpApplication httpApplication = new HttpApplication();

        //initSystemParam();

        // 通过SpringBoot的依赖工厂，构建一个提供给Http Api启动器的依赖工厂，以实现整合
        DependGetter dependGetter = new DependGetter() {
            @Override
            public <T> T get(Class<T> clazz) {
                try {
                    return factory.getBean(clazz);
                }catch (Exception e){
                    return null;
                }
            }

            @Override
            public <T> T get(String name, Class<T> type) {
                try {
                    return factory.getBean(name, type);
                }catch (Exception e){
                    return null;
                }
            }

            @Override
            public Object get(String name) {
                try {
                    return factory.getBean(name);
                }catch (Exception e){
                    return null;
                }
            }

            @Override
            public <T> T constant(String name, Class<T> type) {
                return null;
            }

            @Override
            public Object constant(String name) {
                return null;
            }
        };

        // 启动
        httpApplication.run(new QQRunApplication(dependGetter,ip,javaPort,serverPath,serverPort));
        // 将启动器注入到Spring容器
        return httpApplication;
    }

    /**
     * 通过启动器获取到MsgSender对象
     */
    @Bean
    public MsgSender msgSender(HttpApplication httpApplication){
        return httpApplication.getMsgSender();
    }



}

