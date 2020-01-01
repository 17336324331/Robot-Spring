package com.xingguang.listener;

import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.messages.msgget.GroupMemberIncrease;
import com.forte.qqrobot.beans.messages.msgget.GroupMemberReduce;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.result.GroupMemberInfo;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.xingguang.mapper.SystemCodeMapper;
import com.xingguang.utils.CommandUtil;
import com.xingguang.utils.PropertiesUtil;
import com.xingguang.utils.SqlSessionFactoryUtil;
import com.xingguang.utils.SystemParam;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 陈瑞扬
 * @date 2019年12月01日 20:55
 * @description 其他事件监听
 */
public class OtherListener {

    private static final Logger logger = LoggerFactory.getLogger("OtherListener");

    // 新人入群事件
    @Listen(MsgGetTypes.groupMemberIncrease)
    public void welcome(GroupMemberIncrease msg, MsgSender sender, CQCodeUtil cqCodeUtil){
        SqlSession sqlSession = null ;
        String qq = msg.getQQCode();
        String group = msg.getGroupCode();
        String card = sender.GETTER.getGroupMemberInfo(group, qq).getCard();
        String resultMsg = "" ;
        try {
            sqlSession = SqlSessionFactoryUtil.openSqlSession();
            SystemCodeMapper mapper = sqlSession.getMapper(SystemCodeMapper.class);
            resultMsg = mapper.selectSystemCode("welcome");
        }catch (Exception e){
            e.printStackTrace();
            logger.info(e.toString());
            logger.info("welcome: qq:"+qq+"\tgroup:"+group+"\tcard"+card);

        }

        if (!"1730707275".equals(qq)){
            sender.SENDER.sendGroupMsg(group,cqCodeUtil.getCQCode_At(qq)+" "+resultMsg);
        }

    }


    // 老人退群事件
    @Listen(MsgGetTypes.groupMemberReduce)
    public void goodbye(GroupMemberReduce msg, MsgSender sender, CQCodeUtil cqCodeUtil){
        String qq = msg.getQQCode();
        String beOperatedQQ = msg.getBeOperatedQQ();
        String group = msg.getGroupCode();
        GroupMemberInfo groupMemberInfo = sender.GETTER.getGroupMemberInfo(group, qq);
        String card = groupMemberInfo.getCard();
        card = card == null ? sender.GETTER.getStrangerInfo(qq).getName() : card ;

        String resultMsg = "" ;
        try {
            // 读取配置文件
            PropertiesUtil props = new PropertiesUtil("param.properties");
            // 获取配置文件中帮助菜单的内容
            resultMsg = props.getProperty("goodbye");

        }catch (Exception e){
            e.printStackTrace();
            logger.info(e.toString());
            logger.info("welcome: qq:"+qq+"\tgroup:"+group+"\tcard"+card);
        }
        //String nickName = LogicUtil.getName(qq,group,sender);
        resultMsg = resultMsg.replace("xxx", card);
        sender.SENDER.sendGroupMsg(group,resultMsg);

    }

    // 私聊机器人开关事件
    @Listen(MsgGetTypes.privateMsg)
    public void switchStatus(PrivateMsg msg, MsgSender sender, CQCodeUtil cqCodeUtil){
        String strMsg = msg.getMsg();
        String qq = msg.getQQ();
        String adminName = CommandUtil.checkAdmin(qq);
        if (strMsg.contains("bot")&& StringUtils.isNotBlank(adminName)){

            String command = strMsg.substring(strMsg.indexOf('t')+2);
            if (command.equals("on")&&!SystemParam.botstatus){
                SystemParam.botstatus = true;
                sender.SENDER.sendPrivateMsg(qq,adminName+",,来了来了，嗯？我没有喝酒啦！今天会好好骰的！");
            }else if (command.equals("off")&&SystemParam.botstatus){
                SystemParam.botstatus = false;
                sender.SENDER.sendPrivateMsg(qq,adminName+",不需要我了？那我喝酒去了，挥挥");
            }else if(command.equals("on")){
                sender.SENDER.sendPrivateMsg(qq,adminName+",行光已经在工作了哦");
            }else if(command.equals("off")){
                sender.SENDER.sendPrivateMsg(qq,adminName+",行光已经在度假了呢");
            }else{
                sender.SENDER.sendGroupMsg(qq,adminName+",行光于2019/12/08 14:00:00更新 \n请输入 .help 更新 查看行光的更新内容");
            }
        }

        boolean botstatus = SystemParam.botstatus;
    }

    // 群聊机器人开关事件
    @Listen(MsgGetTypes.groupMsg)
    public void switchStatus(GroupMsg msg, MsgSender sender, CQCodeUtil cqCodeUtil){
        String strMsg = msg.getMsg().trim();
        if (strMsg.contains("at,qq=1730707275")){
            strMsg = strMsg.substring(strMsg.indexOf("]") + 1).trim();
        }else{
           return;
        }
        String qq = msg.getQQ();
        String strGroup = msg.getGroup();
        String adminName = CommandUtil.checkAdmin(qq);
        if (strMsg.contains("bot")&& StringUtils.isNotBlank(adminName)){

            String command = strMsg.substring(strMsg.indexOf('t')+1);
            if (command.contains("on")&&!SystemParam.botstatus){
                SystemParam.botstatus = true;
                sender.SENDER.sendGroupMsg(strGroup,adminName+",来了来了，嗯？我没有喝酒啦！今天会好好骰的！");
            }else if (command.contains("off")&&SystemParam.botstatus){
                SystemParam.botstatus = false;
                sender.SENDER.sendGroupMsg(strGroup,adminName+",不需要我了？那我喝酒去了，挥挥");
            }else if(command.contains("on")){
                sender.SENDER.sendGroupMsg(strGroup,adminName+",行光已经在工作了哦");
            }else if(command.contains("off")){
                sender.SENDER.sendGroupMsg(strGroup,adminName+",行光已经在度假了呢");
            }else{
                sender.SENDER.sendGroupMsg(strGroup,adminName+",行光于2019/12/08 14:00:00更新 \n请输入 .help 更新 查看行光的更新内容");
            }
        }

        boolean botstatus = SystemParam.botstatus;
    }



}
