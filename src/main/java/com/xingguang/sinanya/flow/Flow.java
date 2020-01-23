package com.xingguang.sinanya.flow;

import com.forte.qqrobot.beans.messages.result.GroupMemberInfo;
import com.xingguang.sinanya.dice.game.*;
import com.xingguang.sinanya.dice.get.*;
import com.xingguang.sinanya.dice.manager.*;
import com.xingguang.sinanya.dice.roll.*;
import com.xingguang.sinanya.dice.game.Name;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.*;
import com.xingguang.sinanya.system.MessagesBanList;
import com.xingguang.sinanya.system.MessagesFire;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.getinfo.BanList;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.getinfo.GetNickName;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.dice.MakeNickToSender;
import com.xingguang.sinanya.dice.getbook.Book;
import com.xingguang.sinanya.dice.getbook.CnMod;
import com.xingguang.sinanya.dice.system.Help;
import com.xingguang.sinanya.dice.system.History;
import com.xingguang.sinanya.dice.system.Log;
import com.xingguang.sinanya.dice.system.Man;
import org.docx4j.openpackaging.exceptions.Docx4JException;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static com.forte.qqrobot.beans.messages.types.MsgGetTypes.discussMsg;
import static com.forte.qqrobot.beans.messages.types.MsgGetTypes.privateMsg;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 入口分流类，结合MessagesTag中配置的正则表达式，将被机器人捕捉的小心分流给各个逻辑模块
 * <p>
 * 如果你要添加一个新的命令，需要先去MessagesTag处添加相应的正则表达式，然后在这里分别添加以下内容
 * 1.   isXXXX  布尔值
 * 2.   isXXXX=messages.matches(TAG_XXXXX); 为布尔值赋值
 * 3.   在toPrivate或toPrivateAndGroup中根据需要new自己写的逻辑类并通过布尔值去激活
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
public
class Flow implements MakeNickToSender {


    private EntityTypeMessages entityTypeMessages;

    private boolean isr = false;
    private boolean isRh = false;
    private boolean isRa = false;
    private boolean isRc = false;
    private boolean isRal = false;
    private boolean isRcl = false;
    private boolean isRav = false;
    private boolean isRcv = false;

    private boolean isRb = false;
    private boolean isRp = false;

    private boolean isSc = false;

    private boolean isEn = false;

    private boolean isSetRollMaxValue = false;

    private boolean isStSet = false;
    private boolean isStShow = false;
    private boolean isStList = false;
    private boolean isStMove = false;
    private boolean isStChange = false;
    private boolean isStClr = false;

    private boolean isTeamShow = false;
    private boolean isTeamSet = false;
    private boolean isTeamClr = false;
    private boolean isTeamMove = false;
    private boolean isTeamCall = false;
    private boolean isTeamHp = false;
    private boolean isTeamSan = false;
    private boolean isTeamDesc = false;
    private boolean isTeamEn = false;

    private boolean isHelpNormal = false;
    private boolean isHelpMake = false;
    private boolean isHelpGroup = false;
    private boolean isHelpBook = false;
    private boolean isHelpDnd = false;
    private boolean isHelpInfo = false;
    private boolean isHelpOhter = false;
    private boolean isHelpMaster = false;
    private boolean isHelpManager = false;
    private boolean isHelpGame = false;

    private boolean isMan = false;


    private boolean isBookCard = false;
    private boolean isBookRp = false;
    private boolean isBookKp = false;
    private boolean isBookMake = false;

    private boolean isNpc = false;

    private boolean isBg = false;

    private boolean isTz = false;

    private boolean isGas = false;

    private boolean isCoc7 = false;
    private boolean isCoc6 = false;
    private boolean isCoc7d = false;
    private boolean isCoc6d = false;
    private boolean isCoc7x = false;

    private boolean isLogOn = false;
    private boolean isLogOff = false;
    private boolean isLogGet = false;
    private boolean isLogList = false;
    private boolean isLogDel = false;

    private boolean isTi = false;
    private boolean isLi = false;

    private boolean isDnd = false;
    private boolean isRi = false;
    private boolean isInit = false;
    private boolean isInitRm = false;
    private boolean isInitSet = false;
    private boolean isInitClr = false;

    private boolean isClueSet = false;
    private boolean isClueShow = false;
    private boolean isClueRm = false;
    private boolean isClueClr = false;


    private boolean isBanUser = false;
    private boolean isBanGroup = false;
    private boolean isBanCheck = false;
    private boolean isRmBanUser = false;
    private boolean isRmBanGroup = false;
    private boolean isListBanUser = false;
    private boolean isListBanGroup = false;

    private boolean isWhiteUser = false;
    private boolean isWhiteGroup = false;
    private boolean isRmWhiteUser = false;
    private boolean isRmWhiteGroup = false;
    private boolean isListWhiteUser = false;
    private boolean isListWhiteGroup = false;

    private boolean isAdminOn = false;
    private boolean isAdminOff = false;
    private boolean isAdminExit = false;
    private boolean isAdminSearch = false;

