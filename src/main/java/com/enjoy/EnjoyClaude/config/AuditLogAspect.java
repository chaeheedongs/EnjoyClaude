package com.enjoy.EnjoyClaude.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 감사 로그 AOP
 * ISMS 인증심사 요구사항:
 * - 인증/인가 관련 모든 이벤트 로깅
 * - 접근 제어 실패 로깅
 * - 관리자 작업 로깅
 * - 민감 정보 접근 로깅
 */
@Aspect
@Component
@Slf4j
public class AuditLogAspect {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 인증 관련 메서드 실행 전 로깅
     */
    @Before("execution(* com.enjoy.EnjoyClaude.applications.auth.AuthApplicationService.*(..))")
    public void logBeforeAuthOperation(final JoinPoint joinPoint) {
        final String methodName = joinPoint.getSignature().getName();
        final String timestamp = LocalDateTime.now().format(FORMATTER);
        final HttpServletRequest request = getHttpServletRequest();
        final String ipAddress = getClientIp(request);

        log.info("[AUDIT] [{}] 인증 작업 시작 - 메서드: {}, IP: {}, User-Agent: {}",
                timestamp, methodName, ipAddress, getUserAgent(request));
    }

    /**
     * 인증 관련 메서드 성공 후 로깅
     */
    @AfterReturning(pointcut = "execution(* com.enjoy.EnjoyClaude.applications.auth.AuthApplicationService.*(..))",
            returning = "result")
    public void logAfterAuthSuccess(final JoinPoint joinPoint, final Object result) {
        final String methodName = joinPoint.getSignature().getName();
        final String timestamp = LocalDateTime.now().format(FORMATTER);
        final String username = getCurrentUsername();
        final HttpServletRequest request = getHttpServletRequest();
        final String ipAddress = getClientIp(request);

        log.info("[AUDIT] [{}] 인증 작업 성공 - 메서드: {}, 사용자: {}, IP: {}, 결과: {}",
                timestamp, methodName, username, ipAddress, result != null ? result.getClass().getSimpleName() : "null");
    }

    /**
     * 인증 관련 메서드 실패 시 로깅
     */
    @AfterThrowing(pointcut = "execution(* com.enjoy.EnjoyClaude.applications.auth.AuthApplicationService.*(..))",
            throwing = "exception")
    public void logAfterAuthFailure(final JoinPoint joinPoint, final Throwable exception) {
        final String methodName = joinPoint.getSignature().getName();
        final String timestamp = LocalDateTime.now().format(FORMATTER);
        final HttpServletRequest request = getHttpServletRequest();
        final String ipAddress = getClientIp(request);

        log.warn("[AUDIT] [{}] 인증 작업 실패 - 메서드: {}, IP: {}, 예외: {}, 메시지: {}",
                timestamp, methodName, ipAddress,
                exception.getClass().getSimpleName(), exception.getMessage());
    }

    /**
     * 관리자 API 호출 로깅
     */
    @Before("execution(* com.enjoy.EnjoyClaude.interfaces.api..*Controller.*(..)) && " +
            "within(com.enjoy.EnjoyClaude.interfaces.api..*) && " +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void logApiAccess(final JoinPoint joinPoint) {
        final String methodName = joinPoint.getSignature().getName();
        final String timestamp = LocalDateTime.now().format(FORMATTER);
        final String username = getCurrentUsername();
        final HttpServletRequest request = getHttpServletRequest();

        if (request != null) {
            final String uri = request.getRequestURI();
            final String method = request.getMethod();
            final String ipAddress = getClientIp(request);

            log.info("[AUDIT] [{}] API 접근 - {} {}, 메서드: {}, 사용자: {}, IP: {}",
                    timestamp, method, uri, methodName, username, ipAddress);
        }
    }

    /**
     * 현재 인증된 사용자명 조회
     */
    private String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            return authentication.getName();
        }
        return "anonymous";
    }

    /**
     * HttpServletRequest 조회
     */
    private HttpServletRequest getHttpServletRequest() {
        final ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }

    /**
     * 클라이언트 IP 주소 조회
     */
    private String getClientIp(final HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }

        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (StringUtils.isNotBlank(ip) && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return StringUtils.isNotBlank(ip) ? ip : "unknown";
    }

    /**
     * User-Agent 조회
     */
    private String getUserAgent(final HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        final String userAgent = request.getHeader("User-Agent");
        return StringUtils.isNotBlank(userAgent) ? userAgent : "unknown";
    }
}
