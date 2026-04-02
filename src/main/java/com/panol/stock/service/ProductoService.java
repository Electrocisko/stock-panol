package com.panol.stock.service;

import com.panol.stock.dto.*;
import com.panol.stock.entity.Producto;
import com.panol.stock.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final MovimientoStockService movimientoService;

    public ProductoService(ProductoRepository productoRepository,
                           MovimientoStockService movimientoService) {
        this.productoRepository = productoRepository;
        this.movimientoService = movimientoService;
    }

    private void validarEntrada(String rol) {
        if (rol.equals("OPERARIO")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tiene permiso");
        }
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

    public ProductoResponse crear(ProductoRequest request,String rol) {

        validarEntrada(rol);

        Producto producto = new Producto();
        producto.setCodigo(request.getCodigo());
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setCantidad(request.getCantidad());
        producto.setCategoria(request.getCategoria());
        producto.setUnidadMedida(request.getUnidadMedida());
        producto.setStockMinimo(request.getStockMinimo());
        producto.setUbicacion(request.getUbicacion());
        producto.setUrlImagen(request.getUrlImagen());
        producto.setActivo(true);

        productoRepository.findByCodigo(request.getCodigo())
                .ifPresent(p -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El código ya existe");
                });

        Producto guardado = productoRepository.save(producto);

        return new ProductoResponse(
                guardado.getId(),
                guardado.getCodigo(),
                guardado.getNombre(),
                guardado.getCantidad()
        );
    }

    public List<ProductoResponse> listar() {
        return productoRepository.findByActivoTrue()
                .stream()
                .map(p -> new ProductoResponse(
                        p.getId(),
                        p.getCodigo(),
                        p.getNombre(),
                        p.getCantidad()
                ))
                .toList();
    }

    public ProductoResponse obtener(Long id) {

        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Producto no encontrado"));

        return new ProductoResponse(
                p.getId(),
                p.getCodigo(),
                p.getNombre(),
                p.getCantidad()
        );
    }

    public ProductoResponse actualizar(Long id, ProductoRequest request, String rol) {

        validarEntrada(rol);

        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Producto no encontrado"));

        p.setCodigo(request.getCodigo());
        p.setNombre(request.getNombre());
        p.setDescripcion(request.getDescripcion());
        p.setCategoria(request.getCategoria());
        p.setUnidadMedida(request.getUnidadMedida());
        p.setStockMinimo(request.getStockMinimo());
        p.setUbicacion(request.getUbicacion());
        p.setUrlImagen(request.getUrlImagen());

        Optional<Producto> existente = productoRepository.findByCodigo(request.getCodigo());

        if (existente.isPresent() && !existente.get().getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El código ya existe");
        }

        Producto actualizado = productoRepository.save(p);

        return new ProductoResponse(
                actualizado.getId(),
                actualizado.getCodigo(),
                actualizado.getNombre(),
                actualizado.getCantidad()
        );
    }

    public void eliminar(Long id,String rol) {

        validarEntrada(rol);

        if (rol.equals("PANOLERO")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tiene permiso");
        }

        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Producto no encontrado"));

        p.setActivo(false);
        productoRepository.save(p);
    }


}
