package com.panol.stock.service;

import com.panol.stock.dto.*;
import com.panol.stock.entity.Producto;
import com.panol.stock.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final MovimientoStockService movimientoService;

    public ProductoService(ProductoRepository productoRepository,
                           MovimientoStockService movimientoService) {
        this.productoRepository = productoRepository;
        this.movimientoService = movimientoService;
    }

    public ProductoDetalleResponse obtenerDetalle(Long productoId) {

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Producto no encontrado"));

        List<MovimientoResponse> movimientos =
                movimientoService.buscarPorProducto(productoId);

        return new ProductoDetalleResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getCantidad(),
                movimientos
        );
    }

    public List<ProductoStockBajoResponse> obtenerStockBajo() {

        List<Producto> productos = productoRepository.findAll();

        return productos.stream()
                .filter(p -> p.getCantidad() <= p.getStockMinimo())
                .map(p -> new ProductoStockBajoResponse(
                        p.getId(),
                        p.getNombre(),
                        p.getCantidad(),
                        p.getStockMinimo()
                ))
                .toList();
    }
}
