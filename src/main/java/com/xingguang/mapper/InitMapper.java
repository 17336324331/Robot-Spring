package com.xingguang.mapper;

import com.xingguang.model.InitModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 陈瑞扬
 * @date 2019年12月13日 23:09
 * @description
 */
@Mapper
public interface InitMapper {

    // 存储 即插入一条数据
    void saveInit(InitModel initModel);

    List<InitModel> selectInit();

    void deleteInit(@Param("strQQ") String strQQ, @Param("strGroup") String strGroup);
}
