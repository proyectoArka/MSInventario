package com.Arka.MSInventario.infrastructure.adapters.repository;

import com.Arka.MSInventario.infrastructure.adapters.entity.HistorialStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HistorialStockRepository extends JpaRepository<HistorialStockEntity, Long> {
    Optional<HistorialStockEntity> findById(Long id);

    @Query("SELECT h FROM HistorialStockEntity h WHERE h.IdProducto.id = :productoId ORDER BY h.fecha_Cambio DESC")
    List<HistorialStockEntity> findByProductoIdOrderByFechaCambioDesc(@Param("productoId") Long productoId);
}
