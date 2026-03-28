package com.panol.stock.controller;

import com.panol.stock.dto.MovimientoRequest;
import com.panol.stock.dto.MovimientoResponse;
import com.panol.stock.service.MovimientoStockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/movimientos")
public class MovimientoStockController {

    private final MovimientoStockService service;

    public MovimientoStockController(MovimientoStockService service) {
        this.service = service;
    }

    //LISTAR
    @GetMapping
    public List<MovimientoResponse> listarTodos() {
        return service.listarTodos();
    }

    //LISTAR POR PRODUCT0
    @GetMapping("/producto/{productoId}")
    public List<MovimientoResponse> porProducto(@PathVariable Long productoId) {
        return service.buscarPorProducto(productoId);
    }

    //LISTAR POR USUARIO
    @GetMapping("/usuario/{usuarioId}")
    public List<MovimientoResponse> porUsuario(@PathVariable Long usuarioId) {
        return service.buscarPorUsuario(usuarioId);
    }

    // 🔴 SALIDA
    @PostMapping("/salida")
    public String salida(@RequestBody MovimientoRequest request) {

        service.registrarSalida(
                request.getProductoId(),
                request.getUsuarioId(),
                request.getCantidad(),
                request.getMotivo()
        );

        return "Salida registrada";
    }

    // 🟢 ENTRADA
    @PostMapping("/entrada")
    public String entrada(@RequestBody MovimientoRequest request) {

        service.registrarEntrada(
                request.getProductoId(),
                request.getUsuarioId(),
                request.getCantidad(),
                request.getMotivo()
        );

        return "Entrada registrada";
    }






}
