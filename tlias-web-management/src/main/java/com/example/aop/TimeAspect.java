package com.example.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


//获取操作执行时间的aspect demo
@Component
//@Aspect
@Slf4j
public class TimeAspect {

//    调用service包下的任意类的任意方法时，调用pt方法
    @Pointcut("execution(* com.example.tlias.service.*.*(..))")
    public void pt(){}

//    调用pt方法前后,执行以下操作.
    @Around("pt()")
    public Object recordTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        Object object = proceedingJoinPoint.proceed();
        long end= System.currentTimeMillis();
        log.info(proceedingJoinPoint.getSignature() + "耗时时间{}ms", end - begin);
        return object;
    }
}
