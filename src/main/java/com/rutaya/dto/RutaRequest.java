package com.rutaya.dto;
import jakarta.validation.constraints.*;
public class RutaRequest {
    @NotBlank private String origen;
    @NotBlank private String destino;
    @NotNull private Double precio;
    private String duracion;
    private String salidas;
    private String empresa;
    private String emoji;
    private String color;

    public String getOrigen(){return origen;} public void setOrigen(String o){this.origen=o;}
    public String getDestino(){return destino;} public void setDestino(String d){this.destino=d;}
    public Double getPrecio(){return precio;} public void setPrecio(Double p){this.precio=p;}
    public String getDuracion(){return duracion;} public void setDuracion(String d){this.duracion=d;}
    public String getSalidas(){return salidas;} public void setSalidas(String s){this.salidas=s;}
    public String getEmpresa(){return empresa;} public void setEmpresa(String e){this.empresa=e;}
    public String getEmoji(){return emoji;} public void setEmoji(String e){this.emoji=e;}
    public String getColor(){return color;} public void setColor(String c){this.color=c;}
}
