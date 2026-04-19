package com.panol.stock.service;

import com.panol.stock.dto.*;
import com.panol.stock.entity.*;
import com.panol.stock.repository.UsuarioRepository;
import com.panol.stock.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // =========================
    // REGISTRO
    // =========================
    public UsuarioResponse registrar(UsuarioRequest request) {

        usuarioRepository.findByUsernameIgnoreCase(request.getUsername())
                .ifPresent(u -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username ya registrado");
                });

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setUsername(request.getUsername().toLowerCase());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(Rol.OPERARIO);
        usuario.setActivo(true);
        usuario.setFechaAlta(LocalDate.now());

        Usuario guardado = usuarioRepository.save(usuario);

        return new UsuarioResponse(
                guardado.getId(),
                guardado.getNombre(),
                guardado.getApellido(),
                guardado.getUsername(),
                guardado.getRol().name()
        );
    }

    // =========================
    // LOGIN
    // =========================
    public LoginResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByUsernameIgnoreCase(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no registrado"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password incorrecta");
        }

        if (!usuario.isActivo()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario inactivo");
        }

        String token = jwtService.generarToken(
                usuario.getUsername(),
                usuario.getRol().name()
        );

        return new LoginResponse(
                token,
                usuario.getUsername(),
                usuario.getRol().name()
        );
    }

    // =========================
    // LIST (ADMIN)
    // =========================
    public List<UsuarioResponse> getUsuarios() {

        return usuarioRepository.findAll().stream()
                .map(u -> new UsuarioResponse(
                        u.getId(),
                        u.getNombre(),
                        u.getApellido(),
                        u.getUsername(),
                        u.getRol().name()
                ))
                .toList();
    }

    // =========================
    // RESET PASSWORD (ADMIN)
    // =========================
    public void resetPassword(Long userId, String newPassword, String rol) {

        if (rol == null || !rol.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No autorizado");
        }

        String cleanPassword = newPassword == null ? null : newPassword.trim();

        if (cleanPassword == null || cleanPassword.length() < 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La password debe tener al menos 4 caracteres");
        }

        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        usuario.setPassword(passwordEncoder.encode(cleanPassword));

        usuarioRepository.save(usuario);
    }
}