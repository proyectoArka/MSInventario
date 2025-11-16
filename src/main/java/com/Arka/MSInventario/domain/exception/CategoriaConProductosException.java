package com.Arka.MSInventario.domain.exception;

public class CategoriaConProductosException extends DomainException {
    public CategoriaConProductosException(Long id) {
        super("No se puede eliminar la categoria con id " + id + " porque tiene productos asociados.");
    }
}

