package com.xingguang.service.Impl;

import com.xingguang.mapper.SpecialMapper;
import com.xingguang.service.SpecialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author 陈瑞扬
 * @date 2020年01月22日 23:31
 * @description
 */
@Service
public class SpecialServiceImpl implements SpecialService {

    @Autowired
    private SpecialMapper specialMapper;

    @Override
    public String checkSpecial() {
        return null;
    }

    @Override
    public String dealSpecial() {

        return null;
    }

    @Override
    public Integer fengjiu(String strQQ, String strGroup,String strContent) {
        String now = LocalDate.now().toString();
        specialMapper.fengjiu(strQQ,strGroup,strContent,now);
        return null;
    }

    @Override
    public Integer selectFengjiu(String strQQ) {
        String now = LocalDate.now().toString();
        Integer num = specialMapper.selectFengjiu(strQQ, now);
        return num;
    }

    /**
     * @param strQQ
     * @param strGroup
     * @return
     * @date 2020/1/23 15:30
     * @author 陈瑞扬
     * @description 禁言记录保存
     */
    @Override
    public Integer insertGroupBan(String strQQ, String strGroup) {
        String now = LocalDate.now().toString();
        specialMapper.insertGroupBan(strQQ,strGroup,now);
        return null;
    }

    /**
     * @param strQQ
     * @return
     * @date 2020/1/23 15:31
     * @author 陈瑞扬
     * @description 查询禁言记录
     */
    @Override
    public Integer selectGroupBan(String strQQ) {

        return specialMapper.selectGroupBan(strQQ);
    }

}
