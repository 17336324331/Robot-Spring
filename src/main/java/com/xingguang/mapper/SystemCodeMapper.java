package com.xingguang.mapper;

import com.xingguang.model.SystemCodeModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 陈瑞扬
 * @date 2019年12月13日 23:09
 * @description 系统参数处理类
 */
@Repository
public interface SystemCodeMapper {

    // 存储 即插入一条数据
    void saveSystemCode(SystemCodeModel systemCodeModel);

    String selectSystemCode(@Param("systemCode") String strCode);

   // void deleteSystemCode(@Param("systemCode") String systemCode);
}
