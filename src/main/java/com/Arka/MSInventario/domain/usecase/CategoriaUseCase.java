package com.Arka.MSInventario.domain.usecase;

import com.Arka.MSInventario.application.service.CategoriaValidationService;
import com.Arka.MSInventario.domain.exception.ValidationException;
import com.Arka.MSInventario.domain.model.Categoria;
import com.Arka.MSInventario.domain.model.gateway.CategoriaGateway;

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
}
