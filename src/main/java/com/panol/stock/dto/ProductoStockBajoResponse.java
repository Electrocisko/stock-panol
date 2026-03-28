package com.panol.stock.dto;

public class ProductoStockBajoResponse {

    private Long id;
    private String nombre;
    private int stock;
    private int stockMinimo;

    public ProductoStockBajoResponse() {}

    public ProductoStockBajoResponse(Long id, String nombre, int stock, int stockMinimo) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
    }

    public Long getId() { return id; }

    public String getNombre() { return nombre; }

    public int getStock() { return stock; }

    public int getStockMinimo() { return stockMinimo; }
}
