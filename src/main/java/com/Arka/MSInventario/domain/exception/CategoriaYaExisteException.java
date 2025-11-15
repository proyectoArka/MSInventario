package com.Arka.MSInventario.domain.exception;

public class CategoriaYaExisteException extends RuntimeException {
    public CategoriaYaExisteException(String message) {
        super("La categoria " + message + " ya existe.");
    }
}
