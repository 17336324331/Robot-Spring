package com.xingguang.utils;

import com.forte.qqrobot.beans.messages.result.GroupMemberInfo;
import com.forte.qqrobot.sender.MsgSender;
import com.xingguang.model.RuleModel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈瑞扬
 * @date 2019年12月01日 11:51
 * @description 逻辑处理
 */
public class LogicUtil {

    private static final Logger logger = LoggerFactory.getLogger("LogicUtil");

    // 今日人品
    public static String jrrp() throws Exception {

        // 读取配置文件
        PropertiesUtil props = new PropertiesUtil("param.properties");
        // 获取1-100随机数
        int intJrrp = getRandom(100);
        // 今日人品前缀
        String resultMsg = props.getProperty("jrrp_prefix");
        String renpinMessage = props.getProperty("renpinMessage");

        // 今日人品分数
        resultMsg += intJrrp;
//        // 今日人品后缀
//        if (intJrrp >= 90){
//            resultMsg += props.getProperty("jrrp_unlucky");
//        }else if (intJrrp >= 40 ){
//            resultMsg += props.getProperty("jrrp_suffix");
//        }else {
//            resultMsg += props.getProperty("jrrp_lucky");
//        }

        return resultMsg+renpinMessage;
        //+"\n"
    }

    // 查看帮助
    public static String helpzhiling() throws Exception {
        // 读取配置文件
        PropertiesUtil props = new PropertiesUtil("param.properties");
        // 获取配置文件中帮助菜单的内容
        String resultMsg = props.getProperty("helpzhingling");

        return resultMsg;
    }

    // 查看更新
    public static String helpgengxin() throws Exception {
        // 读取配置文件
       // PropertiesUtil props = new PropertiesUtil("param.properties");
        // 获取配置文件中帮助菜单的内容
        //String resultMsg = props.getProperty("helpzhingling");
        //String resultMsg = "1.修复了@不生效的bug\n2.添加了dismiss退群指令\n3.添加了coc/dnd人物作成\n4.添加了群员禁言\n5.完善了bot和help菜单\n6.技能判定两行合并为1行";
        String resultMsg = "1.修复不@bot生效的bug\n2.修改了bot对话\n3.jrrp对话直接换行\n4.ra/rc修复空格bug\n5.coc随机数范围重置\n6.st属性功能添加\n7.rules功能添加\n8.随机生成怪物属性\n9.ra/rc添加减法\n10.name随机姓名";
        return resultMsg;
    }

    // 查看帮助
    public static String help() throws Exception {
        // 读取配置文件
        PropertiesUtil props = new PropertiesUtil("param.properties");
        // 获取配置文件中帮助菜单的内容
        String resultMsg = props.getProperty("help");

        return resultMsg;
    }

    // 技能鉴定
    public static String ra(String strMsg){
        // 只有长度大于3 才是正常命令  (.ra谈判 1 )
        if (strMsg.trim().length() >3){
            String strCommand = "";
            if (strMsg.contains("a")){
                // 获取 命令和分数  (谈判 1)
                strCommand = strMsg.substring(strMsg.indexOf('a')+1).trim();
            }else if (strMsg.contains("c")){
                // 获取 命令和分数  (谈判 1)
                strCommand = strMsg.substring(strMsg.indexOf('c')+1).trim();
            }
            // 获取 命令和分数  (谈判 1)
             strMsg.substring(strMsg.indexOf('a')+1).trim();
            // 如果包含 英文空格就以英文空格断开
            try {
                //String[] arrCommand = strCommand.split("\\s+");
                String reason = "";
                int number = 0;
                if (StringUtils.isNotBlank(strCommand)){
                    reason = trimNumber(strCommand);
                    number = getNumberfromStr(strCommand);
                }
                return "进行"+(reason.equals("克苏鲁")?"克苏鲁神话":reason)+"检定:"+skillcheck(number+"");
            }catch (Exception e){
                e.printStackTrace();
                return LogicUtil.getErrorMsg();
            }

        }
        // 命令不正确时返回""
        else{
            return  LogicUtil.getErrorMsg();
        }

    }

