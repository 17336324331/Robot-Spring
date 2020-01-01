package com.xingguang.utils;

import com.forte.qqrobot.anno.depend.Depend;
import org.apache.ibatis.session.SqlSession;

/**
 * @author 陈瑞扬
 * @date 2019年12月22日 22:01
 * @description 权限工具类
 */
public class PermissionUtils {

    @Depend
    private static SqlSession sqlSession;

//    // 1.检查是否是可用群
//    public static boolean checkEnablerGroup(String strGroup){
//        if (StringUtils.isNotBlank(strGroup)){
//
//
//        }else{
//            return false;
//        }
//
//    }

    // 2.检查是否是系统管理员

    // 3.检查是否是群管理员

}
