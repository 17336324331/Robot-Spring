package com.xingguang.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * 一个十分简单的Mapper
 * 这个mapper中出现的表：qq_message中只有一个字段：msg
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
//@Mapper
//@Repository
public interface MessageMapper {

//    /**
//     * 插入一条数据
//     */
//    @Insert("INSERT INTO `qq_message` (`msg`) VALUES (#{msg});")
//    void insertNewMsg(@Param("msg")String msg);
//
//
//    /**
//     * 查询出所有的数据
//     */
//    @Select("SELECT msg FROM `qq_message`")
//    List<String> selcetMsgs();
//
//
//    @Select("SELECT `value` FROM `for_test` WHERE id = #{id} ")
//    String test(@Param("id") String id);
//
//    @Insert("INSERT INTO `for_test` (`value`) VALUES (#{value}) ")
//    void test_in(@Param("value")String value);

}
