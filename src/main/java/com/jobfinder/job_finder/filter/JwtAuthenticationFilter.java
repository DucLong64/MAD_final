package com.jobfinder.job_finder.filter;

import com.jobfinder.job_finder.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.annotation.WebFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            try {
                token = token.substring(7);  // Remove "Bearer " prefix
                String username = jwtUtil.extractUsername(token);

                if (username != null && jwtUtil.validateToken(token, username)) {
                    // You can set authentication details in the security context if needed
                }

            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Expired token");
            }
        }

        chain.doFilter(request, response);  // Continue with the request
    }
    @Override
    public void destroy() {
    }
}
