package com.smart.classroom.misc.repository.config;


import com.smart.classroom.misc.utility.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;

/**
 * MyBatis拦截器打印不带问号的完整sql语句
 *
 * @author lishuang
 * @version 2023-05-13
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
// @SuppressWarnings({"unchecked", "rawtypes"})
public class MybatisInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object returnValue;
        try {
            final Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            Object parameter = null;
            if (invocation.getArgs().length > 1) {
                parameter = invocation.getArgs()[1];
            }
            String sqlId = ms.getId();
            BoundSql boundSql = ms.getBoundSql(parameter);
            Configuration configuration = ms.getConfiguration();
            String sql = getSql(configuration, boundSql, sqlId);
            log.info(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

        returnValue = invocation.proceed();
        return returnValue;
    }

    // 封装了一下sql语句，使得结果返回完整xml路径下的sql语句节点id + sql语句
    public static String getSql(Configuration configuration, BoundSql boundSql, String sqlId) {
        String sql = showSql(configuration, boundSql);
        StringBuilder str = new StringBuilder(100);
        str.append("\n\n============================ sql begin================================\n");
        str.append(sqlId);
        str.append("\n");
        str.append(sql);
        str.append("\n============================sql end================================");
        return str.toString();
    }

    // 如果参数是String，则添加单引号， 如果是日期，则转换为时间格式器并加单引号； 对参数是null和不是null的情况作了处理
    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            value = "'" + DateUtil.convertDateToString((Date) obj) + "'";
        } else {
            if (obj == null) {
                value = "";
            } else if (obj.getClass().isEnum()) {
                value = "'" + obj.toString() + "'";
            } else {
                value = obj.toString();
            }

        }
        return value;
    }

    // 进行？的替换
    public static String showSql(Configuration configuration, BoundSql boundSql) {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (CollectionUtils.isNotEmpty(parameterMappings) && parameterObject != null) {
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            // 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));
            } else {
                // MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,主要支持对JavaBean、Collection、Map三种类型对象的操作
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 该分支是动态sql
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else {
                        // 打印出缺失，提醒该参数缺失并防止错位
                        sql = sql.replaceFirst("\\?", "缺失");
                    }
                }
            }
        }
        return sql;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

}