package com.smart.classroom.misc.tool.saber.config;

/**
 * Saber工具中用到的一些配置项
 *
 * @author lishuang
 * @date 2023-05-11
 */
public interface SaberConfig {

    //仓储层的模块名
    String REPOSITORY_MODULE_NAME = "scm-repository";

    //基础包名，即所有类共同的包名前缀。
    String BASE_PACKAGE_NAME = "com.smart.classroom.misc";

    //数据库表前缀
    String DATABASE_TABLE_PREFIX = "scm_";

    //数据库连接串 useInformationSchema=true应该加上，否则获取不到表名注释。
    String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/smart_classroom?useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useInformationSchema=true";
    String JDBC_USERNAME = "smart_classroom";
    String JDBC_PASSWORD = "Smart_classroom123";

}
