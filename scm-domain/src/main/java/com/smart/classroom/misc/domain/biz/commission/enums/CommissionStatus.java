package com.smart.classroom.misc.domain.biz.commission.enums;

import lombok.Getter;

public enum CommissionStatus {

    CREATED("未收款"),
    RECEIVED("已收款"),
    CANCELED("已取消"),
    CLOSED("已关闭");

    @Getter
    private final String description;

    CommissionStatus(String description) {
        this.description = description;
    }

}
