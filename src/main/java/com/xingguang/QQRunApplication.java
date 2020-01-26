package com.xingguang;

import com.forte.component.forcoolqhttpapi.CoolQHttpApp;
import com.forte.component.forcoolqhttpapi.CoolQHttpConfiguration;
import com.forte.qqrobot.beans.messages.result.LoginQQInfo;
import com.forte.qqrobot.depend.DependGetter;
import com.forte.qqrobot.factory.KeywordMatchTypeFactory;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.xingguang.listener.GroupMsgListener;
import com.xingguang.sinanya.db.fire.SelectFire;
import com.xingguang.sinanya.db.groupSwitch.SelectGroupSwitch;
import com.xingguang.sinanya.db.properties.ban.SelectBanProperties;
import com.xingguang.sinanya.db.properties.game.SelectGameProperties;
import com.xingguang.sinanya.db.properties.system.SelectSystemProperties;
import com.xingguang.sinanya.db.welcome.SelectWelcome;
import com.xingguang.sinanya.dice.system.Log;
import com.xingguang.sinanya.tools.getinfo.GetLocationPath;
import com.xingguang.sinanya.tools.getinfo.GetPath;
import com.xingguang.sinanya.windows.Setting;
import com.xingguang.utils.SystemParam;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.xingguang.sinanya.tools.makedata.FullWidth2halfWidth.fullWidth2halfWidth;

import java.io.File;
import java.util.Locale;
import java.util.regex.Pattern;

import static com.xingguang.sinanya.db.system.SelectBot.flushBot;
import static com.xingguang.sinanya.system.MessagesSystem.WINDOWS_MODEL;
import static com.xingguang.sinanya.system.MessagesSystem.loginInfo;
import static com.xingguang.sinanya.tools.getinfo.BanList.flushBanList;
import static com.xingguang.sinanya.tools.getinfo.DefaultMaxRolls.flushMaxRolls;
import static com.xingguang.sinanya.tools.getinfo.GetMessagesProperties.*;
import static com.xingguang.sinanya.tools.getinfo.History.flushHistory;
import static com.xingguang.sinanya.tools.getinfo.Kp.flushKp;
import static com.xingguang.sinanya.tools.getinfo.LogTag.flushLogTag;
import static com.xingguang.sinanya.tools.getinfo.RoleChoose.flushRoleChoose;
import static com.xingguang.sinanya.tools.getinfo.RoleInfo.flushRoleInfoCache;
import static com.xingguang.sinanya.tools.getinfo.Team.flushTeamEn;
import static com.xingguang.sinanya.tools.getinfo.WhiteList.flushWhiteGroupList;
import static com.xingguang.sinanya.tools.getinfo.WhiteList.flushWhiteUserList;

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

    private static final Logger log = LoggerFactory.getLogger(GroupMsgListener.class);

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
        KeywordMatchTypeFactory.registerType("SINANYA_REGEX", (msg, regex) -> Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(fullWidth2halfWidth(msg.trim().toLowerCase())).find());
        LoginQQInfo loginQQInfo = sender.GETTER.getLoginQQInfo();
        SystemParam.strCurrentQQ = loginQQInfo.getQQ();

        System.out.println(SystemParam.strCurrentQQ +"启动成功 ");

        initMessagesSystemProperties();
        //entitySystemProperties.setSystemDir(new File(new GetPath().getPath()).getParentFile().getPath());
        System.out.println(System.getProperty("os.name").toLowerCase(Locale.US));
        String log4jxml2;
        if (WINDOWS_MODEL) {
            entitySystemProperties.setSystemDir(new File(new GetPath().getPath()).getParentFile().getPath());
            log4jxml2 = entitySystemProperties.getSystemDir() + "/SinaNya/conf/log4j2.xml";
            System.setProperty("log_path", entitySystemProperties.getSystemDir() + "/SinaNya/logs/");
        } else {
            entitySystemProperties.setSystemDir(System.getProperty("user.dir"));
            String path;
            String buildClassTargetDir = "/target/classes";
            path = new GetLocationPath().getLocationPath().split(":")[0];
            if (path.contains(buildClassTargetDir)) {
                path = path.replace(buildClassTargetDir, "");
            }
            log4jxml2 = path + "/src/main/resources/log4j2.xml";
            System.setProperty("log_path", path + "/logs/");
        }

        loginInfo.setLoginId(Long.parseLong(sender.getLoginInfo().getQQ()));
        loginInfo.setLoginName(sender.getLoginInfo().getName());
        initMessagesBanProperties();
        initMessagesGameProperties();

        new SelectSystemProperties().flushProperties();
        new SelectBanProperties().flushProperties();
        new SelectGameProperties().flushProperties();
        new SelectGroupSwitch().flushGroupSwitchFromDataBase();
        new SelectWelcome().flushProperties();
        new SelectFire().flushFireSwitch();
        log.info("开始刷写数据库");
        flushTeamEn();
        log.info("读取幕间成长到缓存");
        flushMaxRolls();
        log.info("读取最大默认骰到缓存");
        flushBot();
        log.info("读取机器人开关到缓存");
        flushRoleChoose();
        log.info("读取当前已选角色到缓存");
        flushRoleInfoCache();
        log.info("读取角色信息到缓存");
        flushLogTag();
        log.info("读取日志开关到缓存");
        flushKp();
        log.info("读取kp主群设定到缓存");
        flushHistory();
        log.info("读取骰点历史信息到缓存");
        if (entityBanProperties.isCloudBan()) {
            flushBanList();
            log.info("读取云黑列表到缓存");
        }
        if (entityBanProperties.isWhiteGroup()) {
            flushWhiteGroupList();
        }

        if (entityBanProperties.isWhiteUser()) {
            flushWhiteUserList();
        }
//        if (entityBanProperties.isPrometheus()) {
//            new com.xingguang.sinanya.monitor.Prometheus().start();
//            log.info("开启普罗米修斯监控");
//        }
        //new Setting();
    }
}
