package com.sqber.commonWeb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.stream.Collectors;

/**
 * 统一异常处理
 */
@RestControllerAdvice
public class ExceptionOpr {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionOpr.class);

    @ExceptionHandler(Exception.class)
    public R globleExceptionHandler(Exception e) {

        if (!(e instanceof BindException || e instanceof ValidationException)) {
            LOGGER.error("", e);
        }
        
        if (e instanceof BindException) {

            String msg = ((BindException) e).getAllErrors().stream()
                    .map(m -> m.getDefaultMessage())
                    .collect(Collectors.joining("; "));

            return R.warn(msg);

        } else if (e instanceof ConstraintViolationException) {

            String msg = ((ConstraintViolationException) e).getConstraintViolations().stream()
                    .map(m -> m.getMessage())
                    .collect(Collectors.joining("; "));

            return R.warn(msg);
        } else if (e instanceof ValidationException) {
            return R.warn(e.getMessage());
        }

        return R.error(e.getMessage());
    }
}
