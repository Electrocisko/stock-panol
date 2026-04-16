package com.panol.stock.controller;

import com.panol.stock.dto.*;
import com.panol.stock.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // =========================
    // RESET PASSWORD (ADMIN)
    // =========================
    @PutMapping("/{id}/reset-password")
    public ResponseEntity<String> resetPassword(@PathVariable Long id,
                                                @RequestBody ResetPasswordRequest request) {

        service.resetPassword(id, request.getNewPassword());

        return ResponseEntity.ok("Password reseteado correctamente");
    }
}