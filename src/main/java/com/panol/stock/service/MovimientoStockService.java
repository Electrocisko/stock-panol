package com.panol.stock.service;

import com.panol.stock.dto.MovimientoResponse;
import com.panol.stock.entity.*;
import com.panol.stock.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimientoStockService {

    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MovimientoStockRepository movimientoRepository;

    public MovimientoStockService(ProductoRepository productoRepository,
                                  UsuarioRepository usuarioRepository,
                                  MovimientoStockRepository movimientoRepository) {
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.movimientoRepository = movimientoRepository;
    }

    // LISTAR TODOS
    public List<MovimientoResponse> listarTodos() {
        return movimientoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //LISTAR POR PRODUCTO
    public List<MovimientoResponse> buscarPorProducto(Long productoId) {
        return movimientoRepository.findByProductoId(productoId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //LISTAR POR USUARIO
    public List<MovimientoResponse> buscarPorUsuario(Long usuarioId) {
        return movimientoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // 🔴 SALIDA DE STOCK
    public void registrarSalida(Long productoId, String username, int cantidad, String motivo) {

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Producto no encontrado"));

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuario no encontrado"));

        // ⚠️ VALIDACIÓN CLAVE
        if (producto.getCantidad() < cantidad) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Stock Insuficiente");
        }

        // 🔻 Actualizar stock
        producto.setCantidad(producto.getCantidad() - cantidad);
        productoRepository.save(producto);

        // 🧾 Crear movimiento
        MovimientoStock mov = new MovimientoStock();
        mov.setProducto(producto);
        mov.setUsuario(usuario);
        mov.setCantidad(cantidad);
        mov.setTipo(TipoMovimiento.SALIDA);
        mov.setMotivo(motivo);
        mov.setFecha(LocalDateTime.now());

        movimientoRepository.save(mov);
    }

    // 🟢 ENTRADA DE STOCK
    public void registrarEntrada(Long productoId, String username, int cantidad, String motivo) {

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Producto no encontrado"));

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuario no encontrado"));

        // 🔺 Sumar stock
        producto.setCantidad(producto.getCantidad() + cantidad);
        productoRepository.save(producto);

        // 🧾 Crear movimiento
        MovimientoStock mov = new MovimientoStock();
        mov.setProducto(producto);
        mov.setUsuario(usuario);
        mov.setCantidad(cantidad);
        mov.setTipo(TipoMovimiento.ENTRADA);
        mov.setMotivo(motivo);
        mov.setFecha(LocalDateTime.now());

        movimientoRepository.save(mov);
    }

    private MovimientoResponse toResponse(MovimientoStock mov) {
        return new MovimientoResponse(
                mov.getId(),
                mov.getProducto().getNombre(),
                mov.getUsuario().getNombre() + " " + mov.getUsuario().getApellido(),
                mov.getCantidad(),
                mov.getTipo().name(),
                mov.getMotivo(),
                mov.getFecha()
        );
    }
}
