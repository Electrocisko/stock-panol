package com.panol.stock.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MovimientoStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad;

    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipo;

    private String motivo;

    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public MovimientoStock() {}

    public MovimientoStock(Long id, int cantidad, TipoMovimiento tipo, String motivo,
                           LocalDateTime fecha, Producto producto, Usuario usuario) {
        this.id = id;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.motivo = motivo;
        this.fecha = fecha;
        this.producto = producto;
        this.usuario = usuario;
    }

    public Long getId() { return id; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public TipoMovimiento getTipo() { return tipo; }
    public void setTipo(TipoMovimiento tipo) { this.tipo = tipo; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
