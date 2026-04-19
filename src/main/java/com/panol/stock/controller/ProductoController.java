package com.panol.stock.controller;

import com.panol.stock.dto.ProductoDetalleResponse;
import com.panol.stock.dto.ProductoRequest;
import com.panol.stock.dto.ProductoResponse;
import com.panol.stock.dto.ProductoStockBajoResponse;
import com.panol.stock.service.ProductoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
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
    public ProductoResponse crear(@RequestBody ProductoRequest request, HttpServletRequest httpRequest) {

        String rol = (String) httpRequest.getAttribute("rol");
        String username = (String) httpRequest.getAttribute("username");


        return service.crear(request,rol,username);
    }

    //LISTAR
    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(service.listar());
        } catch (Exception e) {
            e.printStackTrace(); // 🔥 esto aparece en logs de Railway
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ProductoResponse actualizar(@PathVariable Long id,
                                       @RequestBody ProductoRequest request,
                                       HttpServletRequest httpRequest) {
        String rol = (String) httpRequest.getAttribute("rol");
        return service.actualizar(id, request,rol);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id,HttpServletRequest httpRequest) {
        String rol = (String) httpRequest.getAttribute("rol");
        service.eliminar(id,rol);
    }


}
