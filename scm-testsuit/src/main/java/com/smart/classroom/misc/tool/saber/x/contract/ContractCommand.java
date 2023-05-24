package com.smart.classroom.misc.tool.saber.x.contract;


import com.smart.classroom.misc.domain.biz.contract.enums.ContractStatus;
import com.smart.classroom.misc.tool.saber.command.SaberCommand;
import com.smart.classroom.misc.tool.saber.support.SaberHelper;
import com.smart.classroom.misc.utility.model.Pair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 直接运行该文件，会生成对应的DO，Mapper文件。
 */
public class ContractCommand {
    public static void main(String[] args) {

        /*
         * 	scm_contract
         */
        SaberCommand saberCommand = new SaberCommand("scm_contract");

        //模糊筛选
        saberCommand.setFilterLikeFields(new ArrayList<String>() {{
            //下划线风格，驼峰均可以
            add("name");

        }});

        //等号筛选
        saberCommand.setFilterEqualFields(new ArrayList<String>() {{
            //下划线风格，驼峰均可以

            add("column_id");
            add("author_id");
            add("status");
        }});

        //排序
        saberCommand.setFilterOrderFields(new ArrayList<String>() {{
            //下划线风格，驼峰均可以

        }});

        //枚举定义
        saberCommand.setEnumFieldMap(new HashMap<String, Pair<Class<? extends Enum<?>>, String>>() {{
            put("status", new Pair<>(ContractStatus.class, ContractStatus.NEW.name()));
        }});

        SaberHelper.run(saberCommand);
    }
}
