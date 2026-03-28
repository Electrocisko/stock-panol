package com.panol.stock.dto;

import java.time.LocalDateTime;

public class MovimientoResponse {

    private Long id;
    private String producto;
    private String usuario;
    private int cantidad;
    private String tipo;
    private String motivo;
    private LocalDateTime fecha;

    public MovimientoResponse() {}

    public MovimientoResponse(Long id, String producto, String usuario,
                              int cantidad, String tipo,
                              String motivo, LocalDateTime fecha) {
        this.id = id;
        this.producto = producto;
        this.usuario = usuario;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.motivo = motivo;
        this.fecha = fecha;
    }

    public Long getId() { return id; }

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
