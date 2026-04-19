package com.panol.stock.repository;
import java.util.Optional;

import com.panol.stock.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByUsernameIgnoreCase(String username);
}
