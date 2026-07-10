package com.rutaya.model;
import jakarta.persistence.*;

@Entity
@Table(name = "rutas")
public class Ruta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 80) private String origen;
    @Column(nullable = false, length = 80) private String destino;
    @Column(nullable = false) private Double precio;
    @Column(length = 40) private String duracion;
    @Column(length = 40) private String salidas;
    @Column(length = 80) private String empresa;
    @Column(length = 10) private String emoji;
    @Column(length = 20) private String color;
    @Column(name = "total_asientos", nullable = false) private Integer totalAsientos = 40;

    public Ruta() {}
    public Ruta(String o, String d, Double p, String dur, String sal, String emp, String emoji, String color) {
        this.origen=o; this.destino=d; this.precio=p; this.duracion=dur;
        this.salidas=sal; this.empresa=emp; this.emoji=emoji; this.color=color;
    }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getOrigen() { return origen; } public void setOrigen(String o) { this.origen = o; }
    public String getDestino() { return destino; } public void setDestino(String d) { this.destino = d; }
    public Double getPrecio() { return precio; } public void setPrecio(Double p) { this.precio = p; }
    public String getDuracion() { return duracion; } public void setDuracion(String d) { this.duracion = d; }
    public String getSalidas() { return salidas; } public void setSalidas(String s) { this.salidas = s; }
    public String getEmpresa() { return empresa; } public void setEmpresa(String e) { this.empresa = e; }
    public String getEmoji() { return emoji; } public void setEmoji(String e) { this.emoji = e; }
    public String getColor() { return color; } public void setColor(String c) { this.color = c; }
    public Integer getTotalAsientos() { return totalAsientos; } public void setTotalAsientos(Integer t) { this.totalAsientos = t; }
}
