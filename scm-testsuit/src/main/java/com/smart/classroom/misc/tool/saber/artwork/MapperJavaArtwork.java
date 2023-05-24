package com.smart.classroom.misc.tool.saber.artwork;

import com.smart.classroom.misc.tool.saber.artwork.base.Artwork;
import com.smart.classroom.misc.tool.saber.command.SaberCommand;
import com.smart.classroom.misc.tool.saber.config.SaberConfig;
import com.smart.classroom.misc.tool.saber.meta.SaberTable;
import com.smart.classroom.misc.utility.util.PathUtil;
import com.smart.classroom.misc.utility.util.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * XXMapper.java
 * 负责生产模型Mapper的
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MapperJavaArtwork extends Artwork {
    /**
     * 表的主体名称，例如 scm_application_member 主体为application,主体会作为一层包名。
     * 默认：scm_后的第一个单词
     */
    private String bodyName;

    //表注释。
    private String tableRemark;

    //包名路径
    private String mapperPackage;

    //类名大写。
    private String entityUpperBodyName;

    /**
     * Mapper类名
     * 默认会使用表名的首字母大写驼峰写法+Mapper
     */
    private String mapperUpperClassName;



    //依赖的包。
    private List<String> mapperImportNones;

    //依赖的包。
    private List<String> mapperImportJavas;


    public MapperJavaArtwork() {
        super("TemplateMapperJava.vm", false);
    }

    @Override
    public String destAbsolutePath() {

        String directoryPath = PathUtil.getAppHomePath() + "/"+ SaberConfig.REPOSITORY_MODULE_NAME +"/src/main/java/" + this.mapperPackage.replaceAll("\\.", "/");

        //获取到所在文件夹的路径，如果没有自动创建。
        directoryPath = PathUtil.getSafeDirectoryPath(directoryPath);

        return directoryPath + "/" + this.entityUpperBodyName + "Mapper.java";
    }

    @Override
    public void init(@NonNull SaberTable saberTable, @NonNull SaberCommand saberCommand) {
        this.bodyName = saberCommand.getDirectFolderName();
        this.tableRemark = saberTable.getRemark();
        this.mapperPackage = SaberConfig.BASE_PACKAGE_NAME+".repository.mapper." + this.bodyName;
        this.entityUpperBodyName = saberCommand.getEntityUpperBodyName();

        //处理Mapper类需要引入的包
        this.mapperImportNones = new ArrayList<>();
        this.mapperImportNones.add(Mapper.class.getName());
        this.mapperImportNones.add(Repository.class.getName());

        this.mapperImportJavas = new ArrayList<String>() {{

        }};



        //进行排序
        this.mapperImportJavas.sort(StringUtil::compare);
        this.mapperImportNones.sort(StringUtil::compare);

    }
}
