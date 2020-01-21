package com.xingguang.service.Impl;

import com.xingguang.mapper.STMapper;
import com.xingguang.model.STModel;
import com.xingguang.service.StService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 陈瑞扬
 * @date 2020年01月05日 12:44
 * @description 人物属性service实现类
 */
@Service
public class StServiceImpl implements StService {

    @Autowired
    private STMapper stMapper;

    /**
     * @param msg
     * @return
     * @date 2020/1/5 12:45
     * @author 陈瑞扬
     * @description 属性设置
     */
    @Override
    public void stsave(String msg) {
        //stMapper.insertOne();
    }

    /**
     * @param stModel
     * @return
     * @date 2020/1/5 12:45
     * @author 陈瑞扬
     * @description 属性查看
     */
    @Override
    public String stshow(STModel stModel) {
        String resultMsg = "";

        List<STModel> modelList = stMapper.selectItem(stModel.getStrQQ(), stModel.getStrGroup());

        for (int i = 0; i < modelList.size(); i++) {
            STModel model = modelList.get(i);
            String stName = model.getStName();
            String stScore = model.getStScore();
            resultMsg+=stName+":"+stScore+"\n";
        }

        return resultMsg;
    }
}
