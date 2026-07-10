package com.rutaya.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank @Column(nullable = false, length = 80) private String nombre;
    @Column(length = 80) private String apellido;
    @NotBlank @Email @Column(nullable = false, unique = true, length = 120) private String email;
    @Column(length = 20) private String telefono;
    @NotBlank @Column(nullable = false) private String password;
    @Column(name = "fecha_registro", nullable = false, updatable = false) private LocalDateTime fechaRegistro = LocalDateTime.now();

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; } public void setNombre(String n) { this.nombre = n; }
    public String getApellido() { return apellido; } public void setApellido(String a) { this.apellido = a; }
    public String getEmail() { return email; } public void setEmail(String e) { this.email = e; }
    public String getTelefono() { return telefono; } public void setTelefono(String t) { this.telefono = t; }
    public String getPassword() { return password; } public void setPassword(String p) { this.password = p; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; } public void setFechaRegistro(LocalDateTime f) { this.fechaRegistro = f; }
}