    private boolean isName = false;
    private boolean isNameEn = false;
    private boolean isNameCh = false;
    private boolean isNameJp = false;

    private boolean isGroupJrrp = false;
    private boolean isGroupNpc = false;
    private boolean isGroupWelcome = false;
    private boolean isGroupGas = false;
    private boolean isGroupBg = false;
    private boolean isGroupTz = false;
    private boolean isGroupSimple = false;
    private boolean isGroupOb = false;
    private boolean isGroupInfo = false;
    private boolean isGroupDeck = false;
    private boolean isGroupList = false;
    private boolean isGroupSearch = false;
    private boolean isGroupClean = false;

    private boolean isDiceList = false;

    private boolean isKp = false;

    private boolean isHiy = false;

    private boolean isRules = false;

    private boolean isRule = false;

    private boolean isMagic5E = false;

    private boolean isMagic3R = false;

    private boolean isContition3R = false;

    private boolean isContition5E = false;

    private boolean isMonster3R = false;

    private boolean isMonster5E = false;

    private boolean isFeat5E = false;

    private boolean isFeat3R = false;

    private boolean isJrrp = false;

    private boolean isDeck = false;

    private boolean isDeckHelp = false;

    private boolean isObInput = false;
    private boolean isObExit = false;
    private boolean isObClr = false;
    private boolean isObList = false;

    private boolean isWw = false;
    private boolean isW = false;
    private boolean isWs = false;

    private boolean isFate = false;

    private boolean isFire = false;
    private boolean isFireStart = false;
    private boolean isFireStop = false;
    private boolean isFireRestart = false;

    private boolean isCnMods = false;

    private boolean isStName = false;


    public Flow(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
        checkMessages();
    }

    private void initObTag() {
        isObExit = checkTagRegex(MessagesTag.TAG_OB_EXIT);
        isObClr = checkTagRegex(MessagesTag.TAG_OB_CLR);
        isObList = checkTagRegex(MessagesTag.TAG_OB_LIST);
        isObInput = checkTagRegex(MessagesTag.HEADER_OB) && !isObExit && !isObClr && !isObList;
    }

    private void initGroupTag() {
        isGroupJrrp = checkTagRegex(MessagesTag.TAG_GROUP_JRRP);
        isGroupNpc = checkTagRegex(MessagesTag.TAG_GROUP_NPC);
        isGroupWelcome = checkTagRegex(MessagesTag.TAG_GROUP_WELCOME);
        isGroupGas = checkTagRegex(MessagesTag.TAG_GROUP_GAS);
        isGroupBg = checkTagRegex(MessagesTag.TAG_GROUP_BG);
        isGroupTz = checkTagRegex(MessagesTag.TAG_GROUP_TZ);
        isGroupSimple = checkTagRegex(MessagesTag.TAG_GROUP_SIMPLE);
        isGroupOb = checkTagRegex(MessagesTag.TAG_GROUP_OB);
        isGroupInfo = checkTagRegex(MessagesTag.TAG_GROUP_INFO);
        isGroupDeck = checkTagRegex(MessagesTag.TAG_GROUP_DECK);
        isGroupList = checkTagRegex(MessagesTag.TAG_GROUP_LIST);
        isGroupSearch = checkTagRegex(MessagesTag.TAG_GROUP_SEARCH);
        isGroupClean = checkTagRegex(MessagesTag.TAG_GROUP_CLEAN);
    }

    private void initTeamTag() {
        isTeamSet = checkTagRegex(MessagesTag.TAG_TEAM_SET);
        isTeamClr = checkTagRegex(MessagesTag.TAG_TEAM_CLR);
        isTeamMove = checkTagRegex(MessagesTag.TAG_TEAM_RM);
        isTeamCall = checkTagRegex(MessagesTag.TAG_TEAM_CALL);
        isTeamHp = checkTagRegex(MessagesTag.TAG_TEAM_HP);
        isTeamSan = checkTagRegex(MessagesTag.TAG_TEAM_SAN);
        isTeamDesc = checkTagRegex(MessagesTag.TAG_TEAM_DESC);
        isTeamEn = checkTagRegex(MessagesTag.TAG_TEAM_EN);
        isTeamShow = checkTagRegex(MessagesTag.TAG_TEAM_SHOW) && !isTeamSet && !isTeamClr && !isTeamMove && !isTeamCall && !isTeamHp && !isTeamSan && !isTeamDesc && !isTeamEn;
    }

    private void initStTag() {
        isStShow = checkTagRegex(MessagesTag.TAG_ST_SHOW);
        isStList = checkTagRegex(MessagesTag.TAG_ST_LIST);
        isStMove = checkTagRegex(MessagesTag.TAG_ST_RM);
        isStChange = checkTagRegex(MessagesTag.TAG__ST_CHANGE);
        isStClr=checkTagRegex(MessagesTag.TAG_ST_CLR);
        isStSet = checkTagRegex(MessagesTag.TAG_ST_SET) && !isStShow && !isStList && !isStMove && !isStChange &&!isStClr;
    }

