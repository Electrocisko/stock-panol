package com.panol.stock.controller;

import com.panol.stock.dto.ProductoDetalleResponse;
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

    // OBTENER STOCK Y DETALLE DE MOVIMIENTOS
    @GetMapping("/{id}/detalle")
    public ProductoDetalleResponse detalle(@PathVariable Long id) {
        return service.obtenerDetalle(id);
    }

    // OBTENER STOCK BAJO
    @GetMapping("/stock-bajo")
    public List<ProductoStockBajoResponse> stockBajo() {
        return service.obtenerStockBajo();
    }
}
