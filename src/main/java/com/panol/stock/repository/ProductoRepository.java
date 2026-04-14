package com.panol.stock.repository;

import com.panol.stock.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p WHERE p.cantidad <= p.stockMinimo")
    List<Producto> findProductosConStockBajo();

    List<Producto> findByActivoTrue();

    Optional<Producto> findByCodigo(String codigo);
}