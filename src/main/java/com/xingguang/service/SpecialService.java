package com.xingguang.service;

/**
 * @author 陈瑞扬
 * @date 2020年01月22日 23:30
 * @description
 */
public interface SpecialService {


    String checkSpecial();

    String dealSpecial();

    Integer fengjiu(String strQQ,String strGroup,String strContent);

    Integer selectFengjiu(String strQQ);
}
