package com.Arka.MSInventario.infrastructure.exception;

import com.Arka.MSInventario.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductoYaExisteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleProductoYaExisteException(ProductoYaExisteException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ProductoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleProductoNoEncontradoException(ProductoNoEncontradoException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(CategoriaNoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCategoriaNoEncontradaException(CategoriaNoEncontradaException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(ValidationException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDomainException(DomainException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(CategoriaYaExisteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleCategoriaYaExisteException(CategoriaYaExisteException ex) {return ex.getMessage();}
}
