package com.smart.classroom.misc.repository.config;

import com.github.pagehelper.PageInterceptor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 数据源配置 参考文档： https://www.cnblogs.com/SweetCode/p/15591792.html
 * @author lishuang
 * @date 2023-04-14
 */
@Configuration
@MapperScan(basePackages = {"com.smart.classroom.misc.repository"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfiguration {


    @Value("${spring.datasource.primary.jdbc-url}")
    private String primaryJdbcUrl;

    @Value("${spring.datasource.primary.username}")
    private String primaryUsername;

    @Value("${spring.datasource.primary.password}")
    private String primaryPassword;


    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() {

        //手动创建数据源
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(primaryJdbcUrl);
        config.setUsername(primaryUsername);
        config.setPassword(primaryPassword);

        config.setConnectionTimeout(60000);
        config.setValidationTimeout(3000);
        config.setIdleTimeout(60000);
        config.setMaxLifetime(500000);
        config.setMaximumPoolSize(100);
        config.setMinimumIdle(4);
        config.setReadOnly(false);
        config.setLeakDetectionThreshold(2000);
        config.setPoolName("HikariPool");


        return new HikariDataSource(config);
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mybatis/mapper/**/*.xml"));


        //添加Mybatis插件
        Interceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("offsetAsPageNum", "false");
        properties.setProperty("rowBoundsWithCount", "false");
        properties.setProperty("pageSizeZero", "false");
        properties.setProperty("reasonable", "false");
        properties.setProperty("returnPageInfo", "none");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("params", "pageNum=pageNum;pageSize=pageSize;");
        pageInterceptor.setProperties(properties);

        //开启日志的写法
        bean.setPlugins(pageInterceptor, new MybatisInterceptor());

        //关闭日志的写法
        //bean.setPlugins(pageInterceptor);


        return bean.getObject();
    }

    /**
     * 事务管理器
     */
    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 方便手动控制的事务模板
     */
    @Bean(name = "transactionTemplate")
    @Primary
    public TransactionTemplate transactionManager(@Qualifier("transactionManager") DataSourceTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
