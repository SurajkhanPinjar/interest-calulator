package com.api.interest_calulator.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiKeyFilter implements Filter {

    @Value("${security.api.key:}")
    private String internalApiKey;  // Railway private API key

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

        // Allow health check & swagger
        String path = req.getRequestURI();
        if (path.equals("/ping") ||
                path.contains("swagger") ||
                path.contains("api-docs") ||
                path.contains("v3/api-docs")) {

            chain.doFilter(request, response);
            return;
        }

        // Extract API key
        String clientKey = req.getHeader("X-API-KEY");

        if (clientKey == null || clientKey.isBlank()) {
            unauthorized(res, "Missing API Key");
            return;
        }

        // 1️⃣ Accept internal Railway key
        if (internalApiKey != null && !internalApiKey.isBlank() && clientKey.equals(internalApiKey)) {
            chain.doFilter(request, response);
            return;
        }

        // 2️⃣ Accept RapidAPI user keys (alphanumeric 20–60 chars)
        // RapidAPI keys match this pattern consistently
        if (clientKey.matches("^[A-Za-z0-9]{20,60}$")) {
            chain.doFilter(request, response);
            return;
        }

        // Otherwise reject
        unauthorized(res, "Invalid API Key");
    }

    private void unauthorized(HttpServletResponse res, String message) throws IOException {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType("application/json");
        res.getWriter().write("{\"success\":false,\"message\":\"" + message + "\"}");
    }
}