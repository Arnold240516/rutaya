package com.rutaya.dto;
import jakarta.validation.constraints.*;
public class CompraRequest {
    @NotNull private Long rutaId;
    @NotNull private Integer numeroAsiento;
    @NotBlank private String metodoPago;
    @NotBlank private String fechaViaje;
    private String nombrePasajero;
    private String apellidoPasajero;
    private String emailPasajero;
    private String telefonoPasajero;
    private String dniPasajero;

    public Long getRutaId(){return rutaId;} public void setRutaId(Long r){this.rutaId=r;}
    public Integer getNumeroAsiento(){return numeroAsiento;} public void setNumeroAsiento(Integer n){this.numeroAsiento=n;}
    public String getMetodoPago(){return metodoPago;} public void setMetodoPago(String m){this.metodoPago=m;}
    public String getFechaViaje(){return fechaViaje;} public void setFechaViaje(String f){this.fechaViaje=f;}
    public String getNombrePasajero(){return nombrePasajero;} public void setNombrePasajero(String n){this.nombrePasajero=n;}
    public String getApellidoPasajero(){return apellidoPasajero;} public void setApellidoPasajero(String a){this.apellidoPasajero=a;}
    public String getEmailPasajero(){return emailPasajero;} public void setEmailPasajero(String e){this.emailPasajero=e;}
    public String getTelefonoPasajero(){return telefonoPasajero;} public void setTelefonoPasajero(String t){this.telefonoPasajero=t;}
    public String getDniPasajero(){return dniPasajero;} public void setDniPasajero(String d){this.dniPasajero=d;}
}
