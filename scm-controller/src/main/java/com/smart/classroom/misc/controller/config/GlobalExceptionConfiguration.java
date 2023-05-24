package com.smart.classroom.misc.controller.config;

import com.smart.classroom.misc.controller.base.result.ResultCode;
import com.smart.classroom.misc.controller.base.result.WebResult;
import com.smart.classroom.misc.utility.exception.ServerException;
import com.smart.classroom.misc.utility.exception.UtilException;
import com.smart.classroom.misc.utility.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionConfiguration {

    //表单提交时报错。
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class})
    @ResponseBody
    public WebResult<?> handleBindException(HttpServletRequest req, BindException exception) {

        String defaultMessage = "以下参数不符合要求。";
        StringBuilder message = new StringBuilder(defaultMessage);
        List<ObjectError> errorList = exception.getAllErrors();
        for (ObjectError objectError : errorList) {

            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;

                if (fieldError.getRejectedValue() == null || Objects.equals(fieldError.getRejectedValue(), "")) {
                    message.append(StringUtil.format("{}:{};", fieldError.getField(), objectError.getDefaultMessage()));
                } else {
                    message.append(StringUtil.format("{}:{}:{};", fieldError.getField(), StringUtil.digest(StringUtil.format("{}", fieldError.getRejectedValue()), 10, 6), objectError.getDefaultMessage()));
                }

            } else {
                message.append(objectError.getDefaultMessage());
            }
        }

        if (message.toString().equals(defaultMessage)) {
            message = new StringBuilder(ExceptionUtils.getRootCauseMessage(exception));
        }

        WebResult<?> webResult = new WebResult<>(ResultCode.PARAMS_ERROR, message.toString());

        log.info("------BindException------");
        log.error(ExceptionUtils.getRootCauseMessage(exception), exception);

        return webResult;
    }


    //请求参数不符合要求。
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public WebResult<?> handleMissingServletRequestParameterException(HttpServletRequest req, MissingServletRequestParameterException exception) {


        String message = StringUtil.format("要求填写的参数{}({})不符合要求", exception.getParameterName(), exception.getParameterType());
        WebResult<?> webResult = new WebResult<>(ResultCode.PARAMS_ERROR, message);

        log.info("------MissingServletRequestParameterException------");
        log.error(ExceptionUtils.getRootCauseMessage(exception), exception);


        return webResult;
    }

    //自定义的异常。
    @ExceptionHandler({UtilException.class})
    @ResponseBody
    public WebResult<?> handleUtilException(HttpServletRequest request,
                                            HttpServletResponse response,
                                            UtilException exception) {

        ResultCode resultCode = ResultCode.fromExceptionCode(exception.getCode());
        //按照各自的异常进行返回。
        response.setStatus(resultCode.getHttpStatus().value());


        WebResult<?> webResult = new WebResult<>(resultCode, exception.getMessage());

        if (resultCode == ResultCode.UNKNOWN) {
            //只有未知错误才打印错误
            log.info("------" + exception.getClass().getName() + "------");
            log.error(ExceptionUtils.getRootCauseMessage(exception), exception);
        } else {
            log.info("------" + exception.getClass().getName() + "------");
            log.warn(ExceptionUtils.getRootCauseMessage(exception), exception);
        }

        return webResult;
    }

    //服务器不能处理的异常。
    @ExceptionHandler({ServerException.class})
    @ResponseBody
    public WebResult<?> handleServerException(HttpServletRequest request,
                                              HttpServletResponse response,
                                              ServerException exception) {

        //按照各自的异常进行返回。
        ResultCode resultCode = ResultCode.fromExceptionCode(exception.getCode());
        response.setStatus(resultCode.getHttpStatus().value());

        WebResult<?> webResult = new WebResult<>(resultCode, exception.getMessage());

        log.info("-------" + exception.getClass().getName() + "-------");
        log.error(ExceptionUtils.getRootCauseMessage(exception), exception);

        return webResult;
    }

    //其他不能处理的异常。
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Throwable.class})
    @ResponseBody
    public WebResult<?> handleException(HttpServletRequest req, Throwable exception) {

        WebResult<?> webResult = new WebResult<>(ResultCode.UNKNOWN, StringUtil.format("未知错误，{}", ExceptionUtils.getRootCauseMessage(exception)));

        log.info("------Exception------");
        log.error(ExceptionUtils.getRootCauseMessage(exception), exception);

        return webResult;
    }
}
