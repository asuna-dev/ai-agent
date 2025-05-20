package org.zepe.aiagent.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zepe.aiagent.common.Response;

/**
 * @author zzpus
 * @datetime 2025/4/27 22:04
 * @description
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Response<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return Response.failed(e.getCode(), e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public Response<?> runtimeExceptionHandler(Exception e) {
        log.error("Exception", e);
        return Response.failed(ErrorCode.SYSTEM_ERROR);
    }

}

