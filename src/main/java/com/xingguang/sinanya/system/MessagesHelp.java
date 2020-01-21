package com.xingguang.sinanya.system;

import com.xingguang.sinanya.system.imal.HelpText;

import java.util.HashMap;

/**
 * @author 孔翠老板娘
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 接口说明: 所有命令的简要说明
 */
public class MessagesHelp extends HelpText {
    public static StringBuilder NORMAL_HELP = new StringBuilder()
            .append("常规骰点帮助包含以下内容:\n")

            .append(".r")
            .append("\t")
            .append("简化骰点")
            .append("\n")

            .append(".rd")
            .append("\t")
            .append("骰点并进行简单计算")
            .append("\n")

            .append(".rh")
            .append("\t")
            .append("进行暗骰")
            .append("\n")

            .append(".set")
            .append("\t")
            .append("设置该群默认骰")
            .append("\n")

            .append(".ra")
            .append("\t")
            .append("技能检定(房规)")
            .append("\n")

            .append(".rc")
            .append("\t")
            .append("技能检定(规则书)")
            .append("\n")

            .append(".rb")
            .append("\t")
            .append("奖励骰")
            .append("\n")

            .append(".rp")
            .append("\t")
            .append("惩罚骰")
            .append("\n")

            .append(".sc")
            .append("\t")
            .append("理智检定")
            .append("\n")

            .append(".hiy")
            .append("\t")
            .append("获取骰点历史信息")
            .append("\n")

            .append(".en")
            .append("\t")
            .append("技能/属性成长检定")
            .append("\n")

            .append(".rav")
            .append("\t")
            .append("对抗骰点（房规）")
            .append("\n")

            .append(".rcv")
            .append("\t")
            .append("对抗骰点（规则书）")
            .append("\n")

            .append("请使用.man命令查看具体用法，如.man r/.man set");
    public static StringBuilder MAKE_HELP = new StringBuilder()
            .append("人物卡帮助包含以下内容:\n")

            .append(".coc")
            .append("\t")
            .append("coc人物属性做成")
            .append("\n")

            .append(".gas")
            .append("\t")
            .append("6版拓展规则煤气灯特征抽取。")
            .append("\n")

            .append(".tz")
            .append("\t")
            .append("非官方特质抽取")
            .append("\n")

            .append(".getbook card")
            .append("\t")
            .append("获取人物卡")
            .append("\n")

            .append(".getbook make")
            .append("\t")
            .append("车卡指南")
            .append("\n")

            .append(".st")
            .append("\t")
            .append("人物卡属性录入")
            .append("\n")

            .append(".st list")
            .append("\t")
            .append("查看已录入人物卡列表")
            .append("\n")

            .append(".st rm")
            .append("\t")
            .append("删除人物卡")
            .append("\n")

            .append(".st clr")
            .append("\t")
            .append("清空当前人物数据")
            .append("\n")

            .append(".st show")
            .append("\t")
            .append("显示当前人物卡属性及技能")
            .append("\n")

            .append("请使用.man命令查看具体用法，如.man coc/.man gas");

    public static StringBuilder GAME_HELP = new StringBuilder()
            .append("娱乐命令帮助包含以下内容:\n")

            .append(".deck")
            .append("\t")
            .append("牌库抽取")
            .append("\n")

            .append(".deck help")
            .append("\t")
            .append("牌库抽取帮助")
            .append("\n")

            .append(".jrrp")
            .append("\t")
            .append("今日人品抽取")
            .append("\n")

            .append(".fire")
            .append("\t")
            .append("向火独行模块")
            .append("\n")

            .append("请使用.man命令查看具体用法，如.man deck/.man deck help");
    public static StringBuilder GROUP_HELP = new StringBuilder()
            .append("带团命令帮助包含以下内容:\n")

            .append(".kp")
            .append("\t")
            .append("设置该群为带团群，并设置自己为本群kp")
            .append("\n")

            .append(".team set")
            .append("\t")
            .append("添加群成员进入小队")
            .append("\n")

            .append(".team")
            .append("\t")
            .append("查看当前小队成员hp、san、db、体格等信息，此信息会随着人物卡信息变动而实时变动。")
            .append("\n")

            .append(".team desc")
            .append("\t")
            .append("骰娘私聊kp小队成员人物卡属性")
            .append("\n")

            .append(".team en")
            .append("\t")
            .append("统计小队内成员技能成功情况")
            .append("\n")

            .append(".team hp")
            .append("\t")
            .append("增减小队成员hp")
            .append("\n")

            .append(".team san")
            .append("\t")
            .append("增减小队成员san值")
            .append("\n")

            .append(".team rm")
            .append("\t")
            .append("删除小队成员")
            .append("\n")

            .append(".team clr")
            .append("\t")
            .append("清空小队")
            .append("\n")

            .append(".team call")
            .append("\t")
            .append("at小队内成员")
            .append("\n")

