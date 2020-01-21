package com.xingguang.sinanya.tools.makedata;

import com.xingguang.sinanya.entity.EntityNickAndRandomAndSkill;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.ManyRollsTimesTooMoreException;
import com.xingguang.sinanya.exceptions.RollCantInZeroException;
import com.xingguang.sinanya.tools.checkdata.CheckResultLevel;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import static com.xingguang.sinanya.tools.makedata.GetNickAndRandomAndSkill.getNickAndRandomAndSkill;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: ral的多线程判断成功等级类
 */
public class MakeRal {
    private static final Logger log = LoggerFactory.getLogger(MakeRal.class.getName());

    private EntityTypeMessages entityTypeMessages;
    private String rolls;

    public MakeRal(EntityTypeMessages entityTypeMessages, String rolls) {
        this.entityTypeMessages = entityTypeMessages;
        this.rolls = rolls;
    }

    public Integer call() {
        EntityNickAndRandomAndSkill entityNickAndRandomAndSkill = null;
        try {
            entityNickAndRandomAndSkill = getNickAndRandomAndSkill(entityTypeMessages, rolls);
        } catch (ManyRollsTimesTooMoreException | RollCantInZeroException e) {
            log.error(e.getMessage(), e);
        }
        assert entityNickAndRandomAndSkill != null;
        CheckResultLevel checkResultLevel = new CheckResultLevel(entityNickAndRandomAndSkill.getRandom(), entityNickAndRandomAndSkill.getSkill(), false);
        return checkResultLevel.getLevelAndRandom().getLevel();
    }


}
