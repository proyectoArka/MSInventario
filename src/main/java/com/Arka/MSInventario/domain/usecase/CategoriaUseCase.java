package com.Arka.MSInventario.domain.usecase;

import com.Arka.MSInventario.application.service.CategoriaValidationService;
import com.Arka.MSInventario.domain.exception.CategoriaNoEncontradaException;
import com.Arka.MSInventario.domain.model.Categoria;
import com.Arka.MSInventario.domain.model.gateway.CategoriaGateway;

import java.util.List;

public class CategoriaUseCase {
    private final CategoriaGateway categoriaGateway;
    private final CategoriaValidationService validationService;

    public CategoriaUseCase(CategoriaGateway categoriaGateway,
                            CategoriaValidationService validationService) {
        this.categoriaGateway = categoriaGateway;
        this.validationService = validationService;
    }

    public Categoria saveCategoria(Categoria categoria) {
        validationService.validationCategoria(categoria);

        return categoriaGateway.saveCategoria(categoria);
    }

    public List<Categoria> obtenerTodasCategorias() {
        return categoriaGateway.obtenerTodasCategorias();
    }

    public Categoria actualizarCategoria(Long id, Categoria categoria) {

        // Validar que la categoría existe
        categoriaGateway.buscarCategoriaPorId(id)
                .orElseThrow(() -> new CategoriaNoEncontradaException(id));

        // Validar reglas de negocio
        categoria.validar();

        // Validar que el nuevo nombre no esté usado por otra categoría
        validationService.validateCategoriaExistsForUpdate(id, categoria.getNombre());

        return categoriaGateway.actualizarCategoria(id, categoria);
    }

    public void eliminarCategoria(Long id) {
        // Validar que la categoría existe
        categoriaGateway.buscarCategoriaPorId(id)
                .orElseThrow(() -> new CategoriaNoEncontradaException(id));

        // Validar que la categoría no tenga productos asociados
        validationService.validateCategoriaNoTieneProductos(id);

        // Eliminar la categoría
        categoriaGateway.eliminarCategoria(id);
    }
}
