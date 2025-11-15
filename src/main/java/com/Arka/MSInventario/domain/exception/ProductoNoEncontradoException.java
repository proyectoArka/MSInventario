package com.Arka.MSInventario.domain.exception;

public class ProductoNoEncontradoException extends DomainException {
    public ProductoNoEncontradoException() {
        super("El producto no existe.");
    }

    public ProductoNoEncontradoException(Long id) {
        super("El producto con id " + id + " no existe.");
    }
}

