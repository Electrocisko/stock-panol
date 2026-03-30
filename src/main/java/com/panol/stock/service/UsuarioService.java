package com.panol.stock.service;

import com.panol.stock.dto.*;
import com.panol.stock.entity.*;
import com.panol.stock.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse registrar(UsuarioRequest request) {

        // 🔴 Validar username único
        usuarioRepository.findByUsername(request.getUsername())
                .ifPresent(u -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username ya registrado");
                });

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setUsername(request.getUsername());
        usuario.setPassword(request.getPassword()); // ⚠️ después lo encriptamos
        usuario.setRol(Rol.valueOf(request.getRol().toUpperCase()));
        usuario.setActivo(true);
        usuario.setFechaAlta(LocalDate.now());

        Usuario guardado = usuarioRepository.save(usuario);

        return new UsuarioResponse(
                guardado.getId(),
                guardado.getNombre(),
                guardado.getUsername(),
                guardado.getRol().name()
        );
    }

    public LoginResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuario no registrado"));

        if (!usuario.getPassword().equals(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password Incorrecto");
        }

        if (!usuario.isActivo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuario No encontrado");
        }

        return new LoginResponse(
                "Login exitoso",
                usuario.getUsername(),
                usuario.getRol().name()
        );
    }

}
