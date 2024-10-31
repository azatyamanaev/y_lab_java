package ru.ylab.aspects;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.ylab.models.User;
import ru.ylab.utils.StringUtil;

/**
 * Aspect auditing user actions and calculating database requests execution time.
 *
 * @author azatyamanaev
 */
@Slf4j
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

    @Pointcut("!execution(public * *initBinder(..))")
    public void excludeBinding() {}

    @AfterReturning(value = "annotatedWithLogRequest() && publicMethod() && excludeBinding()")
    public void logUserRequest() {
        HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        User user = (User) req.getAttribute("currentUser");
        String uri = StringUtil.parseReqUri(req.getRequestURI());
        log.info("Request {} {} completed for user {} with role {}", req.getMethod(), uri, user.getEmail(), user.getRole());
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
