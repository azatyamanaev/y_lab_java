package ru.ylab.auditstarter.aspects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
public class AuditAspect {

    private static Logger logger = LoggerFactory.getLogger(AuditAspect.class);

    @Pointcut("within(@ru.ylab.auditstarter.annotations.AuditRequest *)")
    public void annotatedWithLogRequest() {
    }

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {
    }

    @AfterReturning(value = "annotatedWithLogRequest() && publicMethod()")
    public void logUserRequest() throws InvocationTargetException, IllegalAccessException {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Object user = req.getAttribute("currentUser");

        Method[] methods = user.getClass().getMethods();
        Method getEmail = Arrays.stream(methods).filter(x -> x.getName().equals("getEmail")).findFirst().get();
        Method getRole = Arrays.stream(methods).filter(x -> x.getName().equals("getRole")).findFirst().get();
        logger.info("Request {} {} completed for user {} with role {}", req.getMethod(), req.getRequestURI(),
                getEmail.invoke(user), getRole.invoke(user));
    }
}
