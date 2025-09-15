package com.Arka.MSInventario.domain.usecase;

import com.Arka.MSInventario.domain.model.Categoria;
import com.Arka.MSInventario.domain.model.exception.Exception;
import com.Arka.MSInventario.domain.model.gateway.CategoriaGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoriaUseCase {
    private final CategoriaGateway categoriaGateway;

    public Categoria saveCategoria(Categoria categoria) {
        if (categoria.getNombre().length() > 50) {
            throw new Exception("El nombre de la categoria no puede tener mas de 50 caracteres, tiene: " + categoria.getNombre().length());
        }
        if(categoriaGateway.bucarCategoriaPorNombre(categoria.getNombre()).isPresent()){
            throw new Exception("La categoria " + categoria.getNombre() + " ya existe.");
        }
        return categoriaGateway.saveCategoria(categoria);
    }
}
