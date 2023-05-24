package com.smart.classroom.misc.domain.biz.column.enums;

import lombok.Getter;

public enum ColumnStatus {

    NEW("未发布"),
    OK("已发布"),
    DISABLED("已禁用");

    @Getter
    private final String description;

    ColumnStatus(String description) {
        this.description = description;
    }


    public static String toString(ColumnStatus columnStatus) {
        if (columnStatus == null) {
            return null;
        }
        return columnStatus.name();
    }

    public static ColumnStatus toEnum(String columnStatusStr) {
        if (columnStatusStr == null) {
            return null;
        }
        return ColumnStatus.valueOf(columnStatusStr);
    }

}