    private void initHelpTag() {
        isHelpNormal = checkTagRegex(MessagesTag.TAG_HELP_NORMAL);
        isHelpMake = checkTagRegex(MessagesTag.TAG_HELP_MAKE);
        isHelpGroup = checkTagRegex(MessagesTag.TAG_HELP_GROUP);
        isHelpBook = checkTagRegex(MessagesTag.TAG_HELP_BOOK);
        isHelpDnd = checkTagRegex(MessagesTag.TAG_HELP_DND);
        isHelpOhter = checkTagRegex(MessagesTag.TAG_HELP_OTHER);
        isHelpMaster = checkTagRegex(MessagesTag.TAG_HELP_MASTER);
        isHelpManager = checkTagRegex(MessagesTag.TAG_HELP_MANAGER);
        isHelpGame = checkTagRegex(MessagesTag.TAG_HELP_GAME);
        isHelpInfo = checkTagRegex(MessagesTag.TAG_HELP_INFO) && !isHelpNormal && !isHelpMake && !isHelpGroup && !isHelpBook && !isHelpDnd && !isHelpOhter && !isHelpMaster && !isHelpManager && !isHelpGame;
    }

    private void initCocCardTag() {
        isCoc7d = checkTagRegex(MessagesTag.TAG_COC7D);
        isCoc7x = checkTagRegex(MessagesTag.TAG_COC7X);
        isCoc6d = checkTagRegex(MessagesTag.TAG_COC6D);
        isCoc6 = checkTagRegex(MessagesTag.TAG_COC6);
        isCoc7 = checkTagRegex(MessagesTag.TAG_COC7) && !isCoc7d && !isCoc7x && !isCoc6d && !isCoc6;
    }

    private void initDndTag() {
        isDnd = checkTagRegex(MessagesTag.TAG_DND);
        isInitClr = checkTagRegex(MessagesTag.TAG_INIT_CLR);
        isInitRm = checkTagRegex(MessagesTag.TAG_INIT_RM);
        isInitSet = checkTagRegex(MessagesTag.TAG_INIT_SET);
        isInit = checkTagRegex(MessagesTag.TAG_INIT) && !isInitClr && !isInitRm && !isInitSet;
    }

    private void initBookTag() {
        isBookCard = checkTagRegex(MessagesTag.TAG_BOOK_CARD);
        isBookMake = checkTagRegex(MessagesTag.TAG_BOOK_MAKE);
        isBookRp = checkTagRegex(MessagesTag.TAG_BOOK_RP);
        isBookKp = checkTagRegex(MessagesTag.TAG_BOOK_KP);
    }

    private void initLogTag() {
        isLogOn = checkTagRegex(MessagesTag.TAG_LOG_ON);
        isLogOff = checkTagRegex(MessagesTag.TAG_LOG_OFF);
        isLogGet = checkTagRegex(MessagesTag.TAG_LOG_GET);
        isLogList = checkTagRegex(MessagesTag.TAG_LOG_LIST);
        isLogDel = checkTagRegex(MessagesTag.TAG_LOG_RM);
    }

    private void initClueTag() {
        isClueClr = checkTagRegex(MessagesTag.TAG_CLUE_CLR);
        isClueShow = checkTagRegex(MessagesTag.TAG_CLUE_SHOW);
        isClueRm = checkTagRegex(MessagesTag.TAG_CLUE_RM);
        isClueSet = checkTagRegex(MessagesTag.TAG_CLUE_SET) && !isClueClr && !isClueShow && !isClueRm;
    }

    private void initDiceTag() {
        isRi = checkTagRegex(MessagesTag.TAG_RI);

        isRb = checkTagRegex(MessagesTag.TAG_RB);
        isRp = checkTagRegex(MessagesTag.TAG_RP);

        isRal = checkTagRegex(MessagesTag.TAG_RAL);
        isRcl = checkTagRegex(MessagesTag.TAG_RCL);
        isRav = checkTagRegex(MessagesTag.TAG_RAV);
        isRcv = checkTagRegex(MessagesTag.TAG_RCV);
        isRh = checkTagRegex(MessagesTag.TAG_RH);
        isRa = checkTagRegex(MessagesTag.TAG_RA) && !isRal && !isRav;
        isRc = checkTagRegex(MessagesTag.TAG_RC) && !isRcl && !isRcv;

        isRules = checkTagRegex(MessagesTag.TAG_RULES);

        isRule = checkTagRegex(MessagesTag.TAG_RULE);

        isFate = checkTagRegex(MessagesTag.TAG_FATE);

        isr = checkTagRegex(MessagesTag.TAGR) && !isRh && !isRa && !isRc && !isRb && !isRp && !isRi && !isRal && !isRcl && !isRav && !isRcv && !isRules && !isRule && !isFate;
    }

