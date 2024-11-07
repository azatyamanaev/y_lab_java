package ru.ylab.aspects;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.ylab.models.User;

/**
 * Aspect auditing user actions and calculating database requests execution time.
 *
 * @author azatyamanaev
 */
@Log4j2
@Aspect
@Component
public class AuditAspect {

    @Pointcut("within(@ru.ylab.aspects.LogRequest *)")
    public void annotatedWithLogRequest() {
    }

    @Pointcut("within(@ru.ylab.aspects.LogQuery *)")
    public void annotatedWithLogQuery() {
    }

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {
    }

    @AfterReturning(value = "annotatedWithLogRequest() && publicMethod()")
    public void logUserRequest() {
        HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        User user = (User) req.getAttribute("currentUser");
        log.info("Request {} {} completed for user {} with role {}", req.getMethod(), req.getRequestURI(), user.getEmail(), user.getRole());
    }

    @Around("annotatedWithLogQuery() && publicMethod()")
    public Object calculateDatabaseRequestExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis() - startTime;
        log.info("Method {} in class {} executed in {} ms", joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getSimpleName(), endTime);
        return result;
    }
}
