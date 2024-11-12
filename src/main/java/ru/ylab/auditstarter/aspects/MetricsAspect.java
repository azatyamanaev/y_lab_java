package ru.ylab.auditstarter.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Aspect for calculating method execution time.
 *
 * @author azatyamanaev
 */
@Aspect
public class MetricsAspect {

    private static Logger logger = LoggerFactory.getLogger(MetricsAspect.class);

    @Pointcut("within(@ru.ylab.auditstarter.annotations.CalculateExecution *)")
    public void annotatedWithCalculateExecution() {
    }

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {
    }

    @Around("annotatedWithCalculateExecution() && publicMethod()")
    public Object calculateDatabaseRequestExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis() - startTime;
        logger.info("Method {} in class {} executed in {} ms", joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getSimpleName(), endTime);
        return result;
    }
}