    // 惩罚骰
    public static String ri(String strMsg){
        // 只有长度大于3 才是正常命令  (.ra谈判 1 )
        if (strMsg.trim().length() >=3){
            String strP = strMsg.substring(strMsg.indexOf('i')+1).trim();
            int random20 = getRandom(20);

            if (StringUtils.isNotBlank(strP)){
                return  strP +"的先攻骰点 : D20="+random20;
            }else{
                return  "*的先攻骰点 : D20="+random20;
            }
        }
        // 命令不正确时返回""
        else{
            return  "";
        }

    }

    // 惩罚骰
    public static String rp(String strMsg){
        // 只有长度大于3 才是正常命令  (.ra谈判 1 )
        if (strMsg.trim().length() >=3){
            String strP = strMsg.substring(strMsg.indexOf('p')+1).trim();
            int random100 = getRandom(100);
            int random10 = getRandom(10);
            String strSesult = "";
            if (random100<10&&random10<10){
                if (random100>=random10){
                    strSesult += random100;
                    strSesult += random10;
                }else{
                    strSesult += random10;
                    strSesult += random100;

                }

            }else{
                if (random100>=random10){
                    strSesult += random100;

                }else{
                    strSesult += random10;
                }

            }

            if (StringUtils.isNotBlank(strP)){
                return  "由于 "+ strP +" 骰出了:P="+random100+"[惩罚骰:"+random10+"]="+strSesult;
            }else{
                return  "骰出了:P="+random100+"[惩罚骰:"+random10+"]="+strSesult;
            }
        }
        // 命令不正确时返回""
        else{
            return  "";
        }

    }

    // 奖励骰
    public static String rb(String strMsg){
        // 只有长度大于3 才是正常命令  (.ra谈判 1 )
        if (strMsg.trim().length() >=3){
            String strB = strMsg.substring(strMsg.indexOf('b')+1).trim();
            int random100 = getRandom(99);
            int random10 = getRandom(9);
            String strSesult = "";
            if (random100<10){
                if (random100>=random10){
                    strSesult += random10;
                }else{
                    strSesult += random10;

                }
            }else{
                if (random100/10>=random10){
                    strSesult += random10;
                    strSesult +=random100%10;

                }else{
                    strSesult += random100;
                }

            }

            if (StringUtils.isNotBlank(strB)){
                return  "由于 "+ strB +" 骰出了:P="+random100+"[奖励骰:"+random10+"]="+strSesult;
            }else{
                return  "骰出了:P="+random100+"[奖励骰:"+random10+"]="+strSesult;
            }
        }
        // 命令不正确时返回""
        else{
            return  "";
        }

    }

    // 普通投掷
    public static String rhd(String strMsg) {
            return getRandom(100)+"";
    }

    // 保存属性
    public static String st(String strMsg) {
        try {
            PropertiesUtil props = new PropertiesUtil("param.properties");
            // 获取配置文件中帮助菜单的内容
            String resultMsg = props.getProperty("setRoleCard");

            return resultMsg;
        }catch (Exception e){

            e.printStackTrace();
            return getErrorMsg();

        }

    }

    public static String r(String strMsg){
        int rIndex = strMsg.indexOf("r");
        String substring = strMsg.substring(rIndex+1);

        return "骰出了:"+ CalcUtil.dealData(substring);

    }

    // 成长鉴定
    public static String en(String strMsg){
        int rIndex = strMsg.indexOf("n");
        String substring = strMsg.substring(rIndex+1).trim();
        String shuxing = substring.substring(0, 2);
        String fenshu = substring.substring(2, substring.length());
        int intFenshu = Integer.valueOf(fenshu.trim());
        int random = getRandom(100);
        String  compareRes = "";
        if (random < intFenshu){
            compareRes = "是失败呢 你的" +shuxing+"没有变化";
        }else{
            compareRes = "是成功呢 你的" +shuxing+"增加了d10="+getRandom(10);
        }

        String s = CalcUtil.dealData("1d100");

        return "的"+shuxing+"增强或成长鉴定:1D100="+random+"/"+fenshu+compareRes;

    }

