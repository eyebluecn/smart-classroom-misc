package com.smart.classroom.misc.tool.saber.command;

import com.smart.classroom.misc.tool.saber.config.SaberConfig;
import com.smart.classroom.misc.tool.saber.contrast.TypeContrast;
import com.smart.classroom.misc.utility.exception.UtilException;
import com.smart.classroom.misc.utility.model.Pair;
import com.smart.classroom.misc.utility.util.StringUtil;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.*;


/**
 * 用户的一条指令，最终生成的文件会严格按照用户的指令来执行。
 *
 * @author lishuang 2023-05-11
 */
@Data
@Slf4j
public class SaberCommand {

    /**
     * 数据库表名.
     * 必须以 SaberConfig.DATABASE_TABLE_PREFIX 开头。
     */
    private String tableName;

    /**
     * 直接文件夹名称，例如 scm_application_member 主体为application,主体会作为一层包名。
     * 默认：scm_后的第一个单词
     */
    private String directFolderName;


    /**
     * 类名
     * 默认会使用表名的驼峰写法
     */
    private String entityUpperBodyName;

    /**
     * 定义用来排序的字段
     * 默认会添加
     * create_time(按创建时间排序)
     * update_time(按修改时间排序)
     */
    private List<String> filterOrderFields;

    /**
     * 定义用来做模糊筛选的字段
     */
    private List<String> filterLikeFields;


    /**
     * 定义用来做等号判断的字段
     */
    private List<String> filterEqualFields;


    /**
     * 对于一些字段，我们需要手动指定其JdbcType，典型的例子：数据库tinyint(4) 希望是 Boolean类型
     */
    private Map<String, TypeContrast> customFieldTypes;

    /**
     * 一些枚举字段的对照关系。优先级高于 customFieldTypes。
     * 字段名 : (枚举类型, 初始值)
     */
    private Map<String, Pair<Class<? extends Enum<?>>, String>> enumFieldMap;


    /**
     * 对于一些字段，数据库中虽然还留着，但是我们已经废弃了，不希望出现了。
     */
    private List<String> ignoreFields;


    public SaberCommand(@NonNull String tableName) {

        this.tableName = tableName;


    }


    /**
     * 对每个指令进行预处理
     */
    public void prepare() {

        if (StringUtil.isBlank(this.tableName)) {
            throw new UtilException("表名不能为空！");
        }

        if (!this.tableName.startsWith(SaberConfig.DATABASE_TABLE_PREFIX)) {
            throw new UtilException("表名{}不符合规范，必须以{}开头", tableName, SaberConfig.DATABASE_TABLE_PREFIX);
        }

        //获取表的主体名称。
        if (StringUtil.isBlank(this.directFolderName)) {
            String[] split = this.tableName.split("_");
            if (split.length == 0) {
                throw new UtilException("表名应该带有前缀");
            }
            this.directFolderName = split[1];
            if (StringUtil.isBlank(this.directFolderName)) {
                throw new UtilException("表名格式错误，无法提取到表名主体bodyName");
            }
        }


        //类名默认使用表名的驼峰形式。
        if (StringUtil.isBlank(this.entityUpperBodyName)) {
            String trimTableName = this.tableName.substring(SaberConfig.DATABASE_TABLE_PREFIX.length());
            this.entityUpperBodyName = StringUtil.underscoreToUpperCamel(trimTableName);
        }

        //筛选条件的处理。
        if (filterOrderFields == null) {
            filterOrderFields = new ArrayList<>();
        }

        //添加create_time,update_time排序
        List<String> tempFilterOrderFields = new ArrayList<>();
        if (!filterOrderFields.contains("create_time") && !filterOrderFields.contains("createTime")) {
            tempFilterOrderFields.add("create_time");
        }
        if (!filterOrderFields.contains("update_time") && !filterOrderFields.contains("updateTime")) {
            tempFilterOrderFields.add("update_time");
        }
        tempFilterOrderFields.addAll(filterOrderFields);
        this.filterOrderFields = tempFilterOrderFields;


        if (filterLikeFields == null) {
            filterLikeFields = new ArrayList<>();
        }


        if (filterEqualFields == null) {
            filterEqualFields = new ArrayList<>();
        }

        //对于一些字段，我们希望手动指定其类型
        if (customFieldTypes == null) {
            customFieldTypes = new HashMap<>();
        }

        //枚举类型的字段
        if (enumFieldMap == null) {
            enumFieldMap = new HashMap<>();
        }


        //一些数据库中虽然还有，但是我们已经废弃掉的字段
        if (ignoreFields == null) {
            ignoreFields = new ArrayList<>();
        }


    }

}