    private void banTag() {
        isRmBanUser = checkTagRegex(MessagesTag.TAG_RM_BAN_USER);
        isRmBanGroup = checkTagRegex(MessagesTag.TAG_RM_BAN_GROUP);
        isListBanUser = checkTagRegex(MessagesTag.TAG_BAN_USER_LIST);
        isListBanGroup = checkTagRegex(MessagesTag.TAG_BAN_GROUP_LIST);
        isBanUser = checkTagRegex(MessagesTag.TAG_BAN_USER) && !isListBanUser;
        isBanGroup = checkTagRegex(MessagesTag.TAG_BAN_GROUP) && !isListBanGroup;
        isBanCheck = checkTagRegex(MessagesTag.TAG_BAN_CHECK);
    }

    private void whiteTag() {
        isWhiteUser = checkTagRegex(MessagesTag.TAG_WHITE_USER);
        isWhiteGroup = checkTagRegex(MessagesTag.TAG_WHITE_GROUP);
        isRmWhiteUser = checkTagRegex(MessagesTag.TAG_RM_WHITE_USER);
        isRmWhiteGroup = checkTagRegex(MessagesTag.TAG_RM_WHITE_GROUP);
        isListWhiteUser = checkTagRegex(MessagesTag.TAG_WHITE_USER_LIST);
        isListWhiteGroup = checkTagRegex(MessagesTag.TAG_WHITE_GROUP_LIST);
    }

    private void adminTag() {
        isAdminOn = checkTagRegex(MessagesTag.TAG_ADMIN_ON);
        isAdminOff = checkTagRegex(MessagesTag.TAG_ADMIN_OFF);
        isAdminExit = checkTagRegex(MessagesTag.TAG_ADMIN_EXIT);
        isAdminSearch = checkTagRegex(MessagesTag.TAG_ADMIN_SEARCH);
    }

    private void nameTag() {
        isNameEn = checkTagRegex(MessagesTag.TAG_NAME_EN);
        isNameCh = checkTagRegex(MessagesTag.TAG_NAME_CH);
        isNameJp = checkTagRegex(MessagesTag.TAG_NAME_JP);
        isName = checkTagRegex(MessagesTag.TAG_NAME) && !isNameEn && !isNameCh && !isNameJp;
    }

    private void wodTag() {
        isWw = checkTagRegex(MessagesTag.TAG_WOD_WW);
        isWs = checkTagRegex(MessagesTag.TAG_WOD_WS);
        isW = checkTagRegex(MessagesTag.TAG_WOD_W) && !isWw && !isWs;
    }

    private void fireTag() {
        isFireStart = checkTagRegex(MessagesTag.TAG_FIRE_START);
        isFireStop = checkTagRegex(MessagesTag.TAG_FIRE_STOP);
        isFireRestart = checkTagRegex(MessagesTag.TAG_FIRE_RESTART);
        isFire = checkTagRegex(MessagesTag.HEADER_FIRE) && !isFireStart && !isFireStop && !isFireRestart;

    }

    private void checkMessages() {
        String forAll = ".*";
        if (checkTagRegex(MessagesTag.HEADER_TEAM + forAll)) {
            initTeamTag();
        } else if (checkTagRegex(MessagesTag.HEADER_ST + forAll)) {
            initStTag();
        } else if (checkTagRegex(MessagesTag.HEADER_HELP + forAll)) {
            initHelpTag();
        } else if (checkTagRegex(MessagesTag.HEADER_COC + forAll)) {
            initCocCardTag();
        } else if (checkTagRegex(MessagesTag.HEADER_DND + forAll) || checkTagRegex(MessagesTag.TAG_INIT)) {
            initDndTag();
        } else if (checkTagRegex(MessagesTag.HEADER_BOOK + forAll)) {
            initBookTag();
        } else if (checkTagRegex(MessagesTag.HEADER_LOG + forAll)) {
            initLogTag();
        } else if (checkTagRegex(MessagesTag.HEADER_CLUE + forAll)) {
            initClueTag();
        } else if (checkTagRegex(MessagesTag.HEADER_BAN + forAll)) {
            banTag();
        } else if (checkTagRegex(MessagesTag.HEADER_ADMIN + forAll)) {
            adminTag();
        } else if (checkTagRegex(MessagesTag.HEADER_NAME + forAll)) {
            nameTag();
        } else if (checkTagRegex(MessagesTag.HEADER_GROUP + forAll)) {
            initGroupTag();
        } else if (checkTagRegex(MessagesTag.HEADER_OB + forAll)) {
            initObTag();
        } else if (checkTagRegex(MessagesTag.HEADER_WHITE + forAll)) {
            whiteTag();
        } else if (checkTagRegex(MessagesTag.TAG_WOD_W) && !entityTypeMessages.getMsgGet().getMsg().contains("welcome")) {
            wodTag();
        } else if (checkTagRegex(MessagesTag.TAGR)) {
            initDiceTag();
        } else if (checkTagRegex(MessagesTag.HEADER_FIRE + forAll)) {
            fireTag();
        } else {
            isKp = checkTagRegex(MessagesTag.TAG_KP);
            isHiy = checkTagRegex(MessagesTag.TAG_HIY);

            isJrrp = checkTagRegex(MessagesTag.TAG_JRRP);

            isSc = checkTagRegex(MessagesTag.TAG_SC);
            isEn = checkTagRegex(MessagesTag.TAG_EN);
            isSetRollMaxValue = checkTagRegex(MessagesTag.TAG_SET_ROLL_MAX_VALUE);

            isNpc = checkTagRegex(MessagesTag.TAG_NPC);
            isBg = checkTagRegex(MessagesTag.TAG_BG);
            isTz = checkTagRegex(MessagesTag.TAG_TZ);
            isGas = checkTagRegex(MessagesTag.TAG_GAS);
            isTi = checkTagRegex(MessagesTag.TAG_TI);
            isLi = checkTagRegex(MessagesTag.TAG_LI);

            isRules = checkTagRegex(MessagesTag.TAG_RULES);

            isRule = checkTagRegex(MessagesTag.TAG_RULE);

            isMagic3R = checkTagRegex(MessagesTag.TAG_MAGIC_3R);

            isMagic5E = checkTagRegex(MessagesTag.TAG_MAGIC_5E) && !isMagic3R;

            isContition3R = checkTagRegex(MessagesTag.TAG_CONDITION_3r);

            isContition5E = checkTagRegex(MessagesTag.TAG_CONDITION_5e) && !isContition3R;

            isFeat3R = checkTagRegex(MessagesTag.TAG_FEAT_3R);

            isFeat5E = checkTagRegex(MessagesTag.TAG_FEAT_5E) && !isFeat3R;

            isMonster3R = checkTagRegex(MessagesTag.TAG_MONSTER_3r);
            isMonster5E = checkTagRegex(MessagesTag.TAG_MONSTER_5e);

            isDiceList = checkTagRegex(MessagesTag.TAG_DICE_LIST);

            isDeckHelp = checkTagRegex(MessagesTag.TAG_DECK_HELP);
            isDeck = checkTagRegex(MessagesTag.TAG_DECK) && !isDeckHelp;

            isMan = checkTagRegex(MessagesTag.TAG_MAN);

            isCnMods = checkTagRegex(MessagesTag.TAG_CNMODS);

            isStName = checkTagRegex(MessagesTag.TAG_ST_NAME);
        }

    }


