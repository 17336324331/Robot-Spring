package com.xingguang.mapper;

import com.xingguang.model.NameModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 陈瑞扬
 * @date 2019年12月13日 23:27
 * @description
 */
@Repository
public interface NameMapper {

    void saveName(NameModel nameModel);

    void updateName(NameModel nameModel);

    String selectNameByQQGroup(@Param("strQQ") String strQQ, @Param("strGroup") String strGroup);

    NameModel selectByQQGroup();
}
