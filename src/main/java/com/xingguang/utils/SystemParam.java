package com.xingguang.utils;

import com.xingguang.model.BotModel;
import com.xingguang.service.BotService;
import com.xingguang.service.SystemParamService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 这是一个系统参数工具类
 **/
public class SystemParam {

    /**
     * 系统超级管理员
     */
    public static String master;

    /**
     * 骰子开关状态(存放处于开启状态的骰娘QQ和QQ群)
     */
    public static List<BotModel> botList;

    /**
     * 当前登录的人员的QQ
     */
    public static String strCurrentQQ;

    /**
     * 当前登录的人员的QQname
     */
    public static String strCurrentQQName;

    /**
     * 异常提示语句
     */
    public static String errorMsg = "!行光完全不想搭理你,并向你扔了一杯酒";

    /**
     * 新人提示词
     */
    public static List<String>  strWelcome = Arrays.asList("（醉酒状态）啧，又来新人了啊，好麻烦，我就象征性欢迎你一下好了，虽然你肯定不会期待我的欢迎吧……","新人吗？给我乖乖坐下听讲！嘿嘿，只是个玩笑，玩笑，祝愿你在群里找到合适的kp或者pl哦～");
    /**
     * 普通鉴定大成功提示词
     */
    public static List<String>  strBigSuccess = Arrays.asList("(醉酒状态）哈？又拿酒来贿赂我？行吧行吧，酒放下，大成功快拿走。","历史无法改变，悔恨亦是毫无用处，请期待眼下和未来的故事吧，是大成功呢，希望能对你有所帮助","(愣住）这大概就是骰子女神的旨意吧，是个大成功呢","醉酒状态）什么！大成功！这一定是鹤丸那个老家伙动了我的骰子吧！","（警惕）刚刚是不是有白色的影子跑过去了……大成功，果然吗，鹤丸先生你怎么能被收买呢（恨铁不成钢）");
    /**
     * 普通鉴定极难成功提示词
     */
    public static List<String>  strBestDiffSuccess = Arrays.asList("(醉酒状态）切，极难成功，这下满意了吧！快走快走，别来烦我了。","不要那么着急，我曾经被烧过一次，但还是被救回来了，放心，你的运气也不会很差的，是极难成功哦","已经不会再逃避了，这次我一定会好好回应你的信赖，是极难成功，恭喜","不动行光！必将会为你带来胜利！是极难成功！","（醉酒状态）极难成功啊，啧，竟然不是大失败，没意思！");
     /**
     * 普通鉴定困难成功提示词
     */
    public static List<String>  strDiffSuccess = Arrays.asList("（醉酒状态）唔，困难成功啊，难得你这次运气还算不错呢，你猜下次是96？还是5？","（醉酒状态）困难成功？嘛，已经很不错了，对于我这种废刀，这已经是能做到的最好程度了……","是困难成功呢，结果还算不错，不是吗，下次会更好的。","（祈祷）不要失败不要失败不要失败……是，是困难成功！","虽然事情总是无法一帆风顺的，但现在还是打起精神来吧，是困难成功");
     /**
     * 普通鉴定普通成功提示词
     */
    public static List<String>  strSuccess = Arrays.asList("（醉酒状态）嗯，成功了……还不满意？那下次你就等着平地摔吧！","（醉酒状态）哈，你说什么骰子？真麻烦啊……唔，成功了","emmmmm，虽然没有特别好的事情发生，但起码成功了不是吗？开心点吧。","成功了！是好的开始呢，那么就不留遗憾的继续加油吧！","不动行光回应你的期待！成功了！（嘀咕）看来接下来我还要继续努力了呢");
    /**
     * 普通鉴定失败提示词
     */
    public static List<String>  strFail = Arrays.asList("（醉酒状态）失败了……才不是我喝多了手抖！是你脸黑啦！","(醉酒状态）意料之中的失败嘛～生气又怎么样？哼，我就是故意的","(自责）竟然失败了，都怪我……是我的错……","那场大火烧尽了我曾拥有过的一切，炽烈滚烫的温度，我不敢再回想……真的很抱歉，是失败了","刚刚太鼓钟那家伙偷偷动了我的骰子（怀疑打开看），果然是失败呢……这可不能怪我了");
    /**
     * 普通鉴定大失败提示词
     */
    public static List<String>  strBigFail = Arrays.asList("（醉酒状态）大失败了啊，什么嘛……你为什么非要对我这种废材抱有期待啊……","（醉酒状态）又是个大失败呢，呵，我不是从一开始就说过了吗？！我这种刀……只配得到这样的结果。","无论是谁的意志，这都不是你我所期望的结果（咬唇）是大失败了……","（担忧）我不是故意的！我真的没有想到会是这种结局！大失败了……我争取下次骰出大成功吧","（尬笑）嘛，这也不是什么新鲜事了不是吗？我上次也这样背刺了我的主人，这样说你会开心点吗？");

