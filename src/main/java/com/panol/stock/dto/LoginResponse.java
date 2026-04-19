package com.panol.stock.dto;

public class LoginResponse {

    private String token;
    private String username;
    private String rol;
    private String nombre;

    public LoginResponse() {}

    public LoginResponse(String token, String username, String rol, String nombre) {
        this.token = token;
        this.username = username;
        this.rol = rol;
        this.nombre = nombre;
    }

    public String getToken() { return token; }
    public String getUsername() { return username; }
    public String getRol() { return rol; }
    public String getNombre() {return nombre;}
}
