package com.smart.classroom.misc.utility.enums;

/**
 * @author lishuang
 * @date 2023-04-14
 */

import lombok.Getter;

public enum SortDirection {
    ASC("升序"),
    DESC("降序");;

    @Getter
    private final String description;

    SortDirection(String description) {
        this.description = description;
    }

    public static String toString(SortDirection direction) {
        if (direction == null) {
            return null;
        }
        return direction.name();
    }

    public static SortDirection toEnum(String s) {
        if (s == null) {
            return null;
        }
        return SortDirection.valueOf(s);
    }
}