    /**
     * 理智鉴定失败提示词
     */
    public static List<String>  strScFail = Arrays.asList("有不妙的气息……","即使现在再怎么变强……呜呜……");


    /**
     * 理智鉴定大失败提示词
     */
    public static List<String>  strScBigFail = Arrays.asList("我果然是个没用的刀","一度得生者……岂有不灭乎……");

    /**
     * 理智鉴定失败且归0
     */
    public static List<String> strScFail0 = Arrays.asList("呜……怎么会……又得到了当初一样的结局吗","（醉酒状态）（传来酒杯碎裂的声音）给我坚强点啊！听见没……别走");

    /**
     * 理智鉴定失败且小于5
     */
    public static List<String> strScFail1 = Arrays.asList("（慌乱）再这样下去……该怎么办","（醉酒状态）担心什么？不是没有临时疯狂吗？");

    /**
     * 理智鉴定失败且大于5
     */
    public static List<String> strScFail6 = Arrays.asList("没办法了，临时疯狂时间我一定会努力骰小一点的！交给我吧！","（醉酒状态）（眼神落寞，独自喝酒不再言语）");

    /**
     * 理智鉴定成功提示词   SystemParam.strScSuccess.get(new Random().nextInt(SystemParam.strScSuccess.size())+1)
     */
    public static List<String> strScSuccess = Arrays.asList("别因为是没用的刀就小看我,.……嗝","给你个成功吧,不然会牵连到信长公的名声","（长吁一口气）还好还好，接下来请务必小心","（醉酒状态）嗯？要我说什么？希望你下次就ti？");

    /**
     * 理智鉴定大成功提示词
     */
    public static List<String> strScBigSuccess = Arrays.asList("看，这是我带回来的特产哦～","要是那时候，也能这样的话……");


