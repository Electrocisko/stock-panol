package com.panol.stock.repository;

import com.panol.stock.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCantidadLessThanEqual(int stockMinimo);

    List<Producto> findByActivoTrue();

    Optional<Producto> findByCodigo(String codigo);
}