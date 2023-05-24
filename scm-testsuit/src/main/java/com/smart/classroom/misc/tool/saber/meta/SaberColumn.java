package com.smart.classroom.misc.tool.saber.meta;


import com.smart.classroom.misc.repository.orm.base.BaseEntityDO;
import com.smart.classroom.misc.tool.saber.constants.SaberConstants;
import com.smart.classroom.misc.tool.saber.contrast.TypeContrast;
import com.smart.classroom.misc.utility.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * 一个字段信息
 *
 * @author lishuang 2023-05-11
 */
@Data
@NoArgsConstructor
@Slf4j
public class SaberColumn {

    //字段名 （英文名）下划线风格
    private String name;

    //字段注释（中文名）
    private String remark;

    //字段类型
    private String type;

    //是否为主键
    private boolean primaryKey;

    //字段名（英文名）驼峰法(首字母小写)
    private String lowerCamelName;

    //字段名（英文名）驼峰法(首字母小写)
    private String upperCamelName;


    //类型和java类型对照
    private TypeContrast typeContrast;

    //jdbc类型
    private String jdbcType;

    //java类
    private Class<?> javaClass;


    //java简单类型
    private String javaSimpleName;

    //java类全名
    private String javaClassName;

    //ts类型
    private String tsType;

    //swift类型
    private String swiftType;

    //odps类型
    private String odpsType;

    //该字段是否可以用作排序
    private boolean canSort = false;

    //如果需要默认值，那么默认值是多少。一个直接在.vm文件中输出的字符串。
    private String defaultValue = null;

    //ts的默认值
    private String tsDefaultValue = null;

    //swift的默认值
    private String swiftDefaultValue = null;

    //完全复制一个新对象
    public static SaberColumn clone(SaberColumn saberColumn) {

        SaberColumn column = new SaberColumn();
        column.name = saberColumn.name;
        column.remark = saberColumn.remark;
        column.type = saberColumn.type;
        column.primaryKey = saberColumn.primaryKey;
        column.lowerCamelName = saberColumn.lowerCamelName;
        column.upperCamelName = saberColumn.upperCamelName;
        column.typeContrast = saberColumn.typeContrast;
        column.jdbcType = saberColumn.jdbcType;
        column.javaClass = saberColumn.javaClass;
        column.javaSimpleName = saberColumn.javaSimpleName;
        column.javaClassName = saberColumn.javaClassName;
        column.tsType = saberColumn.tsType;
        column.odpsType = saberColumn.odpsType;
        column.canSort = saberColumn.canSort;
        column.swiftType = saberColumn.swiftType;
        return column;

    }

    public SaberColumn(String name, String remark, String type, String defaultValue) {
        this.name = name;
        this.remark = remark;
        this.type = type;

        this.lowerCamelName = StringUtil.underscoreToLowerCamel(this.name);
        this.upperCamelName = StringUtil.lowerCamelToUpperCamel(this.lowerCamelName);

        //如果字段名为id，那么我们认为是主键
        this.primaryKey = StringUtil.equalsIgnoreCase("id", name);

        this.updateTypeContrast(TypeContrast.getBySqlType(this.type), defaultValue);

    }


    //更新typeContrast
    public void updateTypeContrast(TypeContrast typeContrast, String defaultValue) {

        this.typeContrast = typeContrast;
        this.jdbcType = this.typeContrast.getJdbcType();

        this.javaClass = this.typeContrast.getJavaClass();
        this.javaSimpleName = this.javaClass.getSimpleName();
        this.javaClassName = this.javaClass.getName();

        if ("number".equals(this.typeContrast.getTsType()) || "boolean".equals(this.typeContrast.getTsType())) {
            this.tsType = this.typeContrast.getTsType();
        } else {
            this.tsType = this.typeContrast.getTsType() + " | null";
        }

        this.swiftType = this.typeContrast.getSwiftType();
        this.odpsType = this.typeContrast.getOdpsType();

        if (StringUtil.isBlank(defaultValue)) {
            this.defaultValue = "null";
            this.tsDefaultValue = "null";
            this.swiftType = this.swiftType.concat("?");
        } else {
            if (Long.class.getSimpleName().equals(this.javaSimpleName)) {
                this.defaultValue = defaultValue + "L";
                this.tsDefaultValue = defaultValue;
                this.swiftDefaultValue = defaultValue;
            } else if (Float.class.getSimpleName().equals(this.javaSimpleName)) {
                this.defaultValue = defaultValue + "f";
                this.tsDefaultValue = defaultValue;
                this.swiftDefaultValue = defaultValue;
            } else if (Boolean.class.getSimpleName().equals(this.javaSimpleName)) {
                if ("1".equals(defaultValue)) {
                    this.defaultValue = "true";
                    this.tsDefaultValue = "true";
                    this.swiftDefaultValue = "true";
                } else {
                    this.defaultValue = "false";
                    this.tsDefaultValue = "false";
                    this.swiftDefaultValue = "false";
                }
            } else if (String.class.getSimpleName().equals(this.javaSimpleName)) {
                if (BaseEntityDO.EMPTY_JSON_ARRAY.equals(defaultValue)) {
                    this.defaultValue = "BaseEntityDO.EMPTY_JSON_ARRAY";
                    this.tsType = "string[]";
                    this.tsDefaultValue = "[]";
                    this.swiftType = "[String]?";
                } else {
                    this.defaultValue = "\"" + defaultValue + "\"";
                    this.tsDefaultValue = "\"" + defaultValue + "\"";
                    this.swiftDefaultValue = "\"" + swiftDefaultValue + "\"";
                }

            } else {
                this.defaultValue = defaultValue;
                this.tsDefaultValue = defaultValue;
                this.swiftDefaultValue = defaultValue;
            }
        }


    }

    //更新枚举相关信息
    public void updateEnumField(Class<? extends Enum<?>> enumClass, String defaultValue) {

        this.javaSimpleName = enumClass.getSimpleName();
        this.javaClassName = enumClass.getName();
        this.tsType = enumClass.getSimpleName();
        this.swiftType = enumClass.getSimpleName();

        if (defaultValue == null) {
            this.defaultValue = "null";
            this.tsDefaultValue = "null";
            this.swiftType = this.swiftType.concat("?");
        } else {
            this.defaultValue = enumClass.getSimpleName() + "." + defaultValue;
            this.tsDefaultValue = enumClass.getSimpleName() + "." + defaultValue;
            this.swiftDefaultValue = "." + defaultValue;
        }


    }


    //两个字段宽松的相等 createTime和create_time和CreateTime是相等的
    public boolean sameField(@NonNull String field) {
        return StringUtil.equalsIgnoreCase(field.replaceAll("_", ""), this.getLowerCamelName());
    }

    //空判断语句。当这个字段用作where筛选的时候使用 TemplateMapperXml.vm
    public String nullTestString() {

        final Class<String> stringClass = String.class;
        if (stringClass.getName().equals(this.javaClassName)) {
            return StringUtil.format("{} != null and {} !=''", this.lowerCamelName, this.lowerCamelName);
        } else {
            return StringUtil.format("{} != null", this.lowerCamelName);
        }

    }

    //如果遇到mysql关键字，那么包上反引号。
    public String safeName() {
        boolean in = false;
        for (String keyword : SaberConstants.MYSQL_KEYWORDS) {
            if (StringUtil.equals(keyword, this.name)) {
                in = true;
            }
        }
        if (in) {
            return StringUtil.format("`{}`", this.name);
        } else {
            return this.name;
        }

    }

}
