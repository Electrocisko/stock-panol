package com.panol.stock.dto;

import com.panol.stock.entity.UnidadMedida;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class ProductoResponse {

    private Long id;
    private String codigo;
    private String nombre;
    private int cantidad;
    private String urlImagen;
    private String categoria;
    private boolean sinStock;
    private boolean stockBajo;
    private Long proveedorId;
    private String proveedorNombre;
    private String descripcion;
    private UnidadMedida unidadMedida;
    private String unidadTexto;
    private boolean activo;


    public ProductoResponse(Long id, String codigo, String nombre, int cantidad) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }


    public ProductoResponse(Long id, String codigo, String nombre, int cantidad, String urlImagen, String categoria,
                            String descripcion, UnidadMedida unidadMedida, boolean sinStock, boolean stockBajo,
                            Long proveedorId, String proveedorNombre, boolean activo) {

        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.urlImagen = urlImagen;
        this.categoria = categoria;
        this.sinStock = sinStock;
        this.stockBajo = stockBajo;
        this.proveedorId = proveedorId;
        this.proveedorNombre = proveedorNombre;
        this.unidadMedida = unidadMedida;
        this.descripcion = descripcion;
        this.unidadTexto = unidadMedida != null
                ? unidadMedida.getTexto(cantidad)
                : "";
        this.activo = activo;

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

    public Long getProveedorId() {return proveedorId;}

    public void setProveedorId(Long proveedorId) {this.proveedorId = proveedorId;}

    public String getProveedorNombre() {return proveedorNombre;}

    public void setProveedorNombre(String proveedorNombre) {this.proveedorNombre = proveedorNombre;}

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getUnidadTexto() {
        return unidadTexto;
    }

    public void setUnidadTexto(String unidadTexto) {
        this.unidadTexto = unidadTexto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
