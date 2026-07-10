package com.rutaya.dto;
import jakarta.validation.constraints.*;
public class RegistroRequest {
    @NotBlank private String nombre;
    private String apellido;
    @NotBlank @Email private String email;
    private String telefono;
    @NotBlank @Size(min=6) private String password;
    public String getNombre(){return nombre;} public void setNombre(String n){this.nombre=n;}
    public String getApellido(){return apellido;} public void setApellido(String a){this.apellido=a;}
    public String getEmail(){return email;} public void setEmail(String e){this.email=e;}
    public String getTelefono(){return telefono;} public void setTelefono(String t){this.telefono=t;}
    public String getPassword(){return password;} public void setPassword(String p){this.password=p;}
}
