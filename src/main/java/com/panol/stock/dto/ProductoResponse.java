package com.panol.stock.dto;

public class ProductoResponse {

    private Long id;
    private String codigo;
    private String nombre;
    private int cantidad;
    private String urlImagen;
    private String categoria;
    private boolean sinStock;
    private boolean stockBajo;

    public ProductoResponse() {
    }

    public ProductoResponse(Long id, String codigo, String nombre, int cantidad) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public ProductoResponse(Long id, String codigo, String nombre, int cantidad, String categoria) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.categoria = categoria;
    }

    public ProductoResponse(Long id, String codigo, String nombre, int cantidad, String urlImagen, String categoria) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.urlImagen = urlImagen;
        this.categoria = categoria;
    }

    public ProductoResponse(Long id, String codigo, String nombre, int cantidad, String urlImagen, String categoria, boolean sinStock, boolean stockBajo) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.urlImagen = urlImagen;
        this.categoria = categoria;
        this.sinStock = sinStock;
        this.stockBajo = stockBajo;
    }

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean isSinStock() {
        return sinStock;
    }

    public void setSinStock(boolean sinStock) {
        this.sinStock = sinStock;
    }

    public boolean isStockBajo() {
        return stockBajo;
    }

    public void setStockBajo(boolean stockBajo) {
        this.stockBajo = stockBajo;
    }
}
