package com.Arka.MSInventario.domain.model.gateway;

import com.Arka.MSInventario.domain.model.Categoria;

import java.util.Optional;

public interface CategoriaGateway {
    Categoria saveCategoria(Categoria categoria);
    Optional<Categoria> bucarCategoriaPorNombre(String nombre);
    Optional<Categoria> buscarCategoriaPorId(Long id);

}
