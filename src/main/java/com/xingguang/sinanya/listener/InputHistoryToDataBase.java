package com.xingguang.sinanya.listener;

import com.forte.qqrobot.anno.timetask.CronTask;
import com.xingguang.sinanya.db.fire.SelectFire;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import static com.xingguang.sinanya.db.system.SelectBot.flushBot;
import static com.xingguang.sinanya.tools.getinfo.DefaultMaxRolls.flushMaxRolls;
import static com.xingguang.sinanya.tools.getinfo.Kp.flushKp;
import static com.xingguang.sinanya.tools.getinfo.LogTag.flushLogTag;
import static com.xingguang.sinanya.tools.getinfo.RoleChoose.flushRoleChoose;
import static com.xingguang.sinanya.tools.getinfo.Team.flushTeamEn;
import static com.xingguang.sinanya.tools.getinfo.Team.saveTeamEn;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 定时任务类，会根据设定的时间每隔多久自动执行一次
 * <p>
 * 这里的“0 * * * * ? *”表示每分钟执行一次，具体怎么写请去查crontab的使用
 * 前5位应该是:分 时 日 月 周
 */
@CronTask("0 0/5 * * * ? *")
public class InputHistoryToDataBase implements Job {
    private static final Logger log = LoggerFactory.getLogger(InputHistoryToDataBase.class.getName());

    @Override
    public void execute(JobExecutionContext context) {
        saveTeamEn();
        flushTeamEn();
//        从数据库中读取幕间成长到缓存
        flushMaxRolls();
//        从数据库中读取最大默认骰到缓存
        flushBot();
//        从数据库中读取机器人开关到缓存
        flushRoleChoose();
//        从数据库中读取当前已选角色到缓存
        flushLogTag();
//        从数据库中读取日志开关到缓存
        flushKp();
//        刷写黑名单
        new SelectFire().flushFireSwitch();
    }
}