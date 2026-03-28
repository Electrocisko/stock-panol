package com.panol.stock.dto;

public class ProductoResponse {

    private Long id;
    private String codigo;
    private String nombre;
    private int cantidad;

    public ProductoResponse() {}

    public ProductoResponse(Long id, String codigo, String nombre, int cantidad) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getCantidad() { return cantidad; }
}
