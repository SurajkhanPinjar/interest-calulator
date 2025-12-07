package com.api.interest_calulator.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiKeyFilter implements Filter {

    @Value("${security.api.key:}")
    private String internalApiKey;  // Your Railway private API key

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

        // Allow Swagger in local environment
        String path = req.getRequestURI();
        if (path.startsWith("/swagger") || path.contains("api-docs") || path.contains("/v3/api-docs")) {
            chain.doFilter(request, response);
            return;
        }

        // Get incoming API key
        String clientKey = req.getHeader("X-API-KEY");

        if (clientKey == null || clientKey.isBlank()) {
            unauthorized(res, "Missing API Key");
            return;
        }

        // 1️⃣ Allow internal (Railway) private key
        if (internalApiKey != null && !internalApiKey.isBlank() && clientKey.equals(internalApiKey)) {
            chain.doFilter(request, response);
            return;
        }

        // 2️⃣ Allow RapidAPI dynamic user keys
        // RapidAPI keys are long & alphanumeric, so we allow non-empty keys that are not your private key
        if (clientKey.length() >= 20) {
            chain.doFilter(request, response);
            return;
        }

        // Otherwise: Reject
        unauthorized(res, "Invalid API Key");
    }

    private void unauthorized(HttpServletResponse res, String message) throws IOException {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType("application/json");
        res.getWriter().write("{\"success\":false,\"message\":\"" + message + "\"}");
    }
}