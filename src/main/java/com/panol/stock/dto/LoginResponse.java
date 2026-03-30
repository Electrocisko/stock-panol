package com.panol.stock.dto;

public class LoginResponse {

    private String mensaje;
    private String username;
    private String rol;

    public LoginResponse() {}

    public LoginResponse(String mensaje, String username, String rol) {
        this.mensaje = mensaje;
        this.username = username;
        this.rol = rol;
    }

    public String getMensaje() { return mensaje; }
    public String getUsername() { return username; }
    public String getRol() { return rol; }
}
