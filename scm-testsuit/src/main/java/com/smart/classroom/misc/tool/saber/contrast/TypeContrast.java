package com.smart.classroom.misc.tool.saber.contrast;


import com.smart.classroom.misc.tool.saber.config.SaberConfig;
import com.smart.classroom.misc.utility.exception.UtilException;
import com.smart.classroom.misc.utility.util.StringUtil;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

/**
 * 类型对照
 * 主要参考 @java.sql.Types
 * 这里定义了常见的类型
 *
 * @author lishuang 2023-05-11
 */
public enum TypeContrast {

    VARCHAR("VARCHAR", "VARCHAR", String.class),
    LONGVARCHAR("LONGVARCHAR", "LONGVARCHAR", String.class),
    CHAR("CHAR", "CHAR", String.class),
    BLOB("BLOB", "BLOB", byte[].class),
    TEXT("TEXT", "LONGVARCHAR", String.class),
    TINYTEXT("TINYTEXT", "LONGVARCHAR", String.class),
    MEDIUMTEXT("MEDIUMTEXT", "LONGVARCHAR", String.class),
    LONGTEXT("LONGTEXT", "LONGVARCHAR", String.class),
    INT("INT", "INTEGER", Integer.class),
    INT_UNSIGNED("INT UNSIGNED", "INTEGER", Integer.class),
    INTEGER("INTEGER", "INTEGER", Integer.class),
    INTEGER_UNSIGNED("INTEGER", "INTEGER UNSIGNED", Integer.class),
    TINYINT("TINYINT", "INTEGER", Boolean.class),
    TINYINT_UNSIGNED("TINYINT UNSIGNED", "INTEGER", Boolean.class),
    SMALLINT("SMALLINT", "INTEGER", Integer.class),
    SMALLINT_UNSIGNED("SMALLINT UNSIGNED", "INTEGER", Integer.class),
    MEDIUMINT("MEDIUMINT", "INTEGER", Integer.class),
    MEDIUMINT_UNSIGNED("MEDIUMINT UNSIGNED", "INTEGER", Integer.class),
    BIT("BIT", "BOOLEAN", Boolean.class),
    BIGINT("BIGINT", "BIGINT", Long.class),
    BIGINT_UNSIGNED("BIGINT UNSIGNED", "BIGINT", Long.class),
    FLOAT("FLOAT", "FLOAT", Float.class),
    DOUBLE("DOUBLE", "DOUBLE", Double.class),
    DECIMAL("DECIMAL", "DECIMAL", BigDecimal.class),
    BOOLEAN("BOOLEAN", "BOOLEAN", Boolean.class),
    ID("PK (INTEGER UNSIGNED)", "INTEGER", Long.class),
    DATE("DATE", "DATE", Date.class),
    TIME("TIME", "TIME", Time.class),
    DATETIME("DATETIME", "TIMESTAMP", Date.class),
    TIMESTAMP("TIMESTAMP", "TIMESTAMP", Date.class),
    YEAR("YEAR", "DATE", java.sql.Date.class);

    @Getter
    private String sqlType;

    //提供给mybatis使用的类型
    @Getter
    private String jdbcType;

    @Getter
    private Class<?> javaClass;


    TypeContrast(String sqlType, String jdbcType, Class<?> javaClass) {

        this.sqlType = sqlType;
        this.jdbcType = jdbcType;
        this.javaClass = javaClass;
    }

    public static TypeContrast getBySqlType(String sqlType) {
        TypeContrast[] values = TypeContrast.values();
        for (TypeContrast value : values) {
            if (StringUtil.equalsIgnoreCase(value.getSqlType(), sqlType)) {
                return value;
            }
        }
        throw new UtilException("不支持类型{}，请在"+ SaberConfig.BASE_PACKAGE_NAME+".tool.saber.contrast.TypeContrast中进行补充", sqlType);
    }

    //和前端的对照关系
    public String getTsType() {
        if (this.getJavaClass() == Integer.class ||
                this.getJavaClass() == Long.class ||
                this.getJavaClass() == Float.class ||
                this.getJavaClass() == Double.class ||
                this.getJavaClass() == BigDecimal.class
        ) {
            return "number";
        } else if (this.getJavaClass() == Boolean.class) {
            return "boolean";
        } else if (this.getJavaClass() == Date.class ||
                this.getJavaClass() == Time.class ||
                this.getJavaClass() == java.sql.Date.class) {
            return "Date";
        } else {
            return "string";
        }
    }

    //和前端的对照关系
    public String getOdpsType() {
        if (this.getJavaClass() == Integer.class) {
            return "INT";
        } else if (this.getJavaClass() == Long.class) {
            return "BIGINT";
        } else if (this.getJavaClass() == Float.class) {
            return "FLOAT";
        } else if (this.getJavaClass() == Double.class) {
            return "DOUBLE";
        } else if (this.getJavaClass() == Boolean.class) {
            return "INT";
        } else if (this.getJavaClass() == java.sql.Date.class) {
            return "DATE";
        } else if (this.getJavaClass() == Date.class) {
            return "DATETIME";
        } else {
            return "STRING";
        }
    }

    //和前端的对照关系
    public String getSwiftType() {
        if (this.getJavaClass() == Integer.class || this.getJavaClass() == Long.class) {
            return "Int";
        } else if (this.getJavaClass() == Float.class) {
            return "Float";
        } else if (this.getJavaClass() == Double.class || this.getJavaClass() == BigDecimal.class) {
            return "Double";
        } else if (this.getJavaClass() == Boolean.class) {
            return "Bool";
        } else if (this.getJavaClass() == Date.class ||
                this.getJavaClass() == Time.class ||
                this.getJavaClass() == java.sql.Date.class) {
            return "Date";
        } else {
            return "String";
        }
    }

    //对于前端而言，是否为日期字段
    public boolean isTsDate() {
        return this.getJavaClass() == Date.class ||
                this.getJavaClass() == Time.class ||
                this.getJavaClass() == java.sql.Date.class;
    }




}
