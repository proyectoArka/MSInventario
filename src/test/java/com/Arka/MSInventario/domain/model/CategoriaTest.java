package com.Arka.MSInventario.domain.model;

import com.Arka.MSInventario.domain.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de validación del modelo Categoria")
class CategoriaTest {

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Categoría Test");
    }

    @Test
    @DisplayName("Debería validar categoría correctamente cuando todos los campos son válidos")
    void deberiaValidarCategoriaCorrectamente() {
        assertDoesNotThrow(() -> categoria.validar());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el nombre es nulo")
    void deberiaLanzarExcepcionCuandoNombreEsNulo() {
        categoria.setNombre(null);

        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> categoria.validarNombre()
        );

        assertEquals("El nombre de la categoria no puede estar vacío.", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el nombre está vacío")
    void deberiaLanzarExcepcionCuandoNombreEstaVacio() {
        categoria.setNombre("");

        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> categoria.validarNombre()
        );

        assertEquals("El nombre de la categoria no puede estar vacío.", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el nombre contiene solo espacios")
    void deberiaLanzarExcepcionCuandoNombreContieneEspacios() {
        categoria.setNombre("   ");

        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> categoria.validarNombre()
        );

        assertEquals("El nombre de la categoria no puede estar vacío.", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el nombre excede 50 caracteres")
    void deberiaLanzarExcepcionCuandoNombreExcedeLimite() {
        categoria.setNombre("A".repeat(51));

        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> categoria.validarNombre()
        );

        assertTrue(exception.getMessage().contains("El nombre de la categoria no puede tener mas de 50 caracteres"));
    }

    @Test
    @DisplayName("Debería aceptar nombre con exactamente 50 caracteres")
    void deberiaAceptarNombreConExactamente50Caracteres() {
        categoria.setNombre("A".repeat(50));
        assertDoesNotThrow(() -> categoria.validarNombre());
    }

    @Test
    @DisplayName("Debería fallar validación completa si validación de nombre falla")
    void deberiaFallarValidacionCompletaSiNombreFalla() {
        categoria.setNombre(null);

        assertThrows(ValidationException.class, () -> categoria.validar());
    }
}

