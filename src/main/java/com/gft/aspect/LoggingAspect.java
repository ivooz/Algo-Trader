package com.gft.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Created by iozi on 15/10/2015.
 */
@Aspect
@Component
public class LoggingAspect {


    @Pointcut("execution(public * *(..)) && @annotation(com.gft.aspect.Log)")
    public void publicService() {
    }

    @Pointcut("execution(public * *(..)) && @annotation(com.gft.aspect.LogNoArgs)")
    public void publicServiceNoArgs() {
    }

    @Before("com.gft.aspect.LoggingAspect.publicService()")
    private void enteringPublicService(JoinPoint joinPoint) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Entering public method ").append(joinPoint.getSignature()).append(" with arguments ");
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            stringBuilder.append(" {").append(args[i]).append("}");
        }
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.info(stringBuilder.toString());
    }

    @AfterReturning(pointcut = "com.gft.aspect.LoggingAspect.publicService()", returning = "retVal")
    public void exitingPublicService(JoinPoint joinPoint, Object retVal) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Exiting public method ").append(joinPoint.getSignature()).append(" returning ")
                .append(retVal);
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.info(stringBuilder.toString());
    }

    @Before("com.gft.aspect.LoggingAspect.publicServiceNoArgs()")
    private void enteringPublicServiceNoArgs(JoinPoint joinPoint) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Entering public method ").append(joinPoint.getSignature());
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.info(stringBuilder.toString());
    }

    @AfterReturning(pointcut = "com.gft.aspect.LoggingAspect.publicServiceNoArgs()", returning = "retVal")
    public void exitingPublicServiceNoArgs(JoinPoint joinPoint, Object retVal) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Exiting public method ").append(joinPoint.getSignature());
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.info(stringBuilder.toString());
    }
}
