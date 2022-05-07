package com.devcourse.drink.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcut {

    @Pointcut("execution(public * com.devcourse.drink..*Service.*(..))")
    public void servicePublicMethodPointcut() {
        // TODO Service Layer pointCut
    }

    @Pointcut("execution(public * com.devcourse.drink.*.*.*Repository.*(..))")
    public void repositoryPublicMethodPointcut() {
        // TODO Repository Layer Pointcut
    }

    @Pointcut("execution(public * com.devcourse.drink.*.*.*Controller.*(..))")
    public void controllerPublicMethodPointcut() {
        // TODO Contoller Layer Pointcut
    }
}
