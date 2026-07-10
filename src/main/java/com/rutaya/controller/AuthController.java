package com.rutaya.controller;
import com.rutaya.dto.*;
import com.rutaya.model.Usuario;
import com.rutaya.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/registro")
    public ResponseEntity<ApiResponse> registrar(@Valid @RequestBody RegistroRequest req) {
        if (usuarioRepository.existsByEmail(req.getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(false,"Ya existe una cuenta con ese correo",null));
        Usuario u = new Usuario();
        u.setNombre(req.getNombre()); u.setApellido(req.getApellido());
        u.setEmail(req.getEmail()); u.setTelefono(req.getTelefono());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        usuarioRepository.save(u);
        Map<String,Object> data = new HashMap<>();
        data.put("nombre", u.getNombre()); data.put("email", u.getEmail());
        return ResponseEntity.ok(new ApiResponse(true,"Cuenta creada exitosamente",data));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest req) {
        Optional<Usuario> opt = usuarioRepository.findByEmail(req.getEmail());
        if (opt.isEmpty() || !passwordEncoder.matches(req.getPassword(), opt.get().getPassword()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,"Correo o contraseña incorrectos",null));
        Usuario u = opt.get();
        Map<String,Object> data = new HashMap<>();
        data.put("id",u.getId()); data.put("nombre",u.getNombre());
        data.put("apellido",u.getApellido()); data.put("email",u.getEmail());
        return ResponseEntity.ok(new ApiResponse(true,"Bienvenido "+u.getNombre(),data));
    }
}
