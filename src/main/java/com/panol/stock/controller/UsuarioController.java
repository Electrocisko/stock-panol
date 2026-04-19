package com.panol.stock.controller;

import com.panol.stock.dto.*;
import com.panol.stock.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    // =========================
    // REGISTER
    // =========================
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> registrar(@RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(service.registrar(request));
    }

    // =========================
    // LOGIN
    // =========================
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    //Listar
    @GetMapping
    public ResponseEntity<  List<UsuarioResponse>> getUsuarios(HttpServletRequest request) {

        String rol = (String) request.getAttribute("rol");

        if (rol == null || !rol.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No autorizado");
        }

        return ResponseEntity.ok(service.getUsuarios());
    }



    // =========================
    // RESET PASSWORD (ADMIN)
    // =========================
    @PutMapping("/{id}/reset-password")
    public ResponseEntity<String> resetPassword(@PathVariable Long id,
                                                @RequestBody ResetPasswordRequest request,
                                                HttpServletRequest httpRequest) {

        String rol = (String) httpRequest.getAttribute("rol");

        service.resetPassword(id, request.getNewPassword(),rol);

        return ResponseEntity.ok("Password reseteado correctamente");
    }
}