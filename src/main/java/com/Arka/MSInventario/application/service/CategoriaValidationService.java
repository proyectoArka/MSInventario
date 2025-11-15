package com.Arka.MSInventario.application.service;

import com.Arka.MSInventario.domain.exception.CategoriaYaExisteException;
import com.Arka.MSInventario.domain.model.Categoria;
import com.Arka.MSInventario.domain.model.gateway.CategoriaGateway;

public class CategoriaValidationService {

    private final CategoriaGateway categoriaGateway;

    public CategoriaValidationService(CategoriaGateway categoriaGateway) {
        this.categoriaGateway = categoriaGateway;
    }

    // Valida que no exista una categoria con el mismo nombre
    public void validateCategoriaExists(String categoria) {
        if (categoriaGateway.bucarCategoriaPorNombre(categoria).isPresent()) {
            throw new CategoriaYaExisteException(categoria);
        }
    }

    // Valida las reglas de negocio para crear una nueva categoria
    public void validationCategoria(Categoria categoria){
        validateCategoriaExists(categoria.getNombre());
        categoria.validar();
    }
}
