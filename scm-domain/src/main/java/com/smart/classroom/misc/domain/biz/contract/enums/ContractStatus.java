package com.smart.classroom.misc.domain.biz.contract.enums;

import lombok.Getter;

public enum ContractStatus {

    NEW("未生效"),
    OK("已生效"),
    DISABLED("已禁用");

    @Getter
    private final String description;

    ContractStatus(String description) {
        this.description = description;
    }

}
