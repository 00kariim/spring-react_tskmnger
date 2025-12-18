package com.example.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Configuration
public class AppConfig {
    // Shared application-level configuration can go here.

    @Bean
    public FilterRegistrationBean<OncePerRequestFilter> requestResponseLoggingFilter() {
        FilterRegistrationBean<OncePerRequestFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
                ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

                long start = System.currentTimeMillis();
                try {
                    // Let downstream read the request (do not pre-read here, that would consume the stream)
                    filterChain.doFilter(wrappedRequest, wrappedResponse);
                } finally {
                    long duration = System.currentTimeMillis() - start;
                    String reqBody = payloadAsString(wrappedRequest.getContentAsByteArray(), wrappedRequest.getCharacterEncoding());
                    String respBody = payloadAsString(wrappedResponse.getContentAsByteArray(), wrappedResponse.getCharacterEncoding());

                    String msg = String.format("%s %s %d %dms req=%s resp=%s",
                            request.getMethod(),
                            request.getRequestURI(),
                            wrappedResponse.getStatus(),
                            duration,
                            sanitize(reqBody),
                            sanitize(respBody)
                    );

                    org.slf4j.LoggerFactory.getLogger("http").info(msg);

                    // copy response body back to the original response
                    wrappedResponse.copyBodyToResponse();
                }
            }
        });
        registration.addUrlPatterns("/*");
        registration.setOrder(0);
        return registration;
    }

    private static String payloadAsString(byte[] buf, String enc) {
        if (buf == null || buf.length == 0) return "";
        try {
            Charset cs = enc == null ? StandardCharsets.UTF_8 : Charset.forName(enc);
            return new String(buf, cs);
        } catch (Exception e) {
            return "[unknown]";
        }
    }

    private static String sanitize(String s) {
        if (s == null) return "";
        String oneLine = s.replaceAll("\\s+", " ").trim();
        if (oneLine.length() > 1000) return oneLine.substring(0, 1000) + "...(truncated)";
        return oneLine;
    }
}


