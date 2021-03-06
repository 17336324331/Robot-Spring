package com.xingguang.mapper;

import com.xingguang.model.RuleModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 陈瑞扬
 * @date 2019年12月08日 11:13
 * @description
 */
@Mapper
public interface RuleMapper {

    RuleModel selectAllByName(String strMsg);

    RuleModel selectContentByName(String strMsg);

}