    // 普通投掷
    public static String rOld(String strMsg){

        int rIndex = strMsg.indexOf("r");
        // increase 加法
        int increase = strMsg.indexOf("+");
        //  减法
        int subduction = strMsg.indexOf("-");
        int inta = increase==-1?subduction:increase;
        //int i1 = strMsg.indexOf("-");
        int intb = strMsg.indexOf("*");
        String substring = "";
        if (inta==-1&&intb==-1){
             substring = strMsg.substring(rIndex+1);
        }else if (inta!=-1&&intb!=-1){
            if (inta<intb){
                substring = strMsg.substring(rIndex+1,inta);
            }else{
                substring = strMsg.substring(rIndex+1,intb);
            }
        }else{
            if (inta<intb){
                substring = strMsg.substring(rIndex+1,intb);
            }else{
                substring = strMsg.substring(rIndex+1,inta);
            }
        }

        substring = substring.trim();

        if (substring.contains("d")){
            List<Integer> intList = new ArrayList<Integer>();
            String str = null;
            if (substring.indexOf("d")==0){
                String strFace = substring.substring(1).trim();
                Integer face = Integer.valueOf(Integer.valueOf(strFace));
                int random = getRandom(face+1);
                return "骰出了"+substring.toUpperCase()+" = "+random;

            }else{

                String[] arrNum = substring.split("d");
                // 投掷多少次
                int num = Integer.valueOf(arrNum[0]);
                for (int i = 0; i < num; i++) {
                    // 几个面的骰子
                    Integer face = Integer.valueOf(arrNum[1]);
                    int intResult = (int) (Math.random() * (face-1) + 1);
                    intList.add(intResult);
                }

                String strSum = "";
                for (Object o : intList) {
                    strSum+="+"+o.toString();
                }

                Integer sum = 0;
                for (Integer o : intList) {
                    sum+=o;
                }

                // 没有乘号并且没有减号
                if (inta==-1&&intb==-1){
                    substring = strMsg.substring(rIndex+1);
                    return "骰出了"+substring.toUpperCase()+" : "+strSum.substring(1)+"="+sum;
                }
                //有乘号并且有加减号
                else if (inta!=-1&&intb!=-1){
                    // 先加减法再乘
                    if (inta<intb){
                        Integer factora = Integer.valueOf(strMsg.substring(inta+1,intb).trim());
                        Integer factorb = Integer.valueOf(strMsg.substring(intb+1).trim());
                        substring = strMsg.substring(rIndex+1,inta);
                        if (increase!=-1){
                            return "骰出了"+substring.toUpperCase()+" : ("+strSum.substring(1)+")+"+factora+"*"+factorb+"="+(sum+factorb*factora);
                        }else{
                            return "骰出了"+substring.toUpperCase()+" : ("+strSum.substring(1)+")-"+factora+"*"+factorb+"="+(sum-factorb*factora);
                        }

                    }
                    // 先乘再加减
                    else{
                        // a 是 乘数
                        Integer factora = Integer.valueOf(strMsg.substring(intb+1,inta).trim());
                        // b 是 加数
                        Integer factorb = Integer.valueOf(strMsg.substring(inta+1).trim());
                        substring = strMsg.substring(rIndex+1,intb);
                        if (increase!=-1){
                            return "骰出了"+substring.toUpperCase()+" : ("+strSum.substring(1)+")*"+factora+"+"+factorb+"="+(sum*factora+factorb);
                        }else{
                            return "骰出了"+substring.toUpperCase()+" : ("+strSum.substring(1)+")*"+factora+"-"+factorb+"="+(sum*factora-factorb);
                        }

                    }
                }
                // 有其中一个
                else{
                    // 有乘号
                    if (inta<intb){
                        Integer factor = Integer.valueOf(strMsg.substring(intb+1).trim());
                        substring = strMsg.substring(rIndex+1,intb);
                        return "骰出了"+substring.toUpperCase()+" : ("+strSum.substring(1)+")*"+factor+"="+sum*factor;
                    }
                    // 有加减号
                    else{
                        Integer factor = Integer.valueOf(strMsg.substring(inta+1).trim());
                        substring = strMsg.substring(rIndex+1,inta);
                        if (increase!=-1){
                            return "骰出了"+substring.toUpperCase()+" : ("+strSum.substring(1)+")+"+factor+"="+(+sum+factor);
                        }else {
                            return "骰出了"+substring.toUpperCase()+" : ("+strSum.substring(1)+")-"+factor+"="+(+sum-factor);
                        }

                    }
                }

                //return "骰出了"+substring.toUpperCase()+" : "+strSum.substring(1)+"="+sum;

            }

        }else{
            // 获取100以内随机数
            int randomSocre = getRandom(100);
            // 拼接返回语句
            String strResultMsg = "D100="+randomSocre;
            return "骰出了"+strResultMsg;
        }

    }

