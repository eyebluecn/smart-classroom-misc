package com.smart.classroom.misc.tool.saber.artwork;

import com.smart.classroom.misc.repository.orm.base.BaseEntityDO;
import com.smart.classroom.misc.tool.saber.artwork.base.Artwork;
import com.smart.classroom.misc.tool.saber.command.SaberCommand;
import com.smart.classroom.misc.tool.saber.config.SaberConfig;
import com.smart.classroom.misc.tool.saber.meta.SaberColumn;
import com.smart.classroom.misc.tool.saber.meta.SaberTable;
import com.smart.classroom.misc.utility.util.PathUtil;
import com.smart.classroom.misc.utility.util.StringUtil;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * XX.java
 * 负责生产模型DO的
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DOArtwork extends Artwork {
    /**
     * 表的主体名称，例如 scm_application_member 主体为application,主体会作为一层包名。
     * 默认：scm_后的第一个单词
     */
    private String bodyName;

    //包名路径
    private String entityPackage;

    //依赖的包。
    private List<String> entityImportNones;

    //依赖的包。
    private List<String> entityImportJavas;

    //表注释。
    private String tableRemark;

    //主体大写。
    private String entityUpperBodyName;

    //类名大写。
    private String entityUpperClassName;

    //所有的字段。
    private List<SaberColumn> selfColumns;

    public DOArtwork() {
        super("TemplateDOJava.vm", true);
    }

    @Override
    public String destAbsolutePath() {

        String directoryPath = PathUtil.getAppHomePath() + "/"+ SaberConfig.REPOSITORY_MODULE_NAME +"/src/main/java/" + this.entityPackage.replaceAll("\\.", "/");

        //获取到所在文件夹的路径，如果没有自动创建。
        directoryPath = PathUtil.getSafeDirectoryPath(directoryPath);

        return directoryPath + "/" + this.entityUpperBodyName + "DO.java";
    }

    @Override
    public void init(@NonNull SaberTable saberTable, @NonNull SaberCommand saberCommand) {
        this.bodyName = saberCommand.getDirectFolderName();
        this.tableRemark = saberTable.getRemark();
        this.entityPackage = SaberConfig.BASE_PACKAGE_NAME+".repository.orm." + this.bodyName;
        this.entityUpperBodyName = saberCommand.getEntityUpperBodyName();
        this.entityUpperClassName = saberCommand.getEntityUpperBodyName()+"DO";

        this.selfColumns = new ArrayList<>();
        for (SaberColumn saberColumn : saberTable.getColumns()) {
            if (StringUtil.equals("id", saberColumn.getLowerCamelName()) ||
                    StringUtil.equals("createTime", saberColumn.getLowerCamelName()) ||
                    StringUtil.equals("updateTime", saberColumn.getLowerCamelName())
            ) {
                continue;
            }
            selfColumns.add(saberColumn);
        }

        //处理DO类需要引入的包
        this.entityImportNones = new ArrayList<>();
        this.entityImportNones.add(BaseEntityDO.class.getName());
        this.entityImportNones.add(Data.class.getName());
        this.entityImportNones.add(Builder.class.getName());
        this.entityImportNones.add(NoArgsConstructor.class.getName());
        this.entityImportNones.add(AllArgsConstructor.class.getName());
        this.entityImportNones.add(EqualsAndHashCode.class.getName());

        this.entityImportJavas = new ArrayList<>();

        this.selfColumns.forEach(saberColumn -> {

            String javaClassName = saberColumn.getJavaClassName();
            //如果实体和字段类型在同一个包中，那么就不需要添加到import中去。
            if (!(this.entityPackage + "." + saberColumn.getJavaSimpleName()).equals(javaClassName)) {

                //java.lang包下的默认不需要引入
                if (!StringUtil.startsWith(javaClassName, "java.lang")) {

                    if (StringUtil.startsWith(javaClassName, "java")) {

                        //不重复添加
                        if (!this.entityImportJavas.contains(javaClassName)) {
                            this.entityImportJavas.add(javaClassName);
                        }

                    } else {

                        //不重复添加
                        if (!this.entityImportNones.contains(javaClassName)) {
                            this.entityImportNones.add(javaClassName);
                        }

                    }
                }
            }

        });

        //进行排序
        this.entityImportJavas.sort(StringUtil::compare);
        this.entityImportNones.sort(StringUtil::compare);

    }
}
