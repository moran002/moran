package com.moran.conf.exception;

import cn.dev33.satoken.exception.SaTokenException;
import com.moran.conf.bean.RestResult;
import com.moran.conf.constant.CodeConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * @author : moran
 * 捕捉全局异常并统一处理
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public RestResult defaultErrorHandler(Exception e) {
        log.error("<!--------默认错误信息:{}------------!>", e.getMessage());
        return RestResult.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public RestResult handleException(Exception e) {
        log.error("<!--------异常信息:{}------------!>", e.getMessage());
        return RestResult.error(e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public RestResult handleBindException(BindException e) {
        log.error("<!--------BindException:{}------------!>", e.getAllErrors());
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return RestResult.error(message);
    }

    @ExceptionHandler(value = SQLException.class)
    public RestResult sqlException(SQLException e) {
        log.error("<!---- SQL异常:{} ----!>", e.getMessage());
        return RestResult.error("数据异常");
    }
    @ExceptionHandler(value = ServiceException.class)
    public RestResult serviceException(ServiceException e) {
        return RestResult.error(CodeConstant.SERVICE_ERROR, e.getMessage());
    }
    @ExceptionHandler(value = SaTokenException.class)
    public RestResult saTokenException(SaTokenException e) {
        log.error("<!---- SaTokenException:{} ----!>", e.getMessage());
        return RestResult.error(CodeConstant.LOGIN_FAIL,e.getMessage());
    }
}