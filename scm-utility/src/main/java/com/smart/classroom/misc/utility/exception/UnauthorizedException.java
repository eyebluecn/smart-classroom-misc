package com.smart.classroom.misc.utility.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 用户没有权限，不能访问
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class UnauthorizedException extends UtilException {

    public UnauthorizedException() {
        super(ExceptionCode.UNAUTHORIZED);
    }

    public UnauthorizedException(String messagePattern, Object... arguments) {
        super(ExceptionCode.UNAUTHORIZED, messagePattern, arguments);
    }

}