    public static String coc(String strMsg){
        int times = 1 ;

        try {
            times = Integer.valueOf(getNumberfromStr(strMsg));

        }catch (Exception e){
            logger.info("错误指令:"+strMsg);
            return LogicUtil.getErrorMsg();
        }

        StringBuffer sb = new StringBuffer();
        sb.append("的人物作成:\n");
        for (int i=0;i<times;i++){
            StringBuffer sbtemp = new StringBuffer();

            int liliang = 5*getRandom(3,18);
            int tizhi = 5*getRandom(3,18);
            int tixing =5*getRandom(8,18);
            int minjie =5*getRandom(3,18);

            int waimao = 5*getRandom(3,18);
            int zhili = 5*getRandom(8,18);
            int yizhi =5*getRandom(3,18);
            int jiaoyu =5*getRandom(8,18);

            int xingyun =5*getRandom(3,18);

            int total = liliang+tizhi+tixing+minjie+waimao+zhili+yizhi+jiaoyu;
            int total2 = total+xingyun;

            sbtemp.append("力量:"+liliang);
            sbtemp.append(" 体质:"+tizhi);
            sbtemp.append(" 体型:"+tixing);
            sbtemp.append(" 敏捷:"+minjie);

            sbtemp.append(" 外貌:"+waimao);
            sbtemp.append(" 智力:"+zhili);
            sbtemp.append(" 意志:"+yizhi);
            sbtemp.append(" 教育:"+jiaoyu);

            sbtemp.append(" 幸运:"+xingyun);

            sbtemp.append(" 共计:"+total +"/"+total2);
            if (i<times-1){
                sbtemp.append("\n");
            }
            sb.append(sbtemp);
        }
        return sb.toString();
    }


    public static String dnd(String strMsg){
        int times = 1 ;

        try {
            times = Integer.valueOf(getNumberfromStr(strMsg));
        }catch (Exception e){
            logger.info("错误指令:"+strMsg);
        }

        StringBuffer sb = new StringBuffer();
        sb.append("的人物作成:\n");
        for (int i=0;i<times;i++){
            StringBuffer sbtemp = new StringBuffer();

            int liliang = getRandom(20);
            int tizhi = getRandom(20);
            int minjie =getRandom(20);


            int zhili = getRandom(20);
            int ganzhi=getRandom(20);
            int meihuo =getRandom(20);


            int total = liliang+tizhi+minjie+zhili+ganzhi+meihuo;

            sbtemp.append("力量:"+liliang);
            sbtemp.append(" 体质:"+tizhi);
            sbtemp.append(" 敏捷:"+minjie);

            sbtemp.append(" 智力:"+zhili);
            sbtemp.append(" 感知:"+ganzhi);
            sbtemp.append(" 魅惑:"+meihuo);

            sbtemp.append(" 共计:"+total );
            if (i<times-1){
                sbtemp.append("\n");
            }
            sb.append(sbtemp);
        }
        return sb.toString();
    }


