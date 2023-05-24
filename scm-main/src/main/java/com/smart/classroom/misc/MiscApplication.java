package com.smart.classroom.misc;

import com.smart.classroom.misc.config.BootstrapConfiguration;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class MiscApplication {

    public static void main(String[] args) {
        //应用定义的启动配置
        BootstrapConfiguration.start();

        //spring-boot的启动入口
        SpringApplication.run(MiscApplication.class, args);
    }

}
