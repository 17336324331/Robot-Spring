package com.xingguang.mapper;

import com.xingguang.model.MsgModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 陈瑞扬
 * @date 2019年12月08日 10:32
 * @description
 */
@Mapper
public interface MsgMapper {

    void saveMsg(MsgModel msgModel);

    List<MsgModel> selectRepeat(@Param("strGroup") String strGroup, @Param("num") Integer num);
}
