package com.rutaya.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class AdminAuthFilter extends OncePerRequestFilter {
    private final SessionStore sessionStore;
    public AdminAuthFilter(SessionStore sessionStore) { this.sessionStore = sessionStore; }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String path = req.getRequestURI();
        boolean protegida = path.startsWith("/api/admin") && !path.equals("/api/admin/login");
        if (protegida) {
            String token = req.getHeader("X-Admin-Token");
            if (!sessionStore.esAdmin(token)) {
                res.setStatus(401);
                res.setContentType("application/json");
                res.getWriter().write("{\"ok\":false,\"mensaje\":\"No autorizado\"}");
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
