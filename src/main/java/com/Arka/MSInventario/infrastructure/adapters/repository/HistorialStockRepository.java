package com.Arka.MSInventario.infrastructure.adapters.repository;

import com.Arka.MSInventario.infrastructure.adapters.entity.HistorialStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HistorialStockRepository extends JpaRepository<HistorialStockEntity, Long> {
    Optional<HistorialStockEntity> findById(Long id);
}
