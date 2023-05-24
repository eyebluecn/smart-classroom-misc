package com.smart.classroom.misc.domain.biz.receipt.enums;

import lombok.Getter;

public enum ReceiptStatus {

    UNPAID("未支付"),
    PAID("已支付"),
    CLOSED("已关闭");

    @Getter
    private final String description;

    ReceiptStatus(String description) {
        this.description = description;
    }

}
