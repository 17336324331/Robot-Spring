package com.xingguang.mapper;

import com.xingguang.model.STModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 陈瑞扬
 * @date 2019年12月10日 23:05
 * @description
 */
@Mapper
public interface STMapper {

    void insertOne(STModel stModel);

    List<STModel> selectItem(@Param("strQQ") String strQQ, @Param("strGroup") String strGroup);

}
