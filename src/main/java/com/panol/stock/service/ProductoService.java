package com.panol.stock.service;

import com.panol.stock.dto.*;
import com.panol.stock.entity.Producto;
import com.panol.stock.repository.ProductoRepository;
import com.panol.stock.entity.Proveedor;
import com.panol.stock.repository.ProveedorRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final MovimientoStockService movimientoService;
    private final ProveedorRepository proveedorRepository;

    public ProductoService(ProductoRepository productoRepository,
                           MovimientoStockService movimientoService,
                           ProveedorRepository proveedorRepository) {
        this.productoRepository = productoRepository;
        this.movimientoService = movimientoService;
        this.proveedorRepository = proveedorRepository;
    }

    private void validarEntrada(String rol) {
        if (rol.equals("OPERARIO")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tiene permiso");
        }
    }

    public ProductoDetalleResponse obtenerDetalle(Long productoId) {

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto no encontrado"));

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

        List<Producto> productos = productoRepository.findProductosConStockBajo();

        return productos.stream()
                .map(p -> new ProductoStockBajoResponse(
                        p.getId(),
                        p.getNombre(),
                        p.getCantidad(),
                        p.getStockMinimo()
                ))
                .toList();
    }

    public ProductoResponse crear(ProductoRequest request, String rol, String username) {

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
        producto.setFechaAlta(LocalDate.now());

        productoRepository.findByCodigo(request.getCodigo())
                .ifPresent(p -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Codigo Duplicado");
                });
        if (request.getProveedorId() != null) {
            Proveedor proveedor = proveedorRepository.findById(request.getProveedorId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Proveedor no encontrado"));

            producto.setProveedor(proveedor);
        }

        Producto guardado = productoRepository.save(producto);

// 🔥 CREAR MOVIMIENTO INICIAL
        if (request.getCantidad() > 0) {
            movimientoService.registrarEntradaInicial(
                    guardado.getId(),
                    request.getCantidad(),
                    "Stock inicial",
                    username
            );
        }


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
                .map(p -> {
                            boolean sinStock = p.getCantidad() == 0;
                            boolean stockBajo = p.getCantidad() > 0 && p.getCantidad() <= p.getStockMinimo();
                            Long proveedorId = p.getProveedor() != null ? p.getProveedor().getId() : null;
                            String proveedorNombre = p.getProveedor() != null ? p.getProveedor().getNombre() : null;


                            return new ProductoResponse(
                                    p.getId(),
                                    p.getCodigo(),
                                    p.getNombre(),
                                    p.getCantidad(),
                                    p.getUrlImagen(),
                                    p.getCategoria(),
                                    sinStock,
                                    stockBajo,
                                    proveedorId,
                                    proveedorNombre
                            );
                        }
                )
                .toList();
    }


    public ProductoResponse obtener(Long id) {

        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto no encontrado"));

        boolean sinStock = p.getCantidad() == 0;
        boolean stockBajo = p.getCantidad() > 0 && p.getCantidad() <= p.getStockMinimo();

        Long proveedorId = p.getProveedor() != null ? p.getProveedor().getId() : null;
        String proveedorNombre = p.getProveedor() != null ? p.getProveedor().getNombre() : null;

        return new ProductoResponse(
                p.getId(),
                p.getCodigo(),
                p.getNombre(),
                p.getCantidad(),
                p.getUrlImagen(),
                p.getCategoria(),
                sinStock,
                stockBajo,
                proveedorId,
                proveedorNombre
        );
    }

    public ProductoResponse actualizar(Long id, ProductoRequest request, String rol) {

        validarEntrada(rol);

        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto no encontrado"));

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

        if (request.getProveedorId() != null) {
            Proveedor proveedor = proveedorRepository.findById(request.getProveedorId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Proveedor no encontrado"));

            p.setProveedor(proveedor);
        }

        Producto actualizado = productoRepository.save(p);

        return new ProductoResponse(
                actualizado.getId(),
                actualizado.getCodigo(),
                actualizado.getNombre(),
                actualizado.getCantidad()
        );
    }

    public void eliminar(Long id, String rol) {

        validarEntrada(rol);

        if (rol.equals("PANOLERO")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tiene permiso");
        }

        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto no encontrado"));

        p.setActivo(false);
        productoRepository.save(p);
    }


}