            .append(".log on")
            .append("\t")
            .append("开始记录跑团日志")
            .append("\n")

            .append(".log off")
            .append("\t")
            .append("停止记录跑团日志")
            .append("\n")

            .append(".log list")
            .append("\t")
            .append("查看当前群log列表 ")
            .append("\n")

            .append(".log get")
            .append("\t")
            .append("获取日志文件")
            .append("\n")

            .append(".log rm")
            .append("\t")
            .append("删除记录")
            .append("\n")

            .append(".clue")
            .append("\t")
            .append("添加线索")
            .append("\n")

            .append(".clue show")
            .append("\t")
            .append("显示当前群内记录线索集")
            .append("\n")

            .append(".clue rm")
            .append("\t")
            .append("删除线索")
            .append("\n")

            .append(".clue clr")
            .append("\t")
            .append("线索集清空")
            .append("\n")

            .append(".ob")
            .append("\t")
            .append("进入旁观模式")
            .append("\n")

            .append(".npc")
            .append("\t")
            .append("npc人物做成。")
            .append("\n")

            .append(".rav")
            .append("\t")
            .append("对抗骰点（房规）")
            .append("\n")

            .append(".rcv")
            .append("\t")
            .append("对抗骰点（规则书）")
            .append("\n")

            .append(".ral")
            .append("\t")
            .append("多次数值检定（房规）")
            .append("\n")

            .append(".rcl")
            .append("\t")
            .append("多次数值检定（规则书）")
            .append("\n")

            .append(".rule")
            .append("\t")
            .append("用法：.rule [规则名]")
            .append("\n")

            .append(".name")
            .append("\t")
            .append("生成随机姓名")
            .append("\n")

            .append(".nn")
            .append("\t")
            .append("随机取名及切换人物卡")
            .append("\n")

            .append("请使用.man命令查看具体用法，如.man kp/.man team set");
    public static StringBuilder BOOK_HELP = new StringBuilder()
            .append("资料集命令帮助包含以下内容:\n")

            .append(".getbook card")
            .append("\t")
            .append("获取人物卡")
            .append("\n")

            .append(".getbook make")
            .append("\t")
            .append("车卡指南")
            .append("\n")

            .append(".getbook kp")
            .append("\t")
            .append("获取规则书")
            .append("\n")

            .append(".getbook rp")
            .append("\t")
            .append("获取rp360问")
            .append("\n")

            .append(".rule")
            .append("\t")
            .append("coc7版规则速查")
            .append("\n")

            .append(".cnmods")
            .append("\t")
            .append("魔都模组查询")
            .append("\n")

            .append("请使用.man命令查看具体用法，如.man getbook card/.man getbook make");
    public static StringBuilder DND_HELP = new StringBuilder()
            .append("DND命令帮助包含以下内容:\n")

            .append(".dnd")
            .append("\t")
            .append("dnd人物属性做成")
            .append("\n")

            .append(".ri")
            .append("\t")
            .append("先攻骰掷")
            .append("\n")

            .append(".init")
            .append("\t")
            .append("查看当前先攻列表，需要使用.ri命令投先攻，详情查看.man ri")
            .append("\n")

            .append(".init rm")
            .append("\t")
            .append("删除某条先攻记录，详情查阅.man init rm")
            .append("\n")

            .append(".init set")
            .append("\t")
            .append("添加一条给定结果的先攻记录，详情查阅.man init set")
            .append("\n")

            .append(".init clr")
            .append("\t")
            .append("清空先攻列表")
            .append("\n")

            .append(".con")
            .append("\t")
            .append("dnd状态速查")
            .append("\n")

            .append(".magic")
            .append("\t")
            .append("dnd魔法速查")
            .append("\n")

            .append("请使用.man命令查看具体用法，如.man dnd/.man ri");

    public static StringBuilder OTHOR_HELP = new StringBuilder()
            .append("其他规则帮助包含以下内容:\n")

            .append(".w")
            .append("\t")
            .append("无限骰点的简化命令")
            .append("\n")

            .append(".ww")
            .append("\t")
            .append("无限骰点的常规命令")
            .append("\n")

            .append(".ws")
            .append("\t")
            .append("无限骰点的统计命令")
            .append("\n")

            .append(".rf")
            .append("\t")
            .append("命运团的骰点命令")
            .append("\n")

            .append("请使用.man命令查看具体用法，如.man w/.man ww");

    public static StringBuilder MASTER_HELP = new StringBuilder()
            .append("骰主命令帮助包含以下内容:\n")

            .append(".ban")
            .append("\t")
            .append("黑名单管理命令")
            .append("\n")

            .append(".ban list")
            .append("\t")
            .append("黑名单查看命令")
            .append("\n")

            .append(".white")
            .append("\t")
            .append("白名单管理命令")
            .append("\n")

            .append(".white list")
            .append("\t")
            .append("白名单查看命令")
            .append("\n")