    /**
     * st提示词
     */
    public static List<String> strStRet = Arrays.asList("等下等下！你说太快了！让我记本子上。","(醉酒状态）不要小看废材刀记忆力！");
    /**
     * Jrrp提示词
     */
    public static List<String> strStJrrp = Arrays.asList("你今天被队友大失败背刺的几率是%s，请务必小心","（醉酒状态）这种东西还要我来决定？啧，那么你今天被开局撕卡的概率是%s，满意了吗？");
    //%s的今日人品为%s,，话说这种东西也要我来决定吗？！
    /**
     * BotOn提示词
     */
    public static List<String> strStBotOn = Arrays.asList("不动行光，现在回到主人的身旁！什么，你问我是谁？太过分了……我只是酒醒了而已","既然你需要我的话，那么这次就由我来守护你","（醉酒状态）骰子？这种东西随便骰不就好了，瞎说，我清醒的很，才没醉");
   /**
     * BotOff提示词
     */
    public static List<String> strStBotOff = Arrays.asList("（失望）结束了吗？要不要再玩一会呢？真的不需要我了吗？好吧好吧，我会乖乖回去畑当番的！","（伸懒腰）终于可以休息了，接下来次郎的部屋里，喝上一杯吧，（委屈）别不相信我啊，真的只喝一杯","（醉酒状态）切，没意思，你们怎么这么顽强！不骰了，不骰了，一定是kp放水了！");
    /**
     * 太鼓钟，sada提示词
     */
    public static List<String> strTaiGu = Arrays.asList("他啊，是个喜欢华丽而且还很天真的人呢……与我不同，比起讨厌，或许我更羡慕他","嗯？你说太鼓钟？他又来乱动我的骰子了吗？！（炸毛）","（扶额）别想太多，我和他真的没什么关系，主人……主人的鬼话你不要相信，她吃cp的时候，脑子都不太清醒");
    /**
     * 织田信长，第六天魔王提示词
     */
    public static List<String> strXinChang = Arrays.asList("斯人已逝，纠结这些又有什么意义呢，我懊悔没能救下信长大人，但同样，我也不会忽略我现在的主人","如果你说的是骰娘nobu的话……我建议你问问她今天心情好不好，信长大人的性格，相信你懂的（吃瓜）","话说我现在的审神者大人也很喜欢自称魔王呢～只是我不会再让她步入那样的结局，以折断的代价起誓");
    /**
     * master提示词
     */
    public static List<String> strMaster = Arrays.asList("master八成是在窥频吧","在背后说坏话的话,我会提醒master的","消息已经转发给master");
    /**
     * 空晓提示词
     */
    public static List<String> strKongxiao = Arrays.asList("嗯，她是我的审神者大人呢，你找她有事吗？","如果你想找她，我建议你中午12点以后，不，不是赖床，只是她喜欢熬夜而已……（担忧）","主人她可能在窥屏哦，如果在谈论她坏话的问题，呵（拔刀）");
    /**
     * 审神者提示词
     */
    public static List<String> strShenShengzhe = Arrays.asList("嗯，空晓就是我的审神者大人呢，你找她有事吗？","如果你想找她(空晓大人)，我建议你中午12点以后，不，不是赖床，只是她喜欢熬夜而已……（担忧）","主人她可能在窥屏哦，如果在谈论她坏话的问题，呵（拔刀）");
     /**
     * 开团提示词
     */
    public static List<String> strKaiTuan = Arrays.asList("coc的至理名言，不自己开团就永远没有团","我猜，你手上还有一叠被鸽了一半的团（同情）");
     /**
     * 喝酒提示词
     */
    public static List<String> strHeJiu = Arrays.asList("怎么说我也是几百岁的刀了，喝点酒不是很正常吗？","以前确实有些过分了……但现在不会了，我会适量饮酒的","果然还是很想喝酒啊（小声），什么？什么喝酒！你听错了！");
     /**
     * 摸摸头提示词
     */
    public static List<String> strMoTou = Arrays.asList("（脸红）好吧，我会好好骰点的，但请不要摸我的头，我毕竟已经几百岁了","（难为情）就只能摸一次哦，就只给摸一次","（乖巧任摸）太鼓钟经过……（突然炸毛）没有！我只是没注意才让你摸的！（去追太鼓钟）");
     /**
     * 行光陪睡提示词
     */
    public static List<String> strXingGuangPeiShui = Arrays.asList("（义正言辞）绝对是不可以的，不管你是出于什么目的，我都不会同意的，再说刀本身就是冷的，不能暖床的！","请容许我拒绝，若是一定要逼迫我的话（行光拔出了本体刀），请带着我的碎片回去吧！","（醉酒状态）陪睡？陪睡是什么东西？新的酒名吗（打嗝），有点想吐……今天就不要了，你下次再给我送酒吧。","我早就看出你不是什么好人了！别过来，你难道想让我召唤雪小姐揍你吗？！","如果……如果是你的话，也是不可以的！放弃吧，不要在hentai的道路上执迷不悟","（醉酒状态）……\uD83D\uDCA4（因为行光睡死了，所以被你强行抱去陪睡！）第二天……（迷糊）可恶的heitai，受死吧！（您已去世，是否需要复活币）");
     /**
     * 行光陪睡提示词
     */
    public static List<String> strXingGuangPeiShui_KongXiao = Arrays.asList("虽然这不太合适，那么既然是主人的要求，今天就由我来替主人暖好床铺吧");
     /**
     * 废刀提示词
     */
    public static List<String> strFeiDao = Arrays.asList("这都是以前的事了，现在我有了更加需要保护的主人，自然不能做废刀");

