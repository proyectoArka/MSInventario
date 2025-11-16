package com.Arka.MSInventario.infrastructure.adapters.repository;

import com.Arka.MSInventario.infrastructure.adapters.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    Optional<ProductoEntity> findByNombre(String nombre);
    // En ProductoRepository
    List<ProductoEntity> findByNombreContainingIgnoreCase(String nombre);
    List<ProductoEntity> findByCategoria_Id(Long categoriaId);
    Optional<ProductoEntity> findById(Long id);
    boolean existsByCategoria_Id(Long categoriaId);
}