    /**
     * 私聊逻辑，同时在私聊和群中生效
     */
    public void toPrivate() throws Exception {
        MakeDndCard makeDndCard = new MakeDndCard(entityTypeMessages);
        Bj bj = new Bj(entityTypeMessages);
        Npc npc = new Npc(entityTypeMessages);
        Tz tz = new Tz(entityTypeMessages);
        Gas gas = new Gas(entityTypeMessages);
        History history = new History(entityTypeMessages);
        Jrrp jrrp = new Jrrp(entityTypeMessages);
        /* Test test = new Test(entityTypeMessages); */
        Rules rules = new Rules(entityTypeMessages);
        Rule rule = new Rule(entityTypeMessages);
        Admin admin = new Admin(entityTypeMessages);
        DndMagic dndMagic = new DndMagic(entityTypeMessages);
        DndMonster dndMonster = new DndMonster(entityTypeMessages);
        DndCondition dndCondition = new DndCondition(entityTypeMessages);
        DndSpeciality dndSpeciality = new DndSpeciality(entityTypeMessages);
        Name name = new Name(entityTypeMessages);
        DiceList diceList = new DiceList(entityTypeMessages);
        Deck deck = new Deck(entityTypeMessages);
        Man man = new Man(entityTypeMessages);
        Fate fate = new Fate(entityTypeMessages);
        GroupManager groupManager = new GroupManager(entityTypeMessages);
        Fire fire = new Fire(entityTypeMessages);
        CnMod cnMod = new CnMod(entityTypeMessages);
        com.xingguang.sinanya.dice.manager.Name stName = new com.xingguang.sinanya.dice.manager.Name(entityTypeMessages);

        isWhiteFunction();
        if (!MessagesFire.fireSwitch.contains(entityTypeMessages.getFromQq()) || (MessagesFire.fireSwitch.contains(entityTypeMessages.getFromQq()) && entityTypeMessages.getMsgGetTypes() != privateMsg)) {
            isFunctionR();
        }
        isStFunction();
        isScFunction();
        isRbAndRpFunction();
        isCocCardFunction();
        isTiAndLiFunction();
        isHelpFunction();
        isBookFunction();
        isBanFunction();
        isWodFunction();

        if (entityTypeMessages.getMsgGetTypes() == privateMsg) {
            if (isFire) {
                fire.fire();
            } else if (isFireStart) {
                fire.start();
            } else if (isFireStop) {
                fire.stop();
            } else if (isFireRestart) {
                fire.restart();
            }

            if (MessagesFire.fireSwitch.contains(entityTypeMessages.getFromQq()) && !isFire && !isFireStart && !isFireStop && !isFireRestart && !entityTypeMessages.getMsgGet().getMsg().contains(".st")) {
                fire.check();
            }
        }

        if (isNpc) {
            npc.npc();
        }

        if (isBg) {
            bj.bg();

        }

        if (isTz) {
            tz.get();

        }

        if (isGas) {
            gas.get();

        }

        if (isDnd) {
            makeDndCard.dnd();
        }

        if (isHiy) {
            history.hiy();
        }


        if (isJrrp) {
            jrrp.get();
        }


        if (isRules) {
            rules.get();
        }

        if (isRule&&!isRules) {
            rule.get();
        }

//        if (isTest) {
//            test.get();
//        }

        if (isAdminOn) {
            admin.on();
        } else if (isAdminOff) {
            admin.off();
        } else if (isAdminExit) {
            admin.exit();
        } else if (isAdminSearch) {
            admin.search();
        }

        if (isMagic5E) {
            dndMagic.get();
        } else if (isMagic3R) {
            dndMagic.get3R();
        }

        if (isName) {
            name.random();
        } else if (isNameEn) {
            name.en();
        } else if (isNameCh) {
            name.ch();
        } else if (isNameJp) {
            name.jp();
        }

        if (isDiceList) {
            diceList.get();
        }


        if (isDeck) {
            deck.get();
        } else if (isDeckHelp) {
            deck.help();
        }

        if (isContition3R) {
            dndCondition.get3r();
        } else if (isContition5E) {
            dndCondition.get5e();
        }

        if (isFeat3R) {
            dndSpeciality.get3r();
        } else if (isFeat5E) {
            dndSpeciality.get5e();
        }

        if (isMonster5E) {
            dndMonster.get5e();
        } else if (isMonster3R) {
            dndMonster.get3r();
        }

        if (isMan) {
            man.man();
        }

        if (isFate) {
            fate.fate();
        }

        if (isGroupList) {
            groupManager.groupList();
        } else if (isGroupSearch) {
            groupManager.groupSearch();
        } else if (isGroupClean) {
            groupManager.groupClean();
        }

        if (isCnMods) {
            cnMod.get();
        }

        if (isStName) {
            stName.nn();
        }
    }


