package com.xingguang.sinanya.monitor;

import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;

import static com.xingguang.sinanya.tools.getinfo.GetMessagesProperties.entityBanProperties;

/**
 * @author SitaNya
 * 日期: 2019-07-12
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class Prometheus {
    private int port;

    public Prometheus() {
    }

    public void start() {
        Logger log = LoggerFactory.getLogger(com.xingguang.sinanya.listener.Prometheus.class.getName());
        try {
            HTTPServer server = new HTTPServer(entityBanProperties.getPrometheusPort());
            log.info("Prometheus监控系统已在本机" + server.getPort() + "端口启动");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        DefaultExports.initialize();
    }
}
