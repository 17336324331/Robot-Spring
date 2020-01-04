package com.xingguang.service.Impl;

import com.forte.qqrobot.beans.messages.get.GetStrangerInfo;
import com.xingguang.mapper.ImageMapper;
import com.xingguang.model.VoImageModel;
import com.xingguang.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 陈瑞扬
 * @date 2020年01月04日 11:00
 * @description
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageMapper imageMapper;

    /**
     * @param strImageMsg
     * @return
     * @date 2020/1/4 10:58
     * @author 陈瑞扬
     * @description 处理带有image的消息
     */
    @Override
    public VoImageModel dealImageMsg(String strImageMsg,String strQQ) {
        int beginIndex = strImageMsg.lastIndexOf("=")+1;
        int endIndex =  0 ;
        if (strImageMsg.contains("jpg")){
            endIndex = strImageMsg.lastIndexOf("jpg")+3;
        }
        else if(strImageMsg.contains("png")){
            endIndex = strImageMsg.lastIndexOf("png")+3;
        }
        String imageId = strImageMsg.substring(beginIndex, endIndex);
        String strRet = "";
        VoImageModel model = imageMapper.selectRetByImageId(imageId);

        if ("7655E8B9FA5C8FAA87766753EB866A49.jpg".equals(imageId)){
            if ("1571650839".equals(strQQ)){
                strRet = "那么既然是master的话,我想主人一定会同意的吧";

            }else if ("2649173157".equals(strQQ)){
                strRet = "主人要自己睡自己吗,完全无法想象呢";
            }
            else if(true){


            }
            model.setStrRet(strRet);
        }

        return model == null ? new VoImageModel():model;
    }

    /**
     * @param strImageMsg
     * @return
     * @date 2020/1/4 15:52
     * @author 陈瑞扬
     * @description 保存新图片
     */
    @Override
    public void saveImage(String strImageMsg) {
        int beginIndex = strImageMsg.lastIndexOf("=")+1;
        int endIndex = strImageMsg.lastIndexOf("g")+1;
        String imageId = strImageMsg.substring(beginIndex, endIndex);
        imageMapper.saveImage(imageId);
    }
}
