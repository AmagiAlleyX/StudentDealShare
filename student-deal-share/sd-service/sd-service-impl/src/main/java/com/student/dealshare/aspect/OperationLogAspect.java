package com.student.dealshare.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.dealshare.annotation.OperationLog;
import com.student.dealshare.service.api.OperationLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

/**
 * 操作日志切面
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OperationLogAspect {

    private final OperationLogService operationLogService;
    private final ObjectMapper objectMapper;

    @Pointcut("@annotation(com.student.dealshare.annotation.OperationLog)")
    public void logPointcut() {
    }

    @Around("logPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        com.student.dealshare.model.entity.OperationLog operationLog = new com.student.dealshare.model.entity.OperationLog();

        OperationLog annotation = getOperationLogAnnotation(point);
        if (annotation != null) {
            operationLog.setModule(annotation.module());
            operationLog.setOperation(annotation.type());
        }

        operationLog.setCreatedAt(LocalDateTime.now());
        operationLog.setMethod(request.getMethod());
        operationLog.setParams(objectMapper.writeValueAsString(point.getArgs()));
        operationLog.setIp(request.getRemoteAddr());
        operationLog.setUserAgent(request.getHeader("User-Agent"));

        try {
            Object result = point.proceed();
            long executeTime = System.currentTimeMillis() - startTime;
            operationLog.setTime(executeTime);
            operationLog.setStatus(1);
            operationLog.setResult(objectMapper.writeValueAsString(result));
            operationLogService.save(operationLog);
            return result;
        } catch (Throwable e) {
            long executeTime = System.currentTimeMillis() - startTime;
            operationLog.setTime(executeTime);
            operationLog.setStatus(0);
            operationLog.setResult(e.getMessage());
            operationLogService.save(operationLog);
            throw e;
        }
    }
    /**
     * 获取操作日志注解
     */
    private OperationLog getOperationLogAnnotation(ProceedingJoinPoint point) {
        try {
            org.aspectj.lang.reflect.MethodSignature signature =
                    (org.aspectj.lang.reflect.MethodSignature) point.getSignature();
            java.lang.reflect.Method method = signature.getMethod();
            return method.getAnnotation(OperationLog.class);
        } catch (Exception e) {
            log.warn("获取 OperationLog 注解失败", e);
            return null;
        }
    }
}
