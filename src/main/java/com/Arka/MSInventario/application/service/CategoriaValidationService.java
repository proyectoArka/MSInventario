package com.Arka.MSInventario.application.service;

import com.Arka.MSInventario.domain.exception.CategoriaConProductosException;
import com.Arka.MSInventario.domain.exception.CategoriaYaExisteException;
import com.Arka.MSInventario.domain.model.Categoria;
import com.Arka.MSInventario.domain.model.gateway.CategoriaGateway;
import com.Arka.MSInventario.domain.model.gateway.ProductoGateway;

public class CategoriaValidationService {

    private final CategoriaGateway categoriaGateway;
    private final ProductoGateway productoGateway;

    public CategoriaValidationService(CategoriaGateway categoriaGateway, ProductoGateway productoGateway) {
        this.categoriaGateway = categoriaGateway;
        this.productoGateway = productoGateway;
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

    // Valida que no exista otra categoria con el mismo nombre al actualizar
    public void validateCategoriaExistsForUpdate(Long id, String nuevoNombre) {
        categoriaGateway.bucarCategoriaPorNombre(nuevoNombre)
                .ifPresent(categoriaExistente -> {
                    if (!categoriaExistente.getId().equals(id)) {
                        throw new CategoriaYaExisteException(nuevoNombre);
                    }
                });
    }

    // Valida que la categoria no tenga productos asociados antes de eliminar
    public void validateCategoriaNoTieneProductos(Long id) {
        if (productoGateway.existenProductosPorCategoria(id)) {
            throw new CategoriaConProductosException(id);
        }
    }
}
