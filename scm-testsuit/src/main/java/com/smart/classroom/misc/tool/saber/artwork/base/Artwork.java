package com.smart.classroom.misc.tool.saber.artwork.base;

import com.smart.classroom.misc.tool.saber.command.SaberCommand;
import com.smart.classroom.misc.tool.saber.meta.SaberTable;
import lombok.Data;
import lombok.NonNull;

/**
 * 一个产出物，一件艺术品。
 * 比如 TemplateMapperJava.vm 是模板，对应的产出物是 XXMapper.java
 */
@Data
public abstract class Artwork {

    //模板的文件名称。
    private String templateFileName;

    //该作品是 可覆盖型的
    //true: 表示直接用最新的去覆盖。 false: 表示文件不存在就生成，存在了就不改动了。
    private boolean overwrite = false;

    public Artwork() {

    }

    public Artwork(String templateFileName, boolean overwrite) {
        this.templateFileName = templateFileName;
        this.overwrite = overwrite;
    }

    //目标文件的路径。
    public abstract String destAbsolutePath();

    public abstract void init(@NonNull SaberTable saberTable, @NonNull SaberCommand saberCommand);
}