    // 向master发送消息
    public static String send(String strMsg){
        // 只有长度大于3 才是正常命令  (.ra谈判 1    .rc 逃跑 3)
        if (strMsg.trim().length() >5){
            // 获取 命令和分数  (谈判 1)
            String strCommand = strMsg.substring(5).trim();
            // 如果包含 英文空格就以英文空格断开
            if (strCommand.contains(" ")){
                String[] arrCommand = strCommand.split(" ");
                return "进行"+arrCommand[0]+"检定:"+skillcheck(arrCommand[1]);
            }
            // 如果包含 中文空格就以中文空格断开
            else if(strCommand.contains("　")){
                String[] arrCommand = strCommand.split(" ");
                return "进行"+arrCommand[0]+"检定:"+skillcheck(arrCommand[1]);
            }else {
                return "";
            }

        }
        // 命令不正确时返回""
        else{
            return  "";
        }

    }



    // 获取指定范围的随机数
    public static int getRandom(int intRandom){
        Double douJrrp = Math.random() * (intRandom)+1;
        int intJrrp = douJrrp.intValue();
        return intJrrp;

    }

    // 获取指定范围的随机数
    public static int getRandom(int intBegin,int intEnd){
        Double douJrrp = Math.random() * (intEnd-intBegin-1)+1;
        int intJrrp = douJrrp.intValue();
        intJrrp+=intBegin;
        return intJrrp;

    }

    // 根据规则 创建实体
    public static RuleModel createModelByRule(RuleModel model){

        model.setStrCON(getScoreBycalc(model.getStrCON()));
        model.setStrDEX(getScoreBycalc(model.getStrDEX()));
        model.setStrINT(getScoreBycalc(model.getStrINT()));
        model.setStrSTR(getScoreBycalc(model.getStrSTR()));
        model.setStrSIZ(getScoreBycalc(model.getStrSIZ()));
        model.setStrPOW(getScoreBycalc(model.getStrPOW()));
        model.setStrHURT(getScoreBycalc(model.getStrHURT()));

        return  model;
    }

    // 技能鉴定结果 true|false
    public static String skillcheck(String score){
        int skillSocre = Integer.valueOf(score);
        int randomSocre = getRandom(100);
        String strResultMsg = "D100="+randomSocre+"/"+skillSocre;
        PropertiesUtil props = null;
        // 读取配置文件
        try {
            props = new PropertiesUtil("param.properties");
        }catch (Exception e){
            logger.info(e+"");
        }

        // bigFail
        if ( randomSocre > 95 ){
            strResultMsg += props.getProperty("bigFail");
            return strResultMsg;
        }
        // bigSuccess
        else if ( randomSocre < 5){
            strResultMsg += props.getProperty("bigSuccess");
            return strResultMsg;
        }
        //
        else if( skillSocre >= randomSocre*4 ){
            strResultMsg += props.getProperty("bigDiffcult");
            return strResultMsg;

        }
        //
        else if( skillSocre >= randomSocre*2 ){
            strResultMsg += props.getProperty("diffcult");
            return strResultMsg;
        }
        //
        else if( skillSocre >= randomSocre ){
            strResultMsg += props.getProperty("success");
            return strResultMsg;
        }
        //
        else if( skillSocre < randomSocre ){
            strResultMsg += props.getProperty("fail");
            return strResultMsg;
        }else {
            return strResultMsg;
        }

    }

    public boolean  checkManage(){

        return false;

    }

