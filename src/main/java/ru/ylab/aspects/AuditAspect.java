package ru.ylab.aspects;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.ylab.models.User;
import ru.ylab.utils.StringUtil;

/**
 * Aspect auditing user actions and calculating database requests execution time.
 *
 * @author azatyamanaev
 */
@Aspect
@Slf4j
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

    @Pointcut("!execution(* *service(..))")
    public void excludeRouting() {
    }

    @AfterReturning(value = "annotatedWithLogRequest() && publicMethod() && excludeRouting() " +
            "&& args(req, resp, user)", argNames = "req, resp, user")
    public void logUserRequest(HttpServletRequest req, HttpServletResponse resp, User user) throws Throwable {
        writeRequestLog(req, user);
    }

    @AfterReturning(value = "annotatedWithLogRequest() && publicMethod() && excludeRouting() " +
            "&& args(req, resp)", argNames = "req, resp")
    public void logAdminRequest(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
        User user = (User) req.getAttribute("currentUser");
        writeRequestLog(req, user);
    }

    private void writeRequestLog(HttpServletRequest req, User user) {
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
