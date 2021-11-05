//package com.sqber.log4jdemo.aop;
//
//import cnki.cqsd.entity.query.BaseResponse;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
///**
// * 日志切面
// */
//@Aspect
//@Component
//public class LogAspect {
//
//    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
//
//    ThreadLocal<Long> start = new ThreadLocal<>();
//
//    @Pointcut("execution(public * cnki.cqsd.web.ui.controller..*.*(..))")
//    public void handlerException(){
//
//    }
//
//    @Around("handlerException()")
//    public Object execute(ProceedingJoinPoint pjp) {
//
//        start.set(System.currentTimeMillis());
//        Object result;
//        try{
//            result = pjp.proceed();
//            logger.info(pjp.getSignature() + " 方法执行耗时:" + (System.currentTimeMillis() - start.get()));
//        }
//        catch(Throwable e){
//            logger.error(pjp.getSignature() + " error {}  args: {}", e.getLocalizedMessage(), Arrays.toString(pjp.getArgs()));
//            result =  new BaseResponse(BaseResponse.FAIL, "出错了");
//        }
//        return result;
//    }
//}
