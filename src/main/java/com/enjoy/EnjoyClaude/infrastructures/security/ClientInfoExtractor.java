package com.enjoy.EnjoyClaude.infrastructures.security;

import com.enjoy.EnjoyClaude.domains.code.DefaultValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ClientInfoExtractor {

    /**
     * 클라이언트의 실제 IP 주소를 추출합니다.
     * 프록시나 로드밸런서를 거친 경우를 고려합니다.
     */
    public String getClientIpAddress(final HttpServletRequest request) {
        final String unknown = DefaultValue.UNKNOWN.getValue();
        String ip = request.getHeader("X-Forwarded-For");

        if (StringUtils.isBlank(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // X-Forwarded-For에 여러 IP가 있는 경우 첫 번째 IP 사용
        if (StringUtils.isNotBlank(ip) && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return StringUtils.isNotBlank(ip) ? ip : unknown;
    }

    /**
     * 클라이언트의 User-Agent를 추출합니다.
     */
    public String getUserAgent(final HttpServletRequest request) {
        final String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        return StringUtils.isNotBlank(userAgent) ? userAgent : DefaultValue.UNKNOWN.getValue();
    }
}
