package com.rutaya.dto;
public class ApiResponse {
    private boolean ok;
    private String mensaje;
    private Object data;
    public ApiResponse(boolean ok, String mensaje, Object data) { this.ok=ok; this.mensaje=mensaje; this.data=data; }
    public boolean isOk() { return ok; } public void setOk(boolean ok) { this.ok=ok; }
    public String getMensaje() { return mensaje; } public void setMensaje(String m) { this.mensaje=m; }
    public Object getData() { return data; } public void setData(Object d) { this.data=d; }
}
