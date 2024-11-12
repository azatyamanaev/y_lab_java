package ru.ylab.auditstarter.aspects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Arrays;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.ylab.auditstarter.repository.AuditRepository;

/**
 * Aspect for auditing user actions.
 *
 * @author azatyamanaev
 */
@Aspect
public class AuditAspect {

    private AuditRepository auditRepository;

    public AuditAspect(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Pointcut("within(@ru.ylab.auditstarter.annotations.AuditRequest *)")
    public void annotatedWithAuditRequest() {
    }

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {
    }

    @AfterReturning(value = "annotatedWithAuditRequest() && publicMethod()")
    public void logUserRequest() throws InvocationTargetException, IllegalAccessException {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Object user = req.getAttribute("currentUser");

        Method[] methods = user.getClass().getMethods();
        Method getId = Arrays.stream(methods).filter(x -> x.getName().equals("getId")).findFirst().get();
        Method getRole = Arrays.stream(methods).filter(x -> x.getName().equals("getRole")).findFirst().get();
        auditRepository.save(req.getMethod(), req.getRequestURI(),
                (Long) getId.invoke(user), (Object) getRole.invoke(user), Instant.now());
    }
}
