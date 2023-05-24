package com.smart.classroom.misc.config;

import com.smart.classroom.misc.utility.util.StringUtil;

/**
 * @author lishuang
 * @date 2023-05-16
 * <p>
 * 启动配置
 */
public class BootstrapConfiguration {

    public static void start() {

        //配置日志路径。
        String logPath = StringUtil.format("{}/logs/smart-classroom-misc",
                System.getProperty("user.home"));
        System.setProperty("log.path", logPath);
        System.out.println("日志地址：" + logPath);

    }

}
