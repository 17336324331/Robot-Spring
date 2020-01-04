package com.xingguang.mapper;

import com.xingguang.model.SystemParamModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 陈瑞扬
 * @date 2019年12月13日 23:09
 * @description 系统参数处理类
 */
@Mapper
public interface SystemParamMapper {

    // 存储 即插入一条数据
    void saveSystemCode(SystemParamModel systemCodeModel);

    String selectSystemCode(@Param("strCode") String strCode);

   // void deleteSystemCode(@Param("systemCode") String systemCode);
}
