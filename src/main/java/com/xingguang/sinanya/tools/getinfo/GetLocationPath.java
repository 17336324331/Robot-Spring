package com.xingguang.sinanya.tools.getinfo;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * @author SitaNya
 * @date 2019/12/16
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public class GetLocationPath {
    /**
     * 通过系统环境，获取当前jar包所在目录
     *
     * @return 返回当前jar包所在目录
     */
    public String getLocationPath() {
        File path;
        String stringPath = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
            stringPath = java.net.URLDecoder.decode(path.getAbsolutePath(), "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        assert stringPath != null;
        stringPath = stringPath.replaceFirst("[a-zA-z]*.jar", "");
        return stringPath;
    }
}
