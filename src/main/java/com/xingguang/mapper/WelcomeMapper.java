package com.xingguang.mapper;

import com.xingguang.model.WelcomeModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 陈瑞扬
 * @date 2020年01月02日 21:55
 * @description
 */
@Mapper
public interface WelcomeMapper {

    void setWelcome(WelcomeModel welcomeModel);
}
