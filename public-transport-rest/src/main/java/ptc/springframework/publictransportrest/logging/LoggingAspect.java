package ptc.springframework.publictransportrest.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    private final ObjectMapper mapper;

    public LoggingAspect(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Pointcut("within(ptc.springframework.publictransportrest.services..*)")
    public void servicePackagePointcut() {}

    @Pointcut("within(ptc.springframework.publictransportrest.controllers..*)")
    public void restPackagePointCut() {}

    @Pointcut("within(ptc.springframework.publictransportrest.exceptions.GlobalExceptionHandler..*)")
    public void exceptionPackagePointCut() {}

    @Around("servicePackagePointcut() || restPackagePointCut() || exceptionPackagePointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String traceId = MDC.get("traceId");
        if (traceId == null) {
            traceId = UUID.randomUUID().toString();
            MDC.put("traceId", traceId);
        }
        boolean inputAlreadyLogged = false;
        if (log.isDebugEnabled()) {
            logInput(joinPoint, traceId);
            inputAlreadyLogged = true;
        }
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            logError(joinPoint, e, inputAlreadyLogged, traceId);
            throw e;
        }
        if (log.isDebugEnabled()) {
            logOutput(joinPoint, result, traceId);
        }

        return result;
    }

    private void logInput(ProceedingJoinPoint joinPoint, String traceId) {
        log.debug(
                "{} - IN: {}.{}(){}",
                traceId,
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs().length > 0 ? ": " + toJson(joinPoint.getArgs()) : "");
    }

    private void logOutput(ProceedingJoinPoint joinPoint, Object result, String traceId) {
        log.debug(
                "{} - OUT: {}.{}(): {}",
                traceId,
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(),
                toJson(result));
    }

    private void logError(
            ProceedingJoinPoint joinPoint, Exception ex, boolean inputalreadyLogged, String traceId) {
        if (!inputalreadyLogged) {
            log.error(
                    "{} - IN: {}.{}(){}",
                    traceId,
                    joinPoint.getSignature().getDeclaringType().getSimpleName(),
                    joinPoint.getSignature().getName(),
                    joinPoint.getArgs().length > 0 ? ": " + toJson(joinPoint.getArgs()) : "");
        }
        log.error(
                "{} - EXCEPTION: {}.{}(): {}",
                traceId,
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(),
                ex.getClass().getSimpleName() + "<" + ex.getMessage() + ">");
    }

    private String toJson(Object result) {
        try {
            return mapper.writeValueAsString(result);
        } catch (JsonProcessingException jpe) {
            return "json parse exception:" + jpe.getMessage();
        }
    }
}
