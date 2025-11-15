package com.Arka.MSInventario.domain.exception;

public class ProductoYaExisteException extends DomainException {
    public ProductoYaExisteException(String nombre) {
        super("El producto " + nombre + " ya existe.");
    }
}