    /**
     * 群逻辑区块，所有在这里声明的逻辑都会在群、讨论组中生效。
     * 此外，虽然私聊的逻辑在群中生效，但这里设置的群逻辑并不会在私聊中生效
     */
    private void toPrivateAndGroup() throws Exception {
        Roll roll = new Roll(entityTypeMessages);
        SetRollMaxValue setRollMaxValue = new SetRollMaxValue(entityTypeMessages);
        Kp kp = new Kp(entityTypeMessages);
        Ob ob = new Ob(entityTypeMessages);


        isTeamFunction();
        isEnFunction();
        isLogFunction();
        isRiFunction();
        isClueFunction();

        if (isRh) {
            roll.rh();
        }

        if (isKp) {
            kp.set();
        }

        if (isSetRollMaxValue) {
            setRollMaxValue.set();
        }

        if (isObList) {
            ob.list();
        } else if (isObClr) {
            ob.clr();
        } else if (isObExit) {
            ob.exit();
        } else if (isObInput) {
            ob.input();
        }

        isGroupFunction();

        toPrivate();
    }

    /**
     * 将群分流到群逻辑区块
     */
    public void toGroup() throws Exception {
        toPrivateAndGroup();
        checkFre();
    }

    /**
     * 将讨论组分流到群逻辑区块
     */
    public void toDisGroup() throws Exception {
        toPrivateAndGroup();
        checkFre();
    }

