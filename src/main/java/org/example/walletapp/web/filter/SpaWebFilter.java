package org.example.walletapp.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class SpaWebFilter extends OncePerRequestFilter {

    /**
     * Forwards any unmapped paths (except those containing a period) to the client {@code index.html}.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        System.out.println("Processing path: " + path); // Log the path for debugging

        // Check the path conditions that shouldn't be redirected to index.html
        if (!path.startsWith("/swagger-ui/") && // Exclude Swagger UI path from redirection
                !path.startsWith("/wallet-app") &&
                !path.startsWith("/api") &&
                !path.startsWith("/management") &&
                !path.startsWith("/v3/api-docs") &&
                !path.startsWith("/h2-console") &&
                !path.contains(".") &&
                path.matches("/(.*)")) {
            System.out.println("Forwarding to index.html"); // Log before forwarding
            request.getRequestDispatcher("/index.html").forward(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }


}
