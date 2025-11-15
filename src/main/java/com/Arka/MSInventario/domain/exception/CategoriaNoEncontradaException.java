package com.Arka.MSInventario.domain.exception;

public class CategoriaNoEncontradaException extends DomainException {
    public CategoriaNoEncontradaException(Long id) {
        super("La categoria con id " + id + " no existe.");
    }
}

