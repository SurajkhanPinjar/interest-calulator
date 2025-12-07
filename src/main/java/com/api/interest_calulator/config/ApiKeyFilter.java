package com.api.interest_calulator.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Slf4j
@Component
public class ApiKeyFilter implements Filter {

    @Value("${security.api.key:}")
    private String internalApiKey;

    @Value("${security.api.enabled:true}")
    private boolean securityEnabled;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (!securityEnabled) {
            chain.doFilter(request, response);
            return;
        }

        // Allow Swagger + Health Check
        String path = req.getRequestURI();
        if (path.equals("/ping") ||
                path.contains("swagger") ||
                path.contains("api-docs") ||
                path.contains("v3/api-docs")) {

            chain.doFilter(request, response);
            return;
        }

        // Support both headers
        String clientKey = req.getHeader("X-API-KEY");
        String rapidKey = req.getHeader("X-RapidAPI-Key");

        // Choose whichever exists
        String finalKey = (clientKey != null) ? clientKey : rapidKey;

        // Safe masked logging
        log.debug("Incoming API Key (masked): {}", mask(finalKey));
        log.debug("From header: {}", clientKey != null ? "X-API-KEY" : "X-RapidAPI-Key");

        if (finalKey == null || finalKey.isBlank()) {
            return unauthorized(res, "Missing API Key");
        }

        // 1) Allow internal Railway private key
        if (finalKey.equals(internalApiKey)) {
            chain.doFilter(request, response);
            return;
        }

        // 2) Allow RapidAPI keys (alphanumeric 20–60 chars)
        if (finalKey.matches("^[A-Za-z0-9]{20,60}$")) {
            chain.doFilter(request, response);
            return;
        }

        unauthorized(res, "Invalid API Key");
    }

    private void unauthorized(HttpServletResponse res, String message) throws IOException {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType("application/json");
        res.getWriter().write("{\"success\":false,\"message\":\"" + message + "\"}");
    }

    private String mask(String key) {
        if (key == null || key.length() < 6) return "******";
        return key.substring(0, 3) + "****" + key.substring(key.length() - 3);
    }
}