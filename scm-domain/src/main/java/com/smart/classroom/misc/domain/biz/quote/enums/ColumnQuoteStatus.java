package com.smart.classroom.misc.domain.biz.quote.enums;

import com.smart.classroom.misc.domain.biz.payment.enums.PayStatus;
import lombok.Getter;

public enum ColumnQuoteStatus {

    OK("已生效"),
    DISABLED("未生效");

    @Getter
    private final String description;

    ColumnQuoteStatus(String description) {
        this.description = description;
    }


    public static String toString(ColumnQuoteStatus status) {
        if (status == null) {
            return null;
        }
        return status.name();
    }

    public static ColumnQuoteStatus toEnum(String s) {
        if (s == null) {
            return null;
        }
        return ColumnQuoteStatus.valueOf(s);
    }

}
