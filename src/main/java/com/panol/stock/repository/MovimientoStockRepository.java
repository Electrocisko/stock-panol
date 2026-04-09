package com.panol.stock.repository;

import com.panol.stock.entity.MovimientoStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoStockRepository extends JpaRepository<MovimientoStock, Long> {


    List<MovimientoStock> findByProductoIdOrderByFechaDesc(Long productoId);

    List<MovimientoStock> findByUsuarioId(Long usuarioId);


}
