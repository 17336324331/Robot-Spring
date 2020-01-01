package com.xingguang.service;

import org.springframework.stereotype.Service;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 16:58
 * @description
 */

public interface NameService {

    String getRandomName();

    void saveName();
}
