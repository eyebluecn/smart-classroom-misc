package com.smart.classroom.misc.tool.saber.x.column;


import com.smart.classroom.misc.domain.biz.column.enums.ColumnStatus;
import com.smart.classroom.misc.tool.saber.command.SaberCommand;
import com.smart.classroom.misc.tool.saber.support.SaberHelper;
import com.smart.classroom.misc.utility.model.Pair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 直接运行该文件，会生成对应的DO，Mapper文件。
 */
public class ColumnCommand {
    public static void main(String[] args) {

        /*
         * 	scm_column
         */
        SaberCommand saberCommand = new SaberCommand("scm_column");

        //模糊筛选
        saberCommand.setFilterLikeFields(new ArrayList<String>() {{
            //下划线风格，驼峰均可以
            add("name");

        }});

        //等号筛选
        saberCommand.setFilterEqualFields(new ArrayList<String>() {{
            //下划线风格，驼峰均可以
            add("author_id");
            add("status");
        }});

        //排序
        saberCommand.setFilterOrderFields(new ArrayList<String>() {{
            //下划线风格，驼峰均可以

        }});

        //枚举定义
        saberCommand.setEnumFieldMap(new HashMap<String, Pair<Class<? extends Enum<?>>, String>>() {{
            put("status", new Pair<>(ColumnStatus.class, ColumnStatus.NEW.name()));
        }});

        SaberHelper.run(saberCommand);
    }
}
