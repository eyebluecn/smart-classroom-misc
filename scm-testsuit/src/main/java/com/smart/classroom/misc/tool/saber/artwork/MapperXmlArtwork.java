package com.smart.classroom.misc.tool.saber.artwork;

import com.smart.classroom.misc.tool.saber.artwork.base.Artwork;
import com.smart.classroom.misc.tool.saber.command.SaberCommand;
import com.smart.classroom.misc.tool.saber.config.SaberConfig;
import com.smart.classroom.misc.tool.saber.meta.SaberTable;
import com.smart.classroom.misc.utility.util.PathUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * XXMapper.xml
 * 负责生产模型Mapper的
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MapperXmlArtwork extends Artwork {
    /**
     * 表的主体名称，例如 scm_application_member 主体为application,主体会作为一层包名。
     * 默认：scm_后的第一个单词
     */
    private String bodyName;


    /**
     * 数据库表名
     */
    private String tableName;


    //表注释。
    private String tableRemark;

    //包名路径
    private String mapperPackage;

    //类名大写。
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
     * Mapper类名
     * 默认会使用表名的首字母大写驼峰写法+Mapper
     */
    private String mapperUpperClassName;
    /**
     * 实体全名
     * 包名+类名
     */
    private String mapperFullName;


    public MapperXmlArtwork() {
        super("TemplateMapperXml.vm", false);
    }

    @Override
    public String destAbsolutePath() {

        String directoryPath = PathUtil.getAppHomePath() + "/"+ SaberConfig.REPOSITORY_MODULE_NAME +"/src/main/resources/mybatis/mapper/" + this.bodyName;

        //获取到所在文件夹的路径，如果没有自动创建。
        directoryPath = PathUtil.getSafeDirectoryPath(directoryPath);

        return directoryPath + "/" + this.entityUpperBodyName + "Mapper.xml";
    }

    @Override
    public void init(@NonNull SaberTable saberTable, @NonNull SaberCommand saberCommand) {
        this.bodyName = saberCommand.getDirectFolderName();
        this.tableName = saberCommand.getTableName();
        this.tableRemark = saberTable.getRemark();
        this.mapperPackage = SaberConfig.BASE_PACKAGE_NAME+".repository.mapper." + this.bodyName;
        this.entityPackage = SaberConfig.BASE_PACKAGE_NAME + ".repository.orm."+ this.bodyName;
        this.entityUpperBodyName = saberCommand.getEntityUpperBodyName();
        this.entityFullName = this.entityPackage + "." + this.entityUpperBodyName+"DO";
        this.baseMapperClassFullName = this.mapperPackage + "." + this.entityUpperBodyName+"BaseMapper";

        this.mapperUpperClassName = this.entityUpperBodyName + "Mapper";
        this.mapperFullName = this.mapperPackage + "." + this.mapperUpperClassName;

    }
}
