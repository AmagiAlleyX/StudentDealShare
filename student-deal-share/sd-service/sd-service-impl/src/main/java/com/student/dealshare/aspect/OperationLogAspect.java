package com.student.dealshare.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.dealshare.model.entity.OperationLog;
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
import java.util.Map;

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
        
        OperationLog operationLog = new OperationLog();
        operationLog.setOperationTime(LocalDateTime.now());
        operationLog.setRequestMethod(request.getMethod());
        operationLog.setRequestUrl(request.getRequestURI());
        operationLog.setRequestParams(objectMapper.writeValueAsString(point.getArgs()));
        operationLog.setIpAddress(request.getRemoteAddr());
        operationLog.setUserAgent(request.getHeader("User-Agent"));
        
        try {
            Object result = point.proceed();
            long executeTime = System.currentTimeMillis() - startTime;
            operationLog.setExecuteTime(executeTime);
            operationLog.setStatus(1);
            operationLogService.save(operationLog);
            return result;
        } catch (Throwable e) {
            long executeTime = System.currentTimeMillis() - startTime;
            operationLog.setExecuteTime(executeTime);
            operationLog.setStatus(0);
            operationLogService.save(operationLog);
            throw e;
        }
    }
}
