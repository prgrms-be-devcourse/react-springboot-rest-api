package com.devcourse.drink.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("com.devcourse.drink.aop.CommonPointcut.controllerPublicMethodPointcut()")
    public Object controllerLog(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("[Controller] Before method called. {}", joinPoint.getSignature());
        long startTime = System. nanoTime();
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime() - startTime;
        logger.info("[Controller] After method called with result => {} as time taken {} ns", result, endTime);
        return result;
    }

    @Around("com.devcourse.drink.aop.CommonPointcut.servicePublicMethodPointcut()")
    public Object serviceLog(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("[Service] Before method called. {}", joinPoint.getSignature());
        long startTime = System. nanoTime();
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime() - startTime;
        logger.info("[Service] After method called with result => {} as time taken {} ns", result, endTime);
        return result;
    }

    @Around("com.devcourse.drink.aop.CommonPointcut.repositoryPublicMethodPointcut()")
    public Object repositoryLog(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("[Repository] Before method called. {}", joinPoint.getSignature());
        long startTime = System. nanoTime();
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime() - startTime;
        logger.info("[Repository] After method called with result => {} as time taken {} ns", result, endTime);
        return result;
    }
}
