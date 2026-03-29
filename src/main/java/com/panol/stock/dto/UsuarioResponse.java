package com.panol.stock.dto;

public class UsuarioResponse {

    private Long id;
    private String nombre;
    private String username;
    private String rol;

    public UsuarioResponse() {}

    public UsuarioResponse(Long id, String nombre, String username, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.rol = rol;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getUsername() { return username; }
    public String getRol() { return rol; }
}