    private void checkFre() {
        ArrayList<Long> timeList = new ArrayList<>();
        long now = System.currentTimeMillis();
        timeList.add(now);
        if (MessagesBanList.frequentnessForGroupList.containsKey(entityTypeMessages.getFromQqString())) {
            for (long time : MessagesBanList.frequentnessForGroupList.get(entityTypeMessages.getFromQqString())) {
                if (time > now - 1000 * 10) {
                    timeList.add(time);
                }
            }
        }
        if (timeList.size() >= GetMessagesProperties.entityBanProperties.getAlterFrequentness()) {
            GroupMemberInfo member = entityTypeMessages.getMsgSender().GETTER.getGroupMemberInfo(entityTypeMessages.getFromGroupString(), entityTypeMessages.getFromQqString());
            entityTypeMessages.getMsgSender().SENDER.sendGroupMsg(entityTypeMessages.getFromGroupString(), GetMessagesProperties.entityBanProperties.getFrequentnessAlterInfo());
            entityTypeMessages.getMsgSender().SENDER.sendGroupMsg(GetMessagesProperties.entityBanProperties.getManagerGroup(), "于" + makeGroupNickToSender(GetNickName.getGroupName(entityTypeMessages)) + entityTypeMessages.getFromGroup() + "中" + member.getNickName() + "(" + member.getQQ() + ")频度达到" + timeList.size() + "/10，警告");
        }
        if (timeList.size() >= GetMessagesProperties.entityBanProperties.getBanFrequentness()) {
            GroupMemberInfo member = entityTypeMessages.getMsgSender().GETTER.getGroupMemberInfo(entityTypeMessages.getFromGroupString(), entityTypeMessages.getFromQqString());
            entityTypeMessages.getMsgSender().SENDER.sendGroupMsg(entityTypeMessages.getFromGroupString(), GetMessagesProperties.entityBanProperties.getFrequentnessBanInfo());
            String text = "于" + makeGroupNickToSender(GetNickName.getGroupName(entityTypeMessages)) + entityTypeMessages.getFromGroup() + "中" + member.getNickName() + "(" + member.getQQ() + ")频度达到" + timeList.size() + "/10";

            if (GetMessagesProperties.entityBanProperties.isBanGroupAndUserByFre()) {
                BanList.insertQqBanList(entityTypeMessages.getFromQqString(), "在群" + entityTypeMessages.getFromGroup() + "中大量刷屏");
                BanList.insertGroupBanList(entityTypeMessages.getFromGroupString(), entityTypeMessages.getFromQq() + "在群中大量刷屏");
                if (entityTypeMessages.getMsgGetTypes()==discussMsg){
                    entityTypeMessages.getMsgSender().SETTER.setDiscussLeave(entityTypeMessages.getFromGroupString());
                }else {
                    entityTypeMessages.getMsgSender().SETTER.setGroupLeave(entityTypeMessages.getFromGroupString());
                }
                entityTypeMessages.getMsgSender().SENDER.sendGroupMsg(GetMessagesProperties.entityBanProperties.getManagerGroup(), text + "已退群并拉黑群和用户");
            }
            if (GetMessagesProperties.entityBanProperties.isBanUserByFre()) {
                BanList.insertQqBanList(entityTypeMessages.getFromQqString(), "在群" + entityTypeMessages.getFromGroup() + "中大量刷屏");
                entityTypeMessages.getMsgSender().SENDER.sendGroupMsg(GetMessagesProperties.entityBanProperties.getManagerGroup(), text + "已拉黑用户");
            }
        }
        MessagesBanList.frequentnessForGroupList.put(entityTypeMessages.getFromQqString(), timeList);
    }

    private boolean checkTagRegex(String tag) {
        Pattern pattern = Pattern.compile(tag, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        return pattern.matcher(entityTypeMessages.getMsgGet().getMsg().trim().toLowerCase()).find();
    }


    private void isFunctionR() throws ManyRollsTimesTooMoreException, RollCantInZeroException, NotSetKpGroupException, NotFoundSkillException {
        Roll roll = new Roll(entityTypeMessages);
        RollAndCheck rollAndCheck = new RollAndCheck(entityTypeMessages);
        if (isr) {
            roll.r();
        } else if (isRa) {
            rollAndCheck.ra();
        } else if (isRc) {
            rollAndCheck.rc();
        } else if (isRal) {
            rollAndCheck.ral();
        } else if (isRcl) {
            rollAndCheck.rcl();
        } else if (isRav) {
            rollAndCheck.rav();
        } else if (isRcv) {
            rollAndCheck.rcv();
        }

    }

    private void isStFunction() throws ManyRollsTimesTooMoreException, RollCantInZeroException {
        Roles roles = new Roles(entityTypeMessages);
        if (isStSet) {
            if (!roles.set()) {
                Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getSetHelp());
            }
        } else if (isStShow) {
            roles.show();
        } else if (isStList) {
            roles.list();
        } else if (isStMove) {
            roles.move();
        } else if (isStChange) {
            roles.change();
        }else if (isStClr){
            roles.clr();
        }
    }

    private void isScFunction() throws ManyRollsTimesTooMoreException, PlayerSetException, SanCheckSetException {
        SanCheck sanCheck = new SanCheck(entityTypeMessages);

        if (isSc) {
            sanCheck.sc();
        }
    }

    private void isRbAndRpFunction() throws ManyRollsTimesTooMoreException, RollCantInZeroException {
        RewardAndPunishment rewardAndPunishment = new RewardAndPunishment(entityTypeMessages);

        if (isRb) {
            rewardAndPunishment.rb();
        } else if (isRp) {
            rewardAndPunishment.rp();
        }
    }

    private void isCocCardFunction() throws CardTooMoreException {
        MakeCocCard makeCocCard = new MakeCocCard(entityTypeMessages);

        if (isCoc7d) {
            makeCocCard.coc7d();
        } else if (isCoc7) {
            makeCocCard.coc7();
        } else if (isCoc7x) {
            makeCocCard.coc7x();
        } else if (isCoc6d) {
            makeCocCard.coc6d();
        } else if (isCoc6) {
            makeCocCard.coc6();
        }
    }

    private void isTiAndLiFunction() {
        TiAndLi tiAndLi = new TiAndLi(entityTypeMessages);

        if (isTi) {
            tiAndLi.ti();
        } else if (isLi) {
            tiAndLi.li();
        }
    }

    private void isHelpFunction() {
        Help help = new Help(entityTypeMessages);

        if (isHelpNormal) {
            help.normal();
        } else if (isHelpMake) {
            help.make();
        } else if (isHelpGroup) {
            help.group();
        } else if (isHelpBook) {
            help.book();
        } else if (isHelpDnd) {
            help.dnd();
        } else if (isHelpInfo) {
            help.info();
        } else if (isHelpOhter) {
            help.other();
        } else if (isHelpMaster) {
            help.master();
        } else if (isHelpManager) {
            help.manager();
        } else if (isHelpGame) {
            help.game();
        }
    }

