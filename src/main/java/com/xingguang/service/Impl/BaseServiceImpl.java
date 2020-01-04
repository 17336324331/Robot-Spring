package com.xingguang.service.Impl;

import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.result.GroupMemberInfo;
import com.forte.qqrobot.beans.messages.types.SexType;
import com.forte.qqrobot.sender.MsgSender;
import com.xingguang.mapper.NameMapper;
import com.xingguang.model.BaseModel;
import com.xingguang.model.NameModel;
import com.xingguang.service.BaseService;
import com.xingguang.utils.SystemParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 20:58
 * @description
 */
@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    private NameMapper nameMapper;

    /**
     * 解析Msg
     *
     * @param msg
     * @param sender
     * @return 解析结果
     */
    @Override
    public BaseModel dealMsg(GroupMsg msg, MsgSender sender) {
        BaseModel baseModel = new BaseModel();
        // 获取发言人的QQ号
        String strQQ = msg.getQQ();
        // 获取发言的群
        String strGroup = msg.getGroup();

        // 获取发言人的信息(优先选择nname游戏昵称,其次选择card群昵称,其次QQ昵称)
        String strName = nameMapper.selectNameByQQGroup(strQQ,strGroup);

        if (StringUtils.isBlank(strName)){
            GroupMemberInfo groupMemberInfo = sender.GETTER.getGroupMemberInfo(strGroup, strQQ);
            String strCard = groupMemberInfo.getCard();
            String strNickName = groupMemberInfo.getNickName();
            groupMemberInfo.getSex();
            strName =  strCard==null?strNickName:strNickName;
            try {
                NameModel nameModel = new NameModel();
                nameModel.setStrQQ(strQQ);
                nameModel.setStrGroup(strGroup);
                nameModel.setStrCard(strCard);
                nameModel.setStrNickName(strNickName);
                nameModel.setStrNName(null);
                nameMapper.saveName(nameModel);
            }catch ( Exception e){
                sender.SENDER.sendPrivateMsg(msg.getQQ(),"尝试保存名称失败");
            }

        }

        baseModel.setStrQQ(strQQ);
        baseModel.setStrGroup(strGroup);
        baseModel.setStrName(strName);

        return baseModel;
    }
}
