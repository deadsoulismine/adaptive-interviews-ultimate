package com.smartech.i2019.adaptiveinterviews.util.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AspectLogger {

    @Pointcut("execution(public * com.smartech.i2019.adaptiveinterviews.service.*.*(..))")
    public void methodExecuting() {
    }

    @AfterReturning(value = "methodExecuting()", returning = "returningValue")
    public void log(JoinPoint point, Object returningValue) {
        if (returningValue != null) {
            log.info("Method [{}] call from class [{}] return value [{}]",
                    point.getSignature().getName(),
                    point.getSourceLocation().getWithinType().getSimpleName(),
                    returningValue);
        } else {
            log.info("Method [{}}] call from class [{}]",
                    point.getSignature().getName(),
                    point.getSourceLocation().getWithinType().getSimpleName());
        }
    }

}
