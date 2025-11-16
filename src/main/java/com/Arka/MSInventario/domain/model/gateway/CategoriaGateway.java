package com.Arka.MSInventario.domain.model.gateway;

import com.Arka.MSInventario.domain.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaGateway {
    Categoria saveCategoria(Categoria categoria);
    Optional<Categoria> bucarCategoriaPorNombre(String nombre);
    Optional<Categoria> buscarCategoriaPorId(Long id);
    List<Categoria> obtenerTodasCategorias();
    Categoria actualizarCategoria(Long id, Categoria categoria);
    void eliminarCategoria(Long id);

}
