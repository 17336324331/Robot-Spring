package com.xingguang.sinanya.tools.log;

import com.xingguang.sinanya.entity.EntityLogText;
import com.xingguang.sinanya.system.MessagesRgb;
import com.xingguang.sinanya.tools.makedata.RandomInt;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import static com.xingguang.sinanya.tools.getinfo.GetMessagesProperties.entitySystemProperties;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 保存染色文件
 */
public class SaveDocx implements Callable<Boolean> {
    private static final Logger log = LoggerFactory.getLogger(SaveDocx.class.getName());


    String groupId;
    String msg;
    ArrayList<EntityLogText> bigResult;
    private ObjectFactory factory;
    private MainDocumentPart documentPart;

    /**
     * 将输入的内容保存到docx文件
     *
     * @param groupId   群号
     * @param msg       日志名
     * @param bigResult 所有日志内容
     * @throws Docx4JException 可能报出docx错误
     */
    public SaveDocx(String groupId, String msg, final ArrayList<EntityLogText> bigResult) throws Docx4JException {
        this.groupId = groupId;
        this.msg = msg;
        this.bigResult = bigResult;
    }

    @Override
    public Boolean call() throws Exception {
        WordprocessingMLPackage wordMlPackage = WordprocessingMLPackage.createPackage();
        factory = Context.getWmlObjectFactory();
        documentPart = wordMlPackage.getMainDocumentPart();
        HashMap<String, Integer> colorTagExist = new HashMap<>();
        ArrayList<Integer> colorTagTmp = new ArrayList<>();
        int colorTag;
        for (EntityLogText entityLogText : bigResult) {
            switch (entityLogText.getLogType()) {
                case SPEAK:
                    if (colorTagExist.containsKey(entityLogText.getNick())) {
                        colorTag = colorTagExist.get(entityLogText.getNick());
                    } else {
                        int nickTag = (entityLogText.getNick().hashCode() + RandomInt.random(1, 100)) % 10;
                        while (colorTagTmp.contains(nickTag)) {
                            nickTag = (entityLogText.getNick().hashCode() + RandomInt.random(1, 100)) % 10;
                        }
                        colorTagExist.put(entityLogText.getNick(), nickTag);
                        colorTagTmp.add(nickTag);
                        colorTag = nickTag;
                    }
                    break;
                case HIDE:
                    colorTag = 11;
                    break;
                case DICE:
                    colorTag = 11;
                    break;
                default:
                    colorTag = 12;
            }
            makePdf(entityLogText.getText(), MessagesRgb.RGB_LIST.get(colorTag));
        }
        File file = new File(entitySystemProperties.getSystemDir() + "/saveLogs/" + groupId + "/" + msg + ".docx");
        if (!file.getParentFile().mkdirs() || !file.getParentFile().exists()) {
            log.error("日志目录错误");
        }
        wordMlPackage.save(file);
        return Boolean.TRUE;
    }

    /**
     * 将单条对象染色后添加到文档对象
     *
     * @param inputText  输入单条
     * @param inputColor 输入颜色
     */
    private void makePdf(String inputText, String inputColor) {
        // word对象
        documentPart.addObject(createTitle(inputText, inputColor));
    }

    /**
     * 根据输入值和单条颜色生成单条对象
     *
     * @param inputText  输入信息
     * @param inputColor 染色信息
     * @return 输入返回值
     */
    private P createTitle(String inputText, String inputColor) {
        RPr rpr = factory.createRPr();
        RFonts font = new RFonts();

        //设置字体
        font.setAscii("宋体");
        font.setEastAsia("宋体");
        //经测试发现这个设置生效
        rpr.setRFonts(font);

        //设置颜色
        Color color = new Color();
        color.setVal(inputColor);
        rpr.setColor(color);

        //设置字体大小
        HpsMeasure fontSize = new HpsMeasure();
        fontSize.setVal(BigInteger.valueOf(30));
        rpr.setSzCs(fontSize);
        rpr.setSz(fontSize);

        Text text = factory.createText();
        text.setValue(inputText);
        R r = factory.createR();
        r.getContent().add(text);
        r.setRPr(rpr);

        P p = factory.createP();
        //设置段落居中
        PPr ppr = new PPr();
        Jc jc = new Jc();
        jc.setVal(JcEnumeration.LEFT);
        ppr.setJc(jc);
        p.setPPr(ppr);
        p.getContent().add(r);

        return p;
    }

}