            .append(".admin")
            .append("\t")
            .append("骰主管理器")
            .append("\n")

            .append(".group list")
            .append("\t")
            .append("群检索命令")
            .append("\n")

            .append(".group search")
            .append("\t")
            .append("群查询命令")
            .append("\n")

            .append(".group clean")
            .append("\t")
            .append("群清理命令")
            .append("\n")

            .append("请使用.man命令查看具体用法，如.man ban/.man white");

    public static StringBuilder MANAGER_HELP = new StringBuilder()
            .append("群管理帮助命令帮助包含以下内容:\n")

            .append(".dice list")
            .append("\t")
            .append("骰娘列表")
            .append("\n")

            .append(".group")
            .append("\t")
            .append("群管理命令")
            .append("\n")

            .append(".welcome")
            .append("\t")
            .append("入群欢迎词")
            .append("\n")

            .append("请使用.man命令查看具体用法，如.man ban/.man white");
    public static HashMap<String, String> helpMap = new HashMap<>();

    static {
        helpMap.put("r", r.toString());
        helpMap.put("rd", rd.toString());
        helpMap.put("rh", rh.toString());
        helpMap.put("set", set.toString());
        helpMap.put("ra", ra.toString());
        helpMap.put("rc", rc.toString());
        helpMap.put("rb", rb.toString());
        helpMap.put("rp", rp.toString());
        helpMap.put("sc", sc.toString());
        helpMap.put("ti", ti.toString());
        helpMap.put("li", li.toString());
        helpMap.put("en", en.toString());
        helpMap.put("coc", coc.toString());
        helpMap.put("gas", gas.toString());
        helpMap.put("tz", tz.toString());
        helpMap.put("rav", rav.toString());
        helpMap.put("rcv", rcv.toString());
        helpMap.put("st", st.toString());
        helpMap.put("st list", st_list.toString());
        helpMap.put("st rm", st_rm.toString());
        helpMap.put("st clr",st_clr.toString());
        helpMap.put("st show", st_show.toString());
        helpMap.put("kp", kp.toString());
        helpMap.put("team set", team_set.toString());
        helpMap.put("team", team.toString());
        helpMap.put("team desc", team_desc.toString());
        helpMap.put("team en", team_en.toString());
        helpMap.put("team hp", team_hp.toString());
        helpMap.put("team san", team_san.toString());
        helpMap.put("team rm", team_rm.toString());
        helpMap.put("team clr", team_clr.toString());
        helpMap.put("team call", team_call.toString());
        helpMap.put("log on", log_on.toString());
        helpMap.put("log off", log_off.toString());
        helpMap.put("log list", log_list.toString());
        helpMap.put("log get", log_get.toString());
        helpMap.put("log rm", log_rm.toString());
        helpMap.put("ob", ob.toString());
        helpMap.put("ral", ral.toString());
        helpMap.put("rcl", rcl.toString());
        helpMap.put("clue", clue.toString());
        helpMap.put("clue show", clue_show.toString());
        helpMap.put("clue rm", clue_rm.toString());
        helpMap.put("clue clr", clue_clr.toString());
        helpMap.put("npc", npc.toString());
        helpMap.put("dnd", dnd.toString());
        helpMap.put("ri", ri.toString());
        helpMap.put("init", init.toString());
        helpMap.put("init clr", init_clr.toString());
        helpMap.put("init rm", init_rm.toString());
        helpMap.put("init set", init_set.toString());
        helpMap.put("magic", magic.toString());
        helpMap.put("con", con.toString());
        helpMap.put("rule", rule.toString());
        helpMap.put("name", name.toString());
        helpMap.put("jrrp", jrrp.toString());
        helpMap.put("deck", deck.toString());
        helpMap.put("deck help", deck_help.toString());
        helpMap.put("hiy", hiy.toString());
        helpMap.put("getbook card", getbook_card.toString());
        helpMap.put("getbook make", getbook_make.toString());
        helpMap.put("getbook kp", getbook_kp.toString());
        helpMap.put("getbook rp", getbook_rp.toString());
        helpMap.put("w", w.toString());
        helpMap.put("ww", ww.toString());
        helpMap.put("ws", ws.toString());
        helpMap.put("rf", fate.toString());
        helpMap.put("ban", ban.toString());
        helpMap.put("ban list", ban_list.toString());
        helpMap.put("white", white.toString());
        helpMap.put("white list", white_list.toString());
        helpMap.put("admin", admin.toString());
        helpMap.put("dice list", dice_list.toString());
        helpMap.put("group", group.toString());
        helpMap.put("welcome", welcome.toString());
        helpMap.put("group list", group_list.toString());
        helpMap.put("group search", group_search.toString());
        helpMap.put("group clean", group_clean.toString());
        helpMap.put("fire", fire.toString());
        helpMap.put("cnmods", cnmods.toString());
        helpMap.put("nn", nn.toString());
    }

    String SEQ = "\n-----------------------------------\n";
}
