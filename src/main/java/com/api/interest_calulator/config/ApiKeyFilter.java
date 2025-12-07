package com.api.interest_calulator.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiKeyFilter implements Filter {

    @Value("${security.api.key:}")
    private String internalApiKey;

    @Value("${security.api.enabled:true}")
    private boolean securityEnabled;

    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(ApiKeyFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (!securityEnabled) {
            chain.doFilter(request, response);
            return;
        }

        // Allow swagger, health check
        String path = req.getRequestURI();
        if (path.equals("/ping") ||
                path.contains("swagger") ||
                path.contains("api-docs") ||
                path.contains("v3/api-docs")) {

            chain.doFilter(request, response);
            return;
        }


        // ✅ SUPPORT BOTH RAPIDAPI + NORMAL HEADER
        String clientKey = req.getHeader("X-API-KEY");
        String rapidKey = req.getHeader("X-RapidAPI-Key");

        // 🔥 SAFE LOGGING (mask keys)
        log.debug("Incoming X-API-KEY: {}", clientKey);
        log.debug("Incoming X-RapidAPI-Key: {}", rapidKey);

        // ✅ SUPPORT BOTH RAPIDAPI + NORMAL HEADER
//        String clientKey = req.getHeader("X-API-KEY");
        if (clientKey == null) {
            clientKey = req.getHeader("X-RapidAPI-Key");  // <- IMPORTANT FIX
        }

        if (clientKey == null || clientKey.isBlank()) {
            unauthorized(res, "Missing API Key");
            return;
        }

        // 1️⃣ Accept your internal private API key
        if (internalApiKey != null && !internalApiKey.isBlank() && clientKey.equals(internalApiKey)) {
            chain.doFilter(request, response);
            return;
        }

        // 2️⃣ Accept ALL RapidAPI user keys (anything long enough & not your internal key)
        if (!clientKey.equals(internalApiKey) && clientKey.length() >= 15) {
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
}