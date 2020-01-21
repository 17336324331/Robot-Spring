package com.xingguang.sinanya.dice.get;

import com.xingguang.sinanya.dice.get.imal.GetRandomList;
import com.xingguang.sinanya.dice.get.imal.MakeCard;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.NotEnableBySimpleException;
import com.xingguang.sinanya.exceptions.NotEnableInGroupException;
import com.xingguang.sinanya.system.MessagesBanList;
import com.xingguang.sinanya.system.MessagesNPC;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.tools.getinfo.GetName;
import com.xingguang.sinanya.tools.makedata.RandomInt;
import com.xingguang.sinanya.tools.makedata.Sender;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 生成NPC
 */
public class Npc implements MakeCard, GetRandomList {

    private EntityTypeMessages entityTypeMessages;
    private int trueAge;
    private boolean isMan = true;
    private Pattern sizPattern = Pattern.compile(".*体型:(\\d+).*");
    private Pattern powPattern = Pattern.compile(".*意志:(\\d+).*");
    private Pattern appPattern = Pattern.compile(".*魅力:(\\d+).*");

    public Npc(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * 生成NPC入口类，分别生成3批数据
     */
    public void npc() throws NotEnableInGroupException, NotEnableBySimpleException {
        checkEnable();
        forPc();
        state();
        prop();
    }

    /**
     * 生成外貌数据，发送到来源群中
     */
    private void forPc() {
        int ageLevel1 = 95;
        int ageLevel2 = 90;
        int ageLevel3 = 70;
        int ageLevel4 = 50;
        int ageLevel5 = 5;
        int ageLevel6 = 0;

        String tagMan = "男";

        StringBuilder stringBuilder = new StringBuilder("以下是另一种选择，如果你觉得给出的外表不够好或者没有外表给出，你可以给玩家放出以下内容:\n");
        String gender = randomFromList(MessagesNPC.Gender);
        int age = RandomInt.random(1, 100);
        String ageInfo = "";

        if (age > ageLevel1) {
            ageInfo = "70岁左右";
            trueAge = RandomInt.random(70, 80);
        } else if (age > ageLevel2) {
            ageInfo = "50-60岁上下";
            trueAge = RandomInt.random(45, 69);
        } else if (age > ageLevel3) {
            ageInfo = "40岁左右";
            trueAge = RandomInt.random(35, 45);
        } else if (age > ageLevel4) {
            ageInfo = "30岁上下";
            trueAge = RandomInt.random(25, 35);
        } else if (age > ageLevel5) {
            ageInfo = "20岁左右";
            trueAge = RandomInt.random(18, 25);
        } else if (age > ageLevel6) {
            ageInfo = "15岁上下";
            trueAge = RandomInt.random(10, 18);
        }

        String genderInfo;
        if (gender.equals(tagMan)) {
            genderInfo = "他";
        } else {
            genderInfo = "她";
            isMan = false;
        }

        String hairLeagth;

        if (gender.equals(tagMan)) {
            hairLeagth = randomFromList(MessagesNPC.HairLengthMan);
        } else {
            hairLeagth = randomFromList(MessagesNPC.HairLengthWomen);
        }

        stringBuilder.append("这是一名大约").append(ageInfo).append("的").append(randomFromListSmall(MessagesNPC.Shape)).append(gender).append("性\n");
        stringBuilder.append(genderInfo).append("有着一头");

        if (!hairLeagth.equals("光头")) {
            stringBuilder.append(randomFromList(MessagesNPC.HairColor)).append("的").append(hairLeagth);
        } else {
            stringBuilder.append(hairLeagth);
        }

        stringBuilder.append("并且有一双").append(randomFromList(MessagesNPC.EyeColor)).append("的").append(randomFromList(MessagesNPC.EyeShape))
                .append("\n而").append(genderInfo).append("的皮肤").append(randomFromListSmall(MessagesNPC.SkinColor))
                .append(",此外还穿着一身有些").append(randomFromListSmall(MessagesNPC.Clothing)).append("的衣服");

        entityTypeMessages.getMsgSender().SENDER.sendPrivateMsg(entityTypeMessages.getFromQqString(), stringBuilder.toString());
    }

    /**
     * 生成特点信息，私聊发送给命令触发者
     */
    private void state() {
        String stringBuilder = "以下是NPC的基本信息:\n" +
                "姓名:\t" +
                GetName.getRandomName() +
                "\n" +
                "年龄:\t" +
                trueAge +
                "\n" +
                "来自:\t" +
                randomFromList(MessagesNPC.strNational) +
                "\n" +
                "职业是:\t" +
                randomFromList(MessagesNPC.job) +
                "\n\n" +
                "三个可选特点分别是:" +
                "\n" +
                "1.\t" +
                randomFromList(MessagesNPC.TZ) +
                "\n" +
                "2.\t" +
                randomFromList(MessagesNPC.TZ) +
                "\n" +
                "3.\t" +
                randomFromList(MessagesNPC.TZ);
        entityTypeMessages.getMsgSender().SENDER.sendPrivateMsg(entityTypeMessages.getFromQqString(), stringBuilder);
    }

    /**
     * 生成属性信息，私聊发送给命令触发者
     * 这里特长技能定义为最低60最高80
     */
    private void prop() {
        String prop = getCoc7CardInfo().getInfo();
        String stringBuilder = "以下是NPC的性格可选信息:\n" +
                "实际性格:\t" +
                randomFromList(MessagesNPC.Character) +
                "\n" +
                "当前状态\t" +
                randomFromList(MessagesNPC.Manner) +
                "\n" +
                "特长技能:\t" +
                randomFromList(MessagesNPC.SpeTag) +
                "\n" +
                "此技能大约有" +
                RandomInt.random(60, 80) +
                "点之高" +
                "\n\n" +
                "其余属性为:\n\n" +
                prop;
        Matcher sizMatcher = sizPattern.matcher(prop);
        Matcher powMatcher = powPattern.matcher(prop);
        Matcher appMatcher = appPattern.matcher(prop);

        if (sizMatcher.find() && powMatcher.find() && appMatcher.find()) {
            int siz = Integer.parseInt(sizMatcher.group(1));
            int pow = Integer.parseInt(powMatcher.group(1));
            int app = Integer.parseInt(appMatcher.group(1));
            String desc = getDesc(isMan, trueAge, siz, pow, app);
            if (!desc.equals(MessagesSystem.NONE)) {
                stringBuilder = stringBuilder + "\n外貌可选描述为:\n" + desc;
                Sender.sender(entityTypeMessages, desc);
            }
            entityTypeMessages.getMsgSender().SENDER.sendPrivateMsg(entityTypeMessages.getFromQqString(), stringBuilder);
        }
    }

    private void checkEnable() throws NotEnableInGroupException, NotEnableBySimpleException {
        if (MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup()) && !MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isNpc()) {
            throw new NotEnableInGroupException(entityTypeMessages);
        }

        if (MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup()) && MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isSimple()) {
            throw new NotEnableBySimpleException(entityTypeMessages);
        }
    }

    private String getDesc(boolean isMan, int age, int siz, int pow, int app) {
        if (isMan) {
            if (age < 20) {
                return "男孩穿着一件和别的学生一样的米色双排扣呢子大衣，大衣敞着，露出里面的剪裁合身的白色衬衣。衣前懒散地系着一条黑色领带，领口处露出他微凸的锁骨和一小部分结实健朗的胸膛。";
            } else if (siz > 70) {
                return "两米高的大汉，长相倒是和演义的张飞有几分神似。眉毛杂乱，眼睛外突鼓起，脸颊外扩犹如刀割，盯着你们颇有几分不耐烦的样子。";
            } else if (pow < 40) {
                return "男子穿着的布鞋前段有牛皮云纹，脚底裹有层层软底橡胶，虽然脚步稳健，腰肢挺直，肩膀耸动摇摆自然，但他的神色间略微有些疲倦，似乎中年人最后的精气神都淡了，透露出年暮的衰败之气。";
            } else if (app > 60) {
                return "三十到四十左右的年纪，身材修长，穿着一件麻布上衣黑色裤子和千层底布鞋，成熟而不沧桑，声音穿透力十足，整个人看起来气质飘逸而随性。";
            } else if (app < 40 && age < 60) {
                return "破破烂烂的裤子，外套脱下来围在腰间，遮掩了屁股上的破洞，裸露着上身，可以看见粗糙的毛孔和坚硬的肌肉线条，皮肤是那种日积月累的辛劳沉淀的古铜肤色。";
            } else if (age > 80) {
                return "这个老头，头发零零碎碎的掉光了，满脸的老人斑，皱纹松松垮垮的，披着一件旧破的袄子，闭着眼睛一动不动，似乎人生中留给他剩下的时光，都打算用来这么躺着。";
            } else if (age > 60 && siz > 50) {
                return "一个戴着军绿色雷贝帽的中年男人，耳廓旁的头发有些银色，穿着一身休闲装和运动鞋，腋下夹着鳄鱼皮纹路的包，眉目间带着不加以掩饰的冷漠，给人疏远的感觉。";
            } else if (age > 60) {
                return "大灯泡的光线让影子格外分明，穿着小区老大爷标配的凉衫白色背心，配肥大的黑色麻布大裤衩，穿着很老小市场里才有卖的草藤凉鞋，漫不经心地拿着蒲扇打着哈欠。";
            } else if (age > 30 && age < 40 && siz > 60) {
                return "这个男人身高一米九八，犹如铁塔一般虎背熊腰，眼突睛露，眉毛杂乱，给人的感觉是那种性格暴躁，容易大打出手，不讲道理的莽夫。";
            } else if (siz > 50) {
                return "身高175左右，整个人显得比较瘦小，但很有力量感，穿着灰色的破旧西服，因为许久没修而有些错乱的胡须，偏瘦但有些肌肉的身材。";
            } else if (app > 40) {
                return "他的脸颊还有些浮肿，头发凌乱，穿着身得体的西服和擦得反光的皮鞋，手里倒提着一根棒球棍，眼睛盯着女生的胸部线条，嬉皮笑脸说着什么。";
            }
        } else {
            if (age > 70) {
                return "轮椅上一个老妇人戴着老花眼镜，拿着报纸的手没有女子的冰肌雪骨，苍老而遍布褶皱。所有的情绪和能够努力表达的神色仿佛都被她身上流逝的时光冻结，只剩下浑浊的瞳孔。";
            }

            if (app > 50) {
                if (age < 20 && siz < 40) {
                    ArrayList<String> result = new ArrayList<>();
                    result.add("一个十五六岁的少女，一头如墨的黑发散在身后，紫色的蕾丝线将一束小发悬在耳侧，红色的衬衣外是一件方格的蕾丝小礼服，白皙的手腕上悬着漂亮的镯子，说话声音甜清。");
                    result.add("水蜜桃般粉嘟嘟地脸，弯月眉，小挺秀气的鼻子，粉嫩粉嫩的玫瑰唇，淡粉色的长发如瀑布般披在身上，扎了一个大大的淡紫色的蝴蝶结，像极一个可爱的布娃娃。");
                    result.add("女孩脸上粘了好几粒西瓜子和瓤糊糊，西瓜被她用勺子挖的干干净净，弄成薄薄的一层，又找了一更长长的鸡公尾羽，插在西瓜底部，倒扣过来，成了个西瓜帽子戴在头上，学着解放军叔叔的姿势双手左摇右摆地走路。");
                    result.add("少女骑着单车，双脚踩着脚踏板，轻松地哼着像缠在风里的民谣，小腿显得格外修长，拉动着浑圆的大腿，肩膀耸动着，长发一摇一摆，两只比肤色稍粉的耳朵煞是可爱。");
                    result.add("少女脸颊上海残留着一点点婴儿肥的痕迹，有些可爱，更多的是少女牛奶一般的色泽和气息，耳廓精致无比，微颤的眼睫毛在光影交错的边沿干净清爽。");
                    result.add("阳光在她的背后，耳边的发丝在橙黄的光芒下一丝飘曳，柔美的脸颊上浮着浅浅的笑意，眼眸中有着调皮而伴生的得意，嘴边哼哼出轻轻的歌曲，仿佛可以看见音符掉落出来，随着她晃晃悠悠的美丽小腿一起摇动着。");
                    result.add("是少见的童颜美少女，浑身上下洋溢着青春活泼的气息，她穿着色调沉静而优雅的坡跟皮鞋，一双白色的长袜，长袜小腿侧面的位置镶嵌着美轮美奂的图案，上边点缀着一颗一颗闪闪发光的宝石。");
                    result.add("美丽的少女有着一头金发，阳光碎碎的落在她身上，金色的长发在金色的光芒下犹如流动着的金色液体，白皙的脸颊没有意思血色的通透，没有西方少女脸上常见的雀斑，那么干干净净的白皙。让人觉得轻轻触碰，就会伤害到她一样的脆弱。");
                    result.add("少女纤细的小腿紧绷着，小腿肚有着起伏的曲线，并没有一点松弛的迹象，膝盖往上的大腿圆润笔直，穿着运动短裤，有着这个年纪的女孩子最羡慕的身材。");
                    return randomFromList(result);
                } else if (siz < 40 && age < 30) {
                    ArrayList<String> result = new ArrayList<>();
                    result.add("女子端起茶碗，手指轻轻地磕了磕碗边，指尖圆润青葱，仿佛轻轻一捏，就会像煮熟烂的豆腐一样绵软,声音不那么清脆，顿时对这碗茶失去了兴趣似的放下，眉眼微敛，眼眸和嘴角显得狭长而妩媚。");
                    result.add("雪白肌肤丝缎般的华丽。眸子里是一望无际的苍蓝，属于最明媚的天空的颜色，闪着灼人的明亮。脸颊线条柔顺。漆黑的头发有着自然的起伏和弧度，散下来，令人百般想象指尖轻抚那些发丝的触感。");
                    result.add("着一身淡紫色旗袍，身上绣有小朵的淡粉色栀子花。头发随意的挽了一个松松的髻，斜插一只淡紫色簪花，显得几分随意却不失典雅。脸蛋娇媚如月，眼神顾盼生辉略施粉黛，朱唇不点及红。");
                    result.add("她说话时，发簪上的流苏就摇摇曳曳的，白白净净的脸庞，柔细的肌肤。眉毛修长如画，双眸闪烁如星。小小的鼻梁下有张小小的嘴，嘴唇薄薄的，嘴角微向上弯，带着点儿哀愁的笑意。");
                    result.add("眼前的女子，眉尾锋锐如矛，形容精致，坐在阳光与树荫之间，双手端正地放在小腹的位置，双腿斜斜地靠在一起，膝盖紧紧并拢，笔直的小腿没有慵散的曲线，依然有着如少女一般的笔直纤细。");
                    return randomFromList(result);
                } else if (age < 30) {
                    ArrayList<String> result = new ArrayList<>();
                    result.add("穿着白色的盘扣小褂，头发松散盘成发髻在脑后，修长的颈脖被领子围的严严实实，双臂犹如白藕，同样颜色高腰长裤让双腿更显修长，脚下搭配着一双平底跑鞋。");
                    result.add("高跟鞋的前端踩在灰白的道路标线上，鞋尖鱼口露出的几根脚指头素白而指甲红润，时不时地收缩着表露出她已经等待的内心有些烦躁，只是那黑色制服包裹的身材展露令人遐想的曲线。");
                    result.add("高高的个子似乎连影子都格外高挑一些，她穿着黑色的裙裤，腰间洗着玫红色的丝带，轻盈而火热的感觉，只是腰肢过于纤细，让人不禁担心能否承受得起她沉甸甸的上围。");
                    result.add("眉目含嗔带娇，神情容貌间有一股说不出的优雅与狐媚并存的味道，很快就收敛了笑声，眉角瞬间扬起，刚才那妩媚动人心魄的女子似乎一下被收进了皮肉里一般，露出锋锐的笑容，嘴角翘起一丝冷淡的笑意。");
                    result.add("少女的眼睛很大，仿佛猫儿一样略带着反光的感觉，让她的眼眸显得格外明亮而有神，浑圆而笔直的双腿靠拢在一起，白色的长袜在夜色中温润而瞩目，蓬松的裙摆仿佛被蝴蝶踩着的别离草花瓣一样摇曳。");
                    result.add("一袭略微紧身的黑衣将完美的身材展露无遗，亚麻色的头发漂亮得让人咋舌，长着一双清澈明亮，透着些许孩子气的眼睛、挺直的鼻梁、光滑的皮肤、薄薄的嘴唇呈现可爱的粉红色。");
                    result.add("长发披散，剪了蓬松的刘海，清清淡淡的感觉，眼眸眉脚常有一丝丝冷清和傲慢。穿着一套淡绿色的套裙，绿色的布结扣子清秀淡雅，轻如薄纱的裙面上染着一片片，一丛丛竹叶。");
                    result.add("穿着浅黑色的丝袜，梳了一个高高翘起的单马尾，发间用一个亮银色的金属发箍紧紧地束起来，格外干练而利爽，紧裹着身材的裙子让臀线显得丰满似。眉眼间却有些楚楚可怜，似乎感情或是身体，受到了一些折磨。");
                    result.add("眼前的少妇脸颊两旁垂着弯曲的发丝，略带几分妩媚，朴素的装扮透露着生活中历练出来的精明，穿着厨裙，带着老气的红色袖套，但是却是干净利落，身子骨纤柔却又藏着一份倔强，很显然是一个勤劳而独立的女人。");
                    result.add("少妇穿着一件白色的泡泡袖短上衣，弯腰走动间会露出短短的一截腰肢细腻的肌肤，下边是黑色的雪纺长裤，带着飘逸而闲暇的风格。蓬松而宽大的长裤，一定要有如此高挑的身材，才能穿着这样潇洒自如。");
                    result.add("身材高挑而性感，双腿尤其修长，没有穿丝袜，光洁的腿背在镂空的黑色高跟鞋里隐隐约约，有着男人最欣赏的胸部线条，脸上没有多少微笑，标准的秘书或者助理的风彩。");
                    result.add("女子身材高挑，黑色的长裤包裹着修长的双腿，勾勒出停止匀称的曲线，小西装的扣子散开，褶皱领的衬衣有些紧绷的感觉，似乎在表达着女子酥胸丰润爆满带来的无奈。");
                    result.add("带着无边框眼镜，小巧而美丽的脸蛋，深色却十分冷漠，一双镂雕精美的高跟鞋，黑色的丝袜质地细密而色泽深邃，搭配这修长笔直的小腿，高挑的身材包裹在合体的制服里，曲线圆润动人。");
                    result.add("身高168左右，有双澄澈到仿佛映出心灵的蓝色眼眸，肌肤细腻雪白，身形匀称没有赘肉，看起来有一股莫名的文雅恬静气质的女性 。手上有些部位有着老茧，似乎是从小练习小提琴和刀剑留下的痕迹，为人总是一副波澜不惊的表情，给人一种值得依靠的感觉 ");
                    result.add("身高为170，身材姣好匀称，端正的容姿，一头过肩的黑色长发，澄澈的蓝色眼眸。轻轻浮动的黑色长发，晶莹通透的细腻肌肤，明亮的大眼睛，薄薄的樱色嘴唇。 ");
                    result.add("长发盘层发髻悬在后脑，额头分成了两缕月牙似的发丝垂下，勾勒的冷淡脸庞多了几分妩媚，镶嵌着钻石的镜框悬在小巧的鼻梁上，唇色鲜艳，白色的A字短裙，加上成熟女性标配的丝网高跟鞋，整个人显得知性二冷艳。 ");
                    result.add("她打着花色绚烂的雨伞，穿着天青色的长裙，裙摆有些湿润，没有摇曳起来的风姿，紧贴着小腿，倒是显得身材更加高挑，步伐间小心翼翼地避开积水的扭动，风情款款，长发在身前也舞不动了，大概是空气中水分太多，压迫着那份飘逸。");
                    return randomFromList(result);
                }
            } else if (age > 50) {
                return "这个阿姨身形微胖，有着中年妇女常见的泼辣气质，手臂上戴着一个粗大的玉环，大脸盘子上的眼睛犹如黄豆，看着很不舒服。";
            }
        }
        return "";
    }


}
