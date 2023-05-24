package com.smart.classroom.misc.tool.saber.artwork;

import com.github.pagehelper.Page;
import com.smart.classroom.misc.tool.saber.artwork.base.Artwork;
import com.smart.classroom.misc.tool.saber.command.SaberCommand;
import com.smart.classroom.misc.tool.saber.config.SaberConfig;
import com.smart.classroom.misc.tool.saber.meta.SaberColumn;
import com.smart.classroom.misc.tool.saber.meta.SaberTable;
import com.smart.classroom.misc.utility.enums.SortDirection;
import com.smart.classroom.misc.utility.util.PathUtil;
import com.smart.classroom.misc.utility.util.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * XXBaseMapper.xml
 * 负责生产模型BaseMapper的
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseMapperXmlArtwork extends Artwork {
    /**
     * 表的主体名称，例如 scm_application_member 主体为application,主体会作为一层包名。
     * 默认：scm_后的第一个单词
     */
    private String directFolderName;


    /**
     * 数据库表名
     */
    private String tableName;


    //表注释。
    private String tableRemark;

    //包名路径
    private String mapperPackage;

    //类名大写。
    private String entityUpperClassName;

    //主体大写。
    private String entityUpperBodyName;

    /**
     * 类所在的包
     * 默认 SaberConfig.BASE_PACKAGE_NAME
     */
    private String entityPackage;

    /**
     * 实体全名
     * 包名+类名
     */
    private String entityFullName;


    /**
     * baseMapper全名
     */
    private String baseMapperClassFullName;


    /**
     * 所有的字段信息
     */
    private List<SaberColumn> columns;


    /**
     * 定义用来排序的字段
     * 默认会添加
     * create_time(按创建时间排序)
     * update_update(按修改时间排序)
     */
    private List<SaberColumn> filterOrderColumns;

    /**
     * 定义用来做模糊筛选的字段
     */
    private List<SaberColumn> filterLikeColumns;

    /**
     * 定义用来做等号判断的字段
     */
    private List<SaberColumn> filterEqualColumns;

    /**
     * 对筛选字段的一个汇总
     * 主要是为了便于在Mapper.java中生成条件。
     */
    private List<SaberColumn> filterSummaryColumns;


    //依赖的包。
    private List<String> mapperImportNones;

    //依赖的包。
    private List<String> mapperImportJavas;


    public BaseMapperXmlArtwork() {
        super("TemplateBaseMapperXml.vm", true);
    }

    @Override
    public String destAbsolutePath() {

        String directoryPath = PathUtil.getAppHomePath() + "/"+ SaberConfig.REPOSITORY_MODULE_NAME +"/src/main/resources/mybatis/mapper/" + this.directFolderName;

        //获取到所在文件夹的路径，如果没有自动创建。
        directoryPath = PathUtil.getSafeDirectoryPath(directoryPath);

        return directoryPath + "/" + this.entityUpperBodyName + "BaseMapper.xml";
    }

    @Override
    public void init(@NonNull SaberTable saberTable, @NonNull SaberCommand saberCommand) {
        this.directFolderName = saberCommand.getDirectFolderName();
        this.tableName = saberCommand.getTableName();
        this.tableRemark = saberTable.getRemark();
        this.mapperPackage = SaberConfig.BASE_PACKAGE_NAME+".repository.mapper." + this.directFolderName;
        this.entityPackage = SaberConfig.BASE_PACKAGE_NAME + ".repository.orm."+ this.directFolderName;
        this.entityUpperBodyName = saberCommand.getEntityUpperBodyName();
        this.entityUpperClassName = saberCommand.getEntityUpperBodyName()+"DO";
        this.entityFullName = this.entityPackage + "." + this.entityUpperClassName;
        this.baseMapperClassFullName = this.mapperPackage + "." + this.entityUpperBodyName+"BaseMapper";

        this.columns = saberTable.getColumns();

        //处理分页查询时候的筛选条件
        this.filterOrderColumns = saberTable.fetchSaberColumns(saberCommand.getFilterOrderFields());
        //这里要新建对象，否则会导致equal的也出错。
        if (CollectionUtils.isNotEmpty(this.filterOrderColumns)) {
            this.filterOrderColumns = this.filterOrderColumns.stream().map(SaberColumn::clone).collect(Collectors.toList());
        }
        //标记其为可分类
        this.filterOrderColumns.forEach(column -> column.setCanSort(true));


        this.filterLikeColumns = saberTable.fetchSaberColumns(saberCommand.getFilterLikeFields());
        this.filterEqualColumns = saberTable.fetchSaberColumns(saberCommand.getFilterEqualFields());
        this.filterSummaryColumns = new ArrayList<>();
        this.filterSummaryColumns.addAll(this.filterOrderColumns);
        this.filterSummaryColumns.addAll(this.filterLikeColumns);
        this.filterSummaryColumns.addAll(this.filterEqualColumns);


        //处理Mapper类需要引入的包
        this.mapperImportNones = new ArrayList<>();
        this.mapperImportNones.add(SortDirection.class.getName());
        this.mapperImportNones.add(SaberConfig.BASE_PACKAGE_NAME+".repository.orm.base.BaseMapper");
        this.mapperImportNones.add(Page.class.getName());
        this.mapperImportNones.add(Mapper.class.getName());
        this.mapperImportNones.add(Repository.class.getName());
        this.mapperImportNones.add(Param.class.getName());

        this.mapperImportJavas = new ArrayList<String>() {{

        }};
        this.filterSummaryColumns.forEach(saberColumn -> {
            if (!saberColumn.isCanSort()) {
                String javaClassName = saberColumn.getJavaClassName();

                //java.lang包下的默认不需要引入
                if (!StringUtil.startsWith(javaClassName, "java.lang")) {

                    if (StringUtil.startsWith(javaClassName, "java")) {

                        //不重复添加
                        if (!this.mapperImportJavas.contains(javaClassName)) {
                            this.mapperImportJavas.add(javaClassName);
                        }

                    } else {

                        //不重复添加
                        if (!this.mapperImportNones.contains(javaClassName)) {
                            this.mapperImportNones.add(javaClassName);
                        }

                    }
                }
            }


        });

        //进行排序
        this.mapperImportJavas.sort(StringUtil::compare);
        this.mapperImportNones.sort(StringUtil::compare);

    }
}
