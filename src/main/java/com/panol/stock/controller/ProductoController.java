package com.panol.stock.controller;

import com.panol.stock.dto.ProductoDetalleResponse;
import com.panol.stock.dto.ProductoRequest;
import com.panol.stock.dto.ProductoResponse;
import com.panol.stock.dto.ProductoStockBajoResponse;
import com.panol.stock.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    // OBTENER STOCK BAJO
    @GetMapping("/stock-bajo")
    public List<ProductoStockBajoResponse> stockBajo() {
        return service.obtenerStockBajo();
    }

    // OBTENER STOCK Y DETALLE DE MOVIMIENTOS
    @GetMapping("/{id}/detalle")
    public ProductoDetalleResponse detalle(@PathVariable Long id) {
        return service.obtenerDetalle(id);
    }

    //OBTENER POR ID
    @GetMapping("/{id}")
    public ProductoResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    // CREAR
    @PostMapping
    public ProductoResponse crear(@RequestBody ProductoRequest request) {
        return service.crear(request);
    }

    //LISTAR
    @GetMapping
    public List<ProductoResponse> listar() {
        return service.listar();
    }


    @PutMapping("/{id}")
    public ProductoResponse actualizar(@PathVariable Long id,
                                       @RequestBody ProductoRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }


}