    private void isBookFunction() {
        Book book = new Book(entityTypeMessages);

        if (isBookKp) {
            book.kp();
        } else if (isBookCard) {
            book.card();
        } else if (isBookRp) {
            book.rp();
        } else if (isBookMake) {
            book.make();
        }
    }

    private void isTeamFunction() throws CantInPrivateException, PlayerSetException, ManyRollsTimesTooMoreException, RollCantInZeroException {
        Team team = new Team(entityTypeMessages);

        if (isTeamSet) {
            team.set();
        } else if (isTeamShow) {
            team.show();
        } else if (isTeamMove) {
            team.remove();
        } else if (isTeamClr) {
            team.clr();
        } else if (isTeamCall) {
            team.call();
        } else if (isTeamHp) {
            team.hp();
        } else if (isTeamSan) {
            team.san();
        } else if (isTeamDesc) {
            team.desc();
        } else if (isTeamEn) {
            team.en();
        }
    }

    private void isEnFunction() throws CantInPrivateException, NotFoundSkillException {
        SkillUp skillUp = new SkillUp(entityTypeMessages);

        if (isEn) {

            skillUp.en();

        }
    }

    private void isLogFunction() throws CantInPrivateException, Docx4JException, CantEmptyLogNameException {
        Log log = new Log(entityTypeMessages);

        if (isLogOn) {
            log.logOn();
        } else if (isLogOff) {
            log.logOff();
        } else if (isLogGet) {
            log.get();
        } else if (isLogList) {
            log.list();
        } else if (isLogDel) {
            log.del();
        }
    }

    private void isRiFunction() throws CantInPrivateException, InitSetFormatException {
        RiAndInit riAndInit = new RiAndInit(entityTypeMessages);

        if (isRi) {
            riAndInit.ri();
        } else if (isInit) {
            riAndInit.init();
        } else if (isInitRm) {
            riAndInit.rm();
        } else if (isInitSet) {
            riAndInit.set();
        } else if (isInitClr) {
            riAndInit.clr();
        }
    }

    private void isClueFunction() throws CantInPrivateException {
        Clue clue = new Clue(entityTypeMessages);

        if (isClueRm) {
            clue.rm();
        } else if (isClueSet) {
            clue.set();
        } else if (isClueClr) {
            clue.clr();
        } else if (isClueShow) {
            clue.show();
        }
    }

    private void isBanFunction() throws BanListInputNotIdException, NotEnableBanException {
        com.xingguang.sinanya.dice.manager.BanList banList = new com.xingguang.sinanya.dice.manager.BanList(entityTypeMessages);

        if (isBanUser) {
            banList.inputQqBanList();
        } else if (isBanGroup) {
            banList.inputGroupBanList();
        } else if (isRmBanUser) {
            banList.rmQqBanList();
        } else if (isRmBanGroup) {
            banList.rmGroupBanList();
        } else if (isListBanUser) {
            banList.getQqBanList();
        } else if (isListBanGroup) {
            banList.getGroupBanList();
        } else if (isBanCheck) {
            banList.isBan();
        }
    }

    private void isWhiteFunction() {
        WhiteList whiteList = new WhiteList(entityTypeMessages);

        if (isWhiteUser) {
            whiteList.inputQqWhiteList();
        } else if (isWhiteGroup) {
            whiteList.inputGroupWhiteList();
        } else if (isRmWhiteUser) {
            whiteList.rmQqWhiteList();
        } else if (isRmWhiteGroup) {
            whiteList.rmGroupWhiteList();
        } else if (isListWhiteUser) {
            whiteList.getQqWhiteList();
        } else if (isListWhiteGroup) {
            whiteList.getGroupWhiteList();
        }
    }

    private void isWodFunction() throws WodCheckMaxCantInAException, WodCheckNotIsOneException, WodToMoreException {
        Wod wod = new Wod(entityTypeMessages);
        if (isW) {
            wod.w();
        } else if (isWw) {
            wod.ww();
        } else if (isWs) {
            wod.ws();
        }
    }

    private void isGroupFunction() throws OnlyManagerException, CantInPrivateException {
        GroupManager groupManager = new GroupManager(entityTypeMessages);
        if (isGroupInfo) {
            groupManager.get();
        } else if (isGroupJrrp) {
            groupManager.jrrp();
        } else if (isGroupNpc) {
            groupManager.npc();
        } else if (isGroupWelcome) {
            groupManager.welcome();
        } else if (isGroupGas) {
            groupManager.gas();
        } else if (isGroupBg) {
            groupManager.bg();
        } else if (isGroupTz) {
            groupManager.tz();
        } else if (isGroupSimple) {
            groupManager.simple();
        } else if (isGroupOb) {
            groupManager.ob();
        } else if (isGroupDeck) {
            groupManager.deck();
        }
    }
}