    /**
     * 骰子列表
     */
    public static List<String> strTouzi = Arrays.asList
    ("2108509115","257905913","2557464559","1730707275","1924779949","272594304","-10006","1002239673","1003551725","1009004210","1009073767","1014687035","1067737677","1070337214","1094341393","1094397781","1098402607","111111111","1125703970","1132293713","1150518072","1173224481","1182093445","1183516314","1193172999","1207644216","1223609152","1241673337","1251027697","1257888369","1259215867","1261513582","1279209405","1289585882","1292347694","1301646413","1317994878","1350893385","1362833714","1413278583","1413872997","1425214612","1435785691","1447235071","1467889746","1468816177","1494428892","1498618989","1498701371","1521581844","1524056603","1538692373","1541024048","1550862100","1558903833","1571252777","1575806990","1606828690","1615414110","166105658","166245693","1665827253","1679551302","1684991148","1686430056","1708726701","1709402182","1715167950","1716487246","1730707275","1762721373","1811553512","1814681326","1824412825","1834187714","1835511389","1853487047","1872189184","1918014450","1920842614","192325152","1924779949","1938409597","1952006268","1954707378","1957431505","1963799211","1971015799","1976723548","1979497618","1981403877","1984749515","2011251530","2012269676","2037857625","203823027","2039176970","2040959528","2053422120","2056556046","2058167072","2074287783","2082591037","20842461","2085962228","2090736607","2092179626","2108509115","2112567012","2115901980","2118282942","212017923","2120245680","2121094458","2122340184","2122742403","2127197972","2129555744","2145068259","2146218418","2148284313","2148476315","2152464563","2155615370","2160469412","2166413681","2170873429","2172735734","2179958411","2180078287","2187138996","2194230148","2198224059","2198350949","2214777618","2235575724","2235794743","2237428859","2239123907","2241685763","2250070038","2251464299","2258654629","2263077716","2270116306","2286534811","2286934582","2306836742","2312872241","2313595751","2315809485","2316188562","2316399589","2320165121","2321685048","2323599890","2325935518","2354679802","2356892876","2358794905","2373962275","2375661057","2403263119","2422857121","2423564096","2451003279","2451846650","2453009600","2486809768","2488065551","2494913947","2518094992","2524240890","2543310051","2545357886","2546605826","256275558","2563688389","2568346257","256883325","2574266281","2580769314","2584260734","2608824476","2624354966","2632388974","2637191851","2637511917","2640830548","2642565436","264621986","2652934200","2657413103","2667629622","2671746009","2683135726","2685838995","2700724396","2702855101","2702952763","2704755534","2705704468","270845648","2717834039","2723695149","272594304","2727006546","2739412028","2745226339","2748509838","2751706510","2765949626","2772915385","2774866394","2783718152","2797910454","2798902752","2836109239","2839365510","2849340158","2849425320","2855170621","2855924189","2894764598","2911787783","2919506232","2928506579","2933932978","2942704230","2942743359","2953502535","2955819909","2966154043","2966346729","2985597315","2986977319","298888892","2993376038","3021095730","3029574939","3029761035","3043662308","304898615","3059748840","3067588848","3090452814","3098223931","3115521194","3122124917","3122725295","3139571135","3148293988","3155992630","3158757505","3179728160","3194319316","3197883109","3207184057","3213341631","3218829397","3225401310","3228368378","3238945562","3242381302","3251250241","3252156658","3254455632","3270826139","3272500990","3278069650","3278484191","3279107466","3283375066","3283944352","328706585","3291308149","3292481743","3296752811","3301057089","3307304834","3308200532","3325645792","3331059875","3341512755","3349553985","3354156257","3354915405","3358573033","3367529077","3367810057","3386812833","3399281790","3400386297","3402663250","3407146787","3407374610","3408808331","3410479711","3411518651","3412762519","3424912536","3439599339","3452740342","3460632628","3462715232","3479387293","3479766604","3481204677","3481983149","3492319893","3492858518","3497034293","3511875793","3520138440","3536144145","3572588700","3600240083","3607209890","3608479786","3609086298","3613097926","3613527865","3613736412","3616337885","3619921214","371265158","390778540","421770674","430418669","435539779","438742381","463477646","470894595","486258021","523902569","563629855","571843880","601015319","624764369","645655130","651326720","663983931","746216094","753507959","765104157","809998324","822523856","905109410","911887315","919007352","932557448","948223432","960179046","979719563","985570290");

