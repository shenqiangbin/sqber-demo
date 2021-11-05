package com.sqber.commonWeb;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 日志切面
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    ThreadLocal<Long> start = new ThreadLocal<>();

    @Pointcut("execution(public * com.sqber.log4jdemo.controller..*.*(..))")
    public void handlerException(){

    }

    @Around("handlerException()")
    public Object execute(ProceedingJoinPoint pjp) {

        start.set(System.currentTimeMillis());
        Object result;
        try{
            result = pjp.proceed();
           //LOGGER.info(pjp.getSignature() + " 方法执行耗时:" + (System.currentTimeMillis() - start.get()));
        }
        catch(Throwable e){
            LOGGER.error(pjp.getSignature() + " args: {} ", Arrays.toString(pjp.getArgs()), e);
            result =  BaseResponse.error(e.getMessage());
        }
        return result;
    }
}
