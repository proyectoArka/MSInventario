package com.Arka.MSInventario.infrastructure.exception;

import com.Arka.MSInventario.domain.model.exception.Exception;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(org.springframework.http.HttpStatus.CONFLICT)

    public String handleProductoYaExisteException(Exception ex) {
        return ex.getMessage();
    }
}
