package com.rutaya.controller;
import com.rutaya.dto.ApiResponse;
import com.rutaya.security.SessionStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {
    private final SessionStore sessionStore;
    @Value("${rutaya.admin.usuario}") private String adminUsuario;
    @Value("${rutaya.admin.password}") private String adminPassword;

    public AdminAuthController(SessionStore sessionStore) { this.sessionStore = sessionStore; }

    public static class AdminLoginRequest { public String usuario; public String password; }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody AdminLoginRequest req) {
        if (req.usuario==null||req.password==null||!req.usuario.equals(adminUsuario)||!req.password.equals(adminPassword))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,"Credenciales incorrectas",null));
        String token = sessionStore.crearSesion(0L,adminUsuario,"ADMIN");
        Map<String,Object> data = new HashMap<>();
        data.put("token",token);
        return ResponseEntity.ok(new ApiResponse(true,"Bienvenido administrador",data));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(@RequestHeader(value="X-Admin-Token",required=false) String token) {
        if (token!=null) sessionStore.eliminar(token);
        return ResponseEntity.ok(new ApiResponse(true,"Sesión cerrada",null));
    }
}
