package com.xingguang.service;

/**
 * @author 陈瑞扬
 * @date 2020年01月22日 23:30
 * @description
 */
public interface SpecialService {


    String checkSpecial();

    String dealSpecial();

    /**
     * @date 2020/1/23 15:32
     * @author 陈瑞扬
     * @description 插入奉酒记录
     * @param strQQ
     * @param strGroup
     * @param strContent
     * @return
     */
    Integer fengjiu(String strQQ,String strGroup,String strContent);

    /**
     * @date 2020/1/23 15:31
     * @author 陈瑞扬
     * @description 查询奉酒次数
     * @param strQQ
     * @return
     */
    Integer selectFengjiu(String strQQ);

    /**
     * @date 2020/1/23 15:30
     * @author 陈瑞扬
     * @description 禁言记录保存
     * @param strQQ
     * @param strGroup
     * @return
     */
    Integer insertGroupBan(String strQQ,String strGroup);

    /**
     * @date 2020/1/23 15:31
     * @author 陈瑞扬
     * @description 查询禁言记录
     * @param strQQ
     * @return
     */
    Integer selectGroupBan(String strQQ);
}
