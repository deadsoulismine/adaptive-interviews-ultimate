package com.smartech.i2019.adaptiveinterviews.util.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogger {
    private static final Logger logger = LoggerFactory.getLogger(AspectLogger.class.getName());

    @Pointcut("execution(public * com.smartech.i2019.adaptiveinterviews.service.*.*(..))")
    public void methodExecuting() {
    }

    @AfterReturning(value = "methodExecuting()", returning = "returningValue")
    public void log(JoinPoint point, Object returningValue) {
        if (returningValue != null) {
            logger.debug("Method [{}] call from class [{}] return value [{}]",
                    point.getSignature().getName(),
                    point.getSourceLocation().getWithinType().getSimpleName(),
                    returningValue);

        } else {
            logger.debug("Method [{}}] call from class [{}]",
                    point.getSignature().getName(),
                    point.getSourceLocation().getWithinType().getSimpleName());
        }
    }

}