    /**
     * 错误指令的回复
     */
    public static String strErrorCommand = "请好好输入属性,你可以发送help指令来查询我的具体功能菜单";

    /**
     * 错误指令的回复
     */
    public static List<String> strZaoAn = Arrays.asList("新的一天从大失败开始，嘿嘿，我是开玩笑啦，别紧张");

    /**
     * 错误指令的回复
     */
    public static List<String> strWanAn = Arrays.asList("这是禁止词哦，请谨慎考虑，（慌张）你说那次……那次跟我有什么吗？没有，肯定没有");
    /**
     * 错误指令的回复
     */
    public static List<String> strAnTou = Arrays.asList("聆听女神的旨意,希望是个好结果呢","(醉酒状态)别怀疑我,我就是在和Kp合谋暗杀你们");
    /**
     * 错误指令的回复
     */
    public static List<String> strKongXiao = Arrays.asList("虽然这不太合适，那么既然是主人的要求，今天就由我来替主人暖好床铺吧","主人不要急,master正在赶来的路上","主人,我向你推荐我的mater呢,他大概是这世界上最爱你的人吧,嗝~~","master已经到家门口了,去开门吧");
    /**
     * 行光W的回复
     */
    public static List<String> xingGuangW = Arrays.asList("不要叫啦,不要叫啦,耽误我喝酒,这就来啦","我已经是一个废刀了,你还来找我做什么,嗝~~","吞吞吐吐的样子,最讨厌了,你指望一把刀能猜出你的意思吗","又要作战了吗,我已经准备好了,绝不会重复当初的错误","有事等我回来说,我正要陪主人游遍你们这边的大好河山","又给我带了新的美酒了吗,你对我这么好,不会有什么企图吧","你先说什么事,我再说我醉没醉","要说信长公有多喜爱我，那是到了喝醉后就一边敲膝盖一边咏歌赞赏的程度。这可是，相当厉害吧？","……嗝。我是不动行光。织田信长公最为喜爱的刀！如何，认输了吗～！","不动行光,就是在下，九十九发，人中五郎左御座候","我是一把没能把被爱的份返还于主人的，没用的刀啊……","嗝……你的意思是没用的刀连被修理的资格都没有吗？","嘿嘿，你的意思是不能把战斗交给没用的刀吗？","我不会背叛主人的,你死心吧(一脸坚定)","今晚不行,我要陪主人逛街.","缺钱找我干啥,我这里只有酒","那家伙真好啊……毕竟还有主人爱着他啊！","我已经是个顶天立地的男子汉了,不会再让你受欺负的!","master说,我要保护好主人,每一分每一秒");




