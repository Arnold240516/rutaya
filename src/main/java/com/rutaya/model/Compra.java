package com.rutaya.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "compras")
public class Compra {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "ruta_id") private Ruta ruta;
    @Column(name = "numero_asiento", nullable = false) private Integer numeroAsiento;
    @Column(name = "nombre_pasajero", length = 120) private String nombrePasajero;
    @Column(name = "apellido_pasajero", length = 80) private String apellidoPasajero;
    @Column(name = "email_pasajero", length = 120) private String emailPasajero;
    @Column(name = "telefono_pasajero", length = 20) private String telefonoPasajero;
    @Column(name = "dni_pasajero", length = 20) private String dniPasajero;
    @Column(name = "metodo_pago", length = 30) private String metodoPago;
    @Column(name = "codigo_ticket", unique = true, length = 20) private String codigoTicket;
    @Column(name = "fecha_viaje") private LocalDate fechaViaje;
    @Column(name = "fecha_compra", nullable = false) private LocalDateTime fechaCompra = LocalDateTime.now();
    @Column(nullable = false, length = 20) private String estado = "CONFIRMADO";
    @Column(nullable = false) private Double total;

    public Compra() {}
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Ruta getRuta() { return ruta; } public void setRuta(Ruta r) { this.ruta = r; }
    public Integer getNumeroAsiento() { return numeroAsiento; } public void setNumeroAsiento(Integer n) { this.numeroAsiento = n; }
    public String getNombrePasajero() { return nombrePasajero; } public void setNombrePasajero(String n) { this.nombrePasajero = n; }
    public String getApellidoPasajero() { return apellidoPasajero; } public void setApellidoPasajero(String a) { this.apellidoPasajero = a; }
    public String getEmailPasajero() { return emailPasajero; } public void setEmailPasajero(String e) { this.emailPasajero = e; }
    public String getTelefonoPasajero() { return telefonoPasajero; } public void setTelefonoPasajero(String t) { this.telefonoPasajero = t; }
    public String getDniPasajero() { return dniPasajero; } public void setDniPasajero(String d) { this.dniPasajero = d; }
    public String getMetodoPago() { return metodoPago; } public void setMetodoPago(String m) { this.metodoPago = m; }
    public String getCodigoTicket() { return codigoTicket; } public void setCodigoTicket(String c) { this.codigoTicket = c; }
    public LocalDate getFechaViaje() { return fechaViaje; } public void setFechaViaje(LocalDate f) { this.fechaViaje = f; }
    public LocalDateTime getFechaCompra() { return fechaCompra; } public void setFechaCompra(LocalDateTime f) { this.fechaCompra = f; }
    public String getEstado() { return estado; } public void setEstado(String e) { this.estado = e; }
    public Double getTotal() { return total; } public void setTotal(Double t) { this.total = t; }
}
