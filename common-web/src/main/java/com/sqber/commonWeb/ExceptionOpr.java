package com.sqber.commonWeb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 */
@RestControllerAdvice
public class ExceptionOpr {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionOpr.class);

    @ExceptionHandler(Exception.class)
    public Resp globleExceptionHandler(Exception e) {

        LOGGER.error("error:", e);
        return Resp.error(e.getMessage());
    }
}