    public static String getErrorMsg(){
        // 读取配置文件
        PropertiesUtil props = null;
        try {
            props = new PropertiesUtil("param.properties");
        }catch (Exception e){
            logger.info(e+"");
        }

        String errorMsg = props.getProperty("errorMsg");

        return errorMsg;


    }

    public static Integer getNumberfromStr(String strMsg){
            String str = strMsg.trim();
            String str2="";
            if(StringUtils.isNotBlank(strMsg)){
                for(int i=0;i<str.length();i++) {
                    if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                        str2 += str.charAt(i);
                    }
                }
            }
            return  Integer.valueOf(str2);
    }

    // 要确保 str 已经 trim 且不为空
    public static String trimNumber(String str){
        String resultMsg = "";

        for(int i=str.length()-1;i>=0;i--) {
            if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {

            }else{
                resultMsg = str.substring(0,i+1).trim();
                break;
            }
        }

        return resultMsg;

    }

    public static String  getScoreBycalc(String calc){
        int length = calc.length();
        Integer score = 0;
        if (length == 3){
            String[] arr = calc.split("D");
            Integer a = Integer.valueOf(arr[0]);
            Integer b = Integer.valueOf(arr[1]);

            for (int i = 0; i < a; i++) {
                score+=getRandom(b);
            }
        }else if (length == 6){
            if (calc.contains("×")){
                // 得到D的索引
                int aIndex = calc.indexOf('D');
                // 得到*的索引
                int bIndex = calc.indexOf('×');
                String baseString = calc.substring(0, aIndex + 2);
                String[] arr = baseString.split("D");
                // 第一个 数字
                Integer a = Integer.valueOf(arr[0]);
                // 第二个 数字
                Integer b = Integer.valueOf(arr[1]);
                // 第三个数字
                Integer c = Integer.valueOf(calc.substring(5, 6));
                for (int i = 0; i < a; i++) {
                    score+=getRandom(b);
                }
                score = score * c;

            }else if(calc.contains("+")){
                // 得到 第一个数字
                int a = Integer.valueOf(calc.substring(0,1));
                // 得到第二个数字
                int b = Integer.valueOf(calc.substring(2,3));
                // 得到第三个数字
                int c = Integer.valueOf(calc.substring(5,6));

                for (int i = 0; i < a; i++) {
                    score+=getRandom(b);
                }
                score = score  +  c;
            }

        }
        // 先加后乘法
        else if (length == 8 ){
            // 得到 第一个数字
            int a = Integer.valueOf(calc.substring(0,1));
            // 得到第二个数字
            int b = Integer.valueOf(calc.substring(2,3));
            // 得到第三个数字
            int c = Integer.valueOf(calc.substring(4,5));
            // 得到第四个数字
            int d = Integer.valueOf(calc.substring(7,8));

            for (int i = 0; i < a; i++) {
                score+=getRandom(b);
            }

            score *= c;
            score += d;

        }else{
            score = -1;

        }
        return score + "";

    }


    // 获取 QQ昵称/群昵称/管理员昵称
    public static String getName(String strQQ,String strGroup, MsgSender sender){
        if(StringUtils.isNotBlank(strQQ)){
            // 获取发言人对应的管理员名称,没有则为null
            String adminName = CommandUtil.checkAdmin(strQQ);
            if(StringUtils.isNotBlank(adminName)){
                //resultMsg= resultMsg.replace("xxx",adminName);
               return adminName;
            }else{
                GroupMemberInfo memberInfo = sender.GETTER.getGroupMemberInfo(strGroup, strQQ, true);

                String card = memberInfo.getCard()==null?memberInfo.getNickName():memberInfo.getCard();
                card = card == null ?sender.getPersonInfoByCode(strQQ).getName():card;
                String name = sender.getPersonInfoByCode(strQQ).getName();
                return card;
            }
        }else{
            return "";
        }


    }

//    public static void main(String[] args) {
//
//        for (int i = 0; i < 1000; i++) {
//            getRandom(15,90);
//        }
//
//
//    }


}
