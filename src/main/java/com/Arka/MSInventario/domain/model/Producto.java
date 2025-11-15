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
public class Producto {

    private Long id;
    private String nombre;
    private String descripcion;
    private int precio;
    private int stock;
    private Long categoria;
    private int umbralStockBajo;
    private LocalDateTime createdAt;

    // valida que el nombre no sea nulo ni vacio y que no supere los 50 caracteres
    public void validarNombre() {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidationException("El nombre del producto no puede estar vacío.");
        }
        if (nombre.length() > 50) {
            throw new ValidationException("El nombre no puede tener mas de 50 caracteres, tiene: " + nombre.length());
        }
    }

    // valida que la descripcion no supere los 200 caracteres
    public void validarDescripcion() {
        if (descripcion != null && descripcion.length() > 200) {
            throw new ValidationException("La descripcion no puede tener mas de 200 caracteres, tiene: " + descripcion.length());
        }
    }

    // Valida que el stock no sea negativo.
    public void validarStock() {
        if (stock < 0) {
            throw new ValidationException("El stock no puede ser negativo.");
        }
    }

    // Valida que el umbral de stock bajo no sea negativo.
    public void validarUmbralStockBajo() {
        if (umbralStockBajo < 0) {
            throw new ValidationException("El umbral de stock bajo no puede ser negativo");
        }
    }

    // Valida todas las reglas de negocio para el producto.
    public void validar() {
        validarNombre();
        validarDescripcion();
        validarStock();
        validarUmbralStockBajo();
    }

    // Verifica si el stock ha cambiado en comparación con el stock anterior
    public boolean stockHaCambiado(int stockAnterior) {
        return this.stock != stockAnterior;
    }

}
