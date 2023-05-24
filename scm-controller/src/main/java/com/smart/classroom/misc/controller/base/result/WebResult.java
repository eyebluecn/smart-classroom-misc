package com.smart.classroom.misc.controller.base.result;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 出参统一格式
 *
 * @param <T> 泛型，可以不带，默认会是Object
 */
@Data
@NoArgsConstructor
public class WebResult<T> {

    private ResultCode code = ResultCode.OK;
    private T data;
    private String msg = "success";

    public WebResult(ResultCode code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public WebResult(ResultCode code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public WebResult(T data) {
        this.data = data;
    }

}
