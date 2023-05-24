package com.smart.classroom.misc.utility.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 服务器内部出错
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class ServerException extends UtilException {

    public ServerException() {
        super(ExceptionCode.SERVER);
    }

    public ServerException(String messagePattern, Object... arguments) {
        super(ExceptionCode.SERVER, messagePattern, arguments);
    }

}