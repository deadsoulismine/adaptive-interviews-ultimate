package com.smartech.i2019.adaptiveinterviews.util.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Slf4j
@Aspect
@Component
public class AspectLogger {
    private Logger logger = Logger.getLogger(AspectLogger.class.getName());

    @Pointcut("execution(public * com.smartech.i2019.adaptiveinterviews.service.*.*(..))")
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


}
