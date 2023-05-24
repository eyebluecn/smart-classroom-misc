package com.smart.classroom.misc.tool.saber.meta;


import com.smart.classroom.misc.tool.saber.command.SaberCommand;
import com.smart.classroom.misc.tool.saber.contrast.TypeContrast;
import com.smart.classroom.misc.utility.exception.UtilException;
import com.smart.classroom.misc.utility.model.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 数据库表，元信息
 *
 * @author lishuang 2023-05-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class SaberTable {

    private String name;
    private String remark;

    private List<SaberColumn> columns = new ArrayList<>();


    //根据给定的数据库字段，获取到对应的SaberColumn.
    public List<SaberColumn> fetchSaberColumns(@NonNull List<String> fields) {

        List<SaberColumn> columnList = new ArrayList<>();

        fields.forEach(field -> {

            SaberColumn targetColumn = null;
            for (SaberColumn column : columns) {
                //用户输入create_time 或者 createTime 都可以
                if (column.sameField(field)) {

                    targetColumn = column;

                }
            }

            if (targetColumn == null) {
                throw new UtilException("{} 中不存在字段 {}", name, field);

            } else {

                columnList.add(targetColumn);
            }

        });

        return columnList;

    }


    //根据saberCommand中的自定义设置，来调整一下saberTable中的内容
    public void prepare(SaberCommand saberCommand) {


        for (Map.Entry<String, TypeContrast> entry : saberCommand.getCustomFieldTypes().entrySet()) {

            //调整自定义的字段
            for (SaberColumn column : this.columns) {

                //用户输入create_time 或者 createTime 都可以
                if (column.sameField(entry.getKey())) {

                    column.updateTypeContrast(entry.getValue(), column.getDefaultValue());

                }


            }


        }


        //一些枚举字段，我们希望直接使用枚举。。
        for (Map.Entry<String, Pair<Class<? extends Enum<?>>, String>> entry : saberCommand.getEnumFieldMap().entrySet()) {

            //调整自定义的字段
            for (SaberColumn column : this.columns) {

                //用户输入gmt_create 或者 gmtCreate 都可以
                if (column.sameField(entry.getKey())) {

                    column.updateEnumField(entry.getValue().getKey(), entry.getValue().getValue());

                }


            }


        }


        //有个别废弃掉的字段不予考虑。(小心删除陷阱，必须使用迭代器删除)
        Iterator<SaberColumn> it = columns.iterator();
        while (it.hasNext()) {
            SaberColumn column = it.next();
            //调整自定义的字段
            for (String ignoreField : saberCommand.getIgnoreFields()) {

                //用户输入create_time 或者 createTime 都可以
                if (column.sameField(ignoreField)) {
                    it.remove();
                    break;
                }


            }

        }


    }


}