    /**
     * @date 2020/1/22 23:43
     * @author 陈瑞扬
     * @description
     * @param param
     * @return
     */
    public static String getRet(String param){
        List<String> list = new ArrayList<>();
        if ("strWelcome".equals(param)){
            list = SystemParam.strWelcome;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strBigSuccess".equals(param)){
            list = SystemParam.strBigSuccess;
        }
        else if ("strBestDiffSuccess".equals(param)){
            list = SystemParam.strBestDiffSuccess;
        }
        else if ("strDiffSuccess".equals(param)){
            list = SystemParam.strDiffSuccess;
        }
        else if ("strSuccess".equals(param)){
            list = SystemParam.strSuccess;
        }
        else if ("strFail".equals(param)){
            list = SystemParam.strFail;
        }
        else if ("strBigFail".equals(param)){
            list = SystemParam.strBigFail;
        }
        else if ("strScFail".equals(param)){
            list = SystemParam.strScFail;
        }
        else if ("strScBigFail".equals(param)){
            list = SystemParam.strScBigFail;
        }
        else if ("strScSuccess".equals(param)){
            list = SystemParam.strScSuccess;
        }
        else if ("strScBigSuccess".equals(param)){
            list = SystemParam.strScBigSuccess;
        }
        else if ("strStRet".equals(param)){
            list = SystemParam.strStRet;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strStJrrp".equals(param)){
            list = SystemParam.strStJrrp;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strStBotOn".equals(param)){
            list = SystemParam.strStBotOn;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strStBotOff".equals(param)){
            list = SystemParam.strStBotOff;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strTaiGu".equals(param)){
            list = SystemParam.strTaiGu;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strXinChang".equals(param)){
            list = SystemParam.strXinChang;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strMaster".equals(param)){
            list = SystemParam.strMaster;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strKongxiao".equals(param)){
            list = SystemParam.strKongxiao;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strShenShengzhe".equals(param)){
            list = SystemParam.strShenShengzhe;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strKaiTuan".equals(param)){
            list = SystemParam.strKaiTuan;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strHeJiu".equals(param)){
            list = SystemParam.strHeJiu;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strMoTou".equals(param)){
            list = SystemParam.strMoTou;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strXingGuangPeiShui_KongXiao".equals(param)){
            list = SystemParam.strXingGuangPeiShui_KongXiao;
            return list.get(new Random().nextInt(list.size()));
        }
        if ("strXingGuangPeiShui".equals(param)){
            list = SystemParam.strXingGuangPeiShui;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strFeiDao".equals(param)){
            list = SystemParam.strFeiDao;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strWanAn".equals(param)){
            list = SystemParam.strWanAn;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strZaoAn".equals(param)){
            list = SystemParam.strZaoAn;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strTouzi".equals(param)){
            list = SystemParam.strTouzi;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strAnTou".equals(param)){
            list = SystemParam.strAnTou;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strScFail0".equals(param)){
            list = SystemParam.strScFail0;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strScFail1".equals(param)){
            list = SystemParam.strScFail1;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strScFail6".equals(param)){
            list = SystemParam.strScFail6;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("xingGuangW".equals(param)){
            list = SystemParam.xingGuangW;
            return list.get(new Random().nextInt(list.size()));
        }
        else if ("strKongXiao".equals(param)){
            list = SystemParam.strKongXiao;
            return list.get(new Random().nextInt(list.size()));
        }

        int size = list.size();
        return  "\n"+list.get(new Random().nextInt(size));

    }


    public static String dealStrLevel(String strLevel){
        if("STR_EXTREME_SUCCESS".equals(strLevel)){
            return SystemParam.getRet("strBestDiffSuccess");
        }
        else if("STR_CRITICAL_SUCCESS".equals(strLevel)){
            return SystemParam.getRet("strBigSuccess");
        }
        else if("STR_HARD_SUCCESS".equals(strLevel)){
            return SystemParam.getRet("strDiffSuccess");
        }
        else if("STR_SUCCESS".equals(strLevel)){
            return SystemParam.getRet("strSuccess");
        }
        else if("STR_FAILURE".equals(strLevel)){
            return SystemParam.getRet("strFail");
        }
        else if("STR_FUMBLE".equals(strLevel)){
            return SystemParam.getRet("strBigFail");
        }else{
            return "0";
        }

    }







}
