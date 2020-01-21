package com.xingguang.sinanya.listener;

import com.forte.qqrobot.anno.timetask.CronTask;
import com.xingguang.sinanya.exceptions.CantFindQqIdException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import static com.xingguang.sinanya.tools.getinfo.BanList.flushBanList;
import static com.xingguang.sinanya.tools.getinfo.History.flushHistory;
import static com.xingguang.sinanya.tools.getinfo.History.setHistory;
import static com.xingguang.sinanya.tools.getinfo.RoleInfo.flushRoleInfoCache;

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
@CronTask("0 0/15 * * * ? *")
public class FlushBig implements Job {
    private static final Logger log = LoggerFactory.getLogger(FlushBig.class.getName());

    @Override
    public void execute(JobExecutionContext context) {
        try {
            setHistory();
        } catch (CantFindQqIdException e) {
            log.error(e.getMessage(), e);
        }
        flushRoleInfoCache();
//        从数据库中读取角色信息到缓存
        flushHistory();
//        从数据库中读取骰点历史信息到缓存
        flushBanList();
    }
}