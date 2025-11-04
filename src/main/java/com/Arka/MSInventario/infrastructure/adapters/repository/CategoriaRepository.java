package com.Arka.MSInventario.infrastructure.adapters.repository;

import com.Arka.MSInventario.infrastructure.adapters.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
    Optional<CategoriaEntity> findByid(Long id);
    Optional<CategoriaEntity> findByNombre(String nombre);
}
