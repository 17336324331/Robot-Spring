package com.xingguang.mapper;

import com.xingguang.model.BotModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 22:08
 * @description
 */
@Mapper
public interface BotMapper {

    /**
     * @date 2020/1/1 22:09
     * @author 陈瑞扬
     * @description 获取处于开启状态的骰娘QQ和QQ群的key-value键值对
     * @param
     * @return
     */
    List<BotModel> getBotList();

    /**
     * @date 2020/1/1 22:17
     * @author 陈瑞扬
     * @description 骰子切换开关
     * @param botStatus
     * @param strQQ
     * @param strGroup
     * @return
     */
    void switchBot(@Param("botStatus") int botStatus, @Param("strQQ") String strQQ, @Param("strGroup") String strGroup);
}
