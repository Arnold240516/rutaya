package com.rutaya.security;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionStore {
    public static class SesionInfo {
        public Long usuarioId;
        public String email;
        public String rol;
    }
    private final Map<String, SesionInfo> sesiones = new ConcurrentHashMap<>();

    public String crearSesion(Long usuarioId, String email, String rol) {
        String token = UUID.randomUUID().toString();
        SesionInfo info = new SesionInfo();
        info.usuarioId = usuarioId; info.email = email; info.rol = rol;
        sesiones.put(token, info);
        return token;
    }
    public SesionInfo obtener(String token) { return token == null ? null : sesiones.get(token); }
    public void eliminar(String token) { sesiones.remove(token); }
    public boolean esAdmin(String token) {
        SesionInfo info = obtener(token);
        return info != null && "ADMIN".equals(info.rol);
    }
}
