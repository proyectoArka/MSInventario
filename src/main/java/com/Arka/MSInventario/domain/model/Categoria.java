package com.Arka.MSInventario.domain.model;

import com.Arka.MSInventario.domain.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {
    private Long id;
    private String nombre;
    private LocalDateTime createdAt;

    // Valida que el nombre de la categoría no esté vacío y no exceda los 50 caracteres.
    public void validarNombre() {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidationException("El nombre de la categoria no puede estar vacío.");
        }
        if (nombre.length() > 50) {
            throw new ValidationException("El nombre de la categoria no puede tener mas de 50 caracteres, tiene: " + nombre.length());
        }
    }

    // Valida todas las reglas de negocio para la categoría.
    public void validar() {
        validarNombre();
    }
}
