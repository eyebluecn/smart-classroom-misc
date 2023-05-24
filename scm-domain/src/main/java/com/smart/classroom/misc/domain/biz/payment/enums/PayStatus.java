package com.smart.classroom.misc.domain.biz.payment.enums;

import lombok.Getter;

public enum PayStatus {

    UNPAID("未支付"),
    PAID("已支付"),
    CLOSED("已关闭");

    @Getter
    private final String description;

    PayStatus(String description) {
        this.description = description;
    }


    public static String toString(PayStatus status) {
        if (status == null) {
            return null;
        }
        return status.name();
    }

    public static PayStatus toEnum(String s) {
        if (s == null) {
            return null;
        }
        return PayStatus.valueOf(s);
    }


}
