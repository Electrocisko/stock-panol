package com.panol.stock.controller;

import com.panol.stock.dto.*;
import com.panol.stock.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public UsuarioResponse registrar(@RequestBody UsuarioRequest request) {
        return service.registrar(request);
    }
}
