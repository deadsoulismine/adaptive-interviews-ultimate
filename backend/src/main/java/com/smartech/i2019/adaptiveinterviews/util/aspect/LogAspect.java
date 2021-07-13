package com.smartech.i2019.adaptiveinterviews.util.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LogAspect {
    private Logger logger = Logger.getLogger(LogAspect.class.getName());

    @Pointcut("execution(* *(..))")
    public void methodExecuting() {
    }

    @AfterReturning(value = "methodExecuting()", returning = "returningValue")
    public void log(JoinPoint point, Object returningValue) {
        if (returningValue != null) {
            logger.info("Method [" + point.getSignature().getName() +
                    "] call from class [" + point.getSourceLocation().getWithinType().getSimpleName() +
                    "] return value [" + returningValue + "]");
        } else {
            logger.info("Method [" + point.getSignature().getName() +
                    "] call from class [" + point.getSourceLocation().getWithinType().getSimpleName() + "]");
        }
    }
//
//    @After("methodExecuting()")
//    public void logMethodCall(JoinPoint jp) {
//        String methodName = jp.getSignature().getName();
//        String className = jp.getSourceLocation().getWithinType().getSimpleName();
//        logger.log(Level.INFO, "Method [" + methodName + "] call from class [" + className + "]");
//    }

}
