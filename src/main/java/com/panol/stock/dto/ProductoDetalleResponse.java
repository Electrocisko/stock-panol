package com.panol.stock.dto;

import java.util.List;

public class ProductoDetalleResponse {

    private Long id;
    private String nombre;
    private int stock;
    private List<MovimientoResponse> movimientos;

    public ProductoDetalleResponse() {}

    public ProductoDetalleResponse(Long id, String nombre, int stock, List<MovimientoResponse> movimientos) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.movimientos = movimientos;
    }

    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public List<MovimientoResponse> getMovimientos() { return movimientos; }
    public void setMovimientos(List<MovimientoResponse> movimientos) { this.movimientos = movimientos; }
}
