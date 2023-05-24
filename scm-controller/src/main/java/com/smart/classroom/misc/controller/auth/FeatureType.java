package com.smart.classroom.misc.controller.auth;

import com.smart.classroom.misc.domain.biz.payment.enums.PayStatus;
import lombok.Getter;

/**
 * @author lishuang
 * @date 2023-05-18
 */
public enum FeatureType {

    /**
     * 公共接口，所有人均可访问。
     */
    PUBLIC("公共接口"),

    /**
     * 读者登录能访问
     */
    READER_LOGIN("读者登录"),

    /**
     * 作者登录能访问
     */
    AUTHOR_LOGIN("作者登录"),

    /**
     * 编辑登录能访问
     */
    EDITOR_LOGIN("编辑登录");

    @Getter
    private final String description;

    FeatureType(String description) {
        this.description = description;
    }


    public static String toString(FeatureType status) {
        if (status == null) {
            return null;
        }
        return status.name();
    }

    public static FeatureType toEnum(String s) {
        if (s == null) {
            return null;
        }
        return FeatureType.valueOf(s);
    }



}
