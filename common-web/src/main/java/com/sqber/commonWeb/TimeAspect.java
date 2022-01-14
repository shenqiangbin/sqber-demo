package com.sqber.commonWeb;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * 切面
 */
@Aspect
@Component
public class TimeAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeAspect.class);

    ThreadLocal<Long> start = new ThreadLocal<>();

    @Pointcut("execution(public * com.sqber.*.controller..*.*(..))")
    public void handlerTime() {

    }

    @Around("handlerTime()")
    public Object execute(ProceedingJoinPoint pjp) throws Throwable {

        start.set(System.currentTimeMillis());
        Object result;
        result = pjp.proceed();
        LOGGER.info("time: " + pjp.getSignature() + ":" + (System.currentTimeMillis() - start.get()) / 1000f + "s");
        return result;
    }
}
