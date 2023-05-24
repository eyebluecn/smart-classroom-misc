package com.smart.classroom.misc.domain.biz.payment.enums;

import lombok.Getter;

public enum PayMethod {

    ALIPAY("支付宝"),
    WEIXIN("微信");

    @Getter
    private final String description;

    PayMethod(String description) {
        this.description = description;
    }


    public static String toString(PayMethod status) {
        if (status == null) {
            return null;
        }
        return status.name();
    }

    public static PayMethod toEnum(String s) {
        if (s == null) {
            return null;
        }
        return PayMethod.valueOf(s);
    }
}
