package com.xingguang.mapper;

import com.xingguang.model.BugModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 陈瑞扬
 * @date 2020年01月02日 18:55
 * @description
 */
@Mapper
public interface BugMapper {

    /**
     * 查询更新的内容
     * @return
     */
    String getGengXin();

    /**
     *  Bug记录
     * @return
     */
    String recordBug(BugModel bugModel);
}
