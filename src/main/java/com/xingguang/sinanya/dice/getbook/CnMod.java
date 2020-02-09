package com.xingguang.sinanya.dice.getbook;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.CnModErrorException;
import com.xingguang.sinanya.exceptions.CnModNumberZeroException;
import com.xingguang.sinanya.exceptions.CnModPageNumTooBigException;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.checkdata.CheckIsNumbers;
import com.xingguang.sinanya.tools.makedata.Sender;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.nlpcn.commons.lang.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static com.xingguang.sinanya.db.system.SelectBot.selectCnModBase;

/**
 * @author SitaNya
 * @date 2019/12/5
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public class CnMod {
    private EntityTypeMessages entityTypeMessages;

    public CnMod(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    public void get() throws IOException, CnModErrorException, CnModNumberZeroException, CnModPageNumTooBigException {
        String tag = MessagesTag.TAG_CNMODS;
        StringBuilder msg = new StringBuilder(entityTypeMessages.getMsgGet().getMsg().trim().replaceFirst(tag.substring(0, tag.length() - 2), "").trim());
        int pageId = 1;
        String[] msgList = msg.toString().split(" ");
        if (msg.toString().contains(" ") && CheckIsNumbers.isNumeric(msgList[msgList.length - 1])) {
            pageId = Integer.parseInt(msgList[msgList.length - 1]);
            msg = new StringBuilder();
            for (int i = 0; i < msgList.length - 1; i++) {
                msg.append(msgList[i]);
            }
        }
        Sender.sender(entityTypeMessages, selectCnmod(msg.toString(), pageId));
    }

    private String selectCnmod(String key, int pageId) throws IOException, CnModErrorException, CnModNumberZeroException, CnModPageNumTooBigException {
        StringBuilder stringBuilder = new StringBuilder();
        JSONObject jsonObject = JSONObject.fromObject(getUrl(key, pageId));
        if (jsonObject.getJSONObject("data").getInt("totalPages") < pageId) {
            throw new CnModPageNumTooBigException(entityTypeMessages);
        }
        if (jsonObject.getInt("code") == 1) {
            int totalElements = jsonObject.getJSONObject("data").getInt("totalElements");
            switch (totalElements) {
                case 0:
                    throw new CnModNumberZeroException(entityTypeMessages);
                case 1:
                    stringBuilder.append("您搜索的关键词是: ").append(key).append(" 结果为:\n");
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data").getJSONArray("list").getJSONObject(0);
                    ArrayList<String> nameList = new ArrayList<>();
                    nameList.add("作者:\t" + jsonObject1.getString("article"));
                    nameList.add("作者QQ:\t" + new String(jsonObject1.getString("qq").getBytes(), StandardCharsets.UTF_8));
                    nameList.add("类型:\t" + new String(jsonObject1.getString("structure").getBytes(), StandardCharsets.UTF_8));
                    nameList.add("版本:\t" + new String(jsonObject1.getString("moduleVersion").getBytes(), StandardCharsets.UTF_8));
                    nameList.add("年代:\t" + new String(jsonObject1.getString("moduleAge").getBytes(), StandardCharsets.UTF_8));
                    nameList.add("地区:\t" + new String(jsonObject1.getString("occurrencePlace").getBytes(), StandardCharsets.UTF_8));
                    nameList.add("评论:\t" + new String(jsonObject1.getString("opinion").getBytes(), StandardCharsets.UTF_8));
                    nameList.add("下载链接:\thttps://www.cnmods.net/#/moduleDetail/index?keyId=" + jsonObject1.getInt("keyId"));
                    stringBuilder.append(StringUtil.joiner(nameList, "\n"));
                    break;
                default:
                    stringBuilder.append("您搜索的关键词不存在，可能是以下条目，本搜索结果共分为").append(jsonObject.getJSONObject("data").getInt("totalPages")).append("页(")
                            .append(jsonObject.getJSONObject("data").getInt("totalElements")).append("项)，当前显示第")
                            .append(pageId).append("页(每页10项):\n");
                    JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("list");
                    nameList = new ArrayList<>();
                    for (Object object : jsonArray) {
                        jsonObject1 = JSONObject.fromObject(object);
                        nameList.add("作品名:\t" + new String(jsonObject1.getString("title").getBytes(), StandardCharsets.UTF_8) + "\t作品链接:\thttps://www.cnmods.net/#/moduleDetail/index?keyId=" + jsonObject1.getInt("keyId") + "\t作者:\t" + new String(jsonObject1.getString("article").getBytes(), StandardCharsets.UTF_8));
                    }
                    stringBuilder.append(StringUtil.joiner(nameList, "\n"));
            }
        } else {
            throw new CnModNumberZeroException(entityTypeMessages);
        }
        return stringBuilder.toString();
    }

    private String getUrl(String val, int pageId) throws IOException {
        StringBuilder response = new StringBuilder();

        URL obj = new URL("https://www.cnmods.net/module/modulePageList.do?title=" + java.net.URLEncoder.encode(val, "utf-8") + "&page=" + pageId + "&pageSize=10");
        HttpURLConnection con;
        con = (HttpURLConnection) obj.openConnection();

        //默认值GET
        con.setRequestMethod("GET");

        //添加请求头
//            con.setRequestProperty("User-Agent", USER_AGENT);
//        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("authorization", selectCnModBase());

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
