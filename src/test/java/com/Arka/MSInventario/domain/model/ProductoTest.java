package com.Arka.MSInventario.domain.model;

import com.Arka.MSInventario.domain.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de validación del modelo Producto")
class ProductoTest {

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto Test");
        producto.setDescripcion("Descripción de prueba");
        producto.setPrecio(100);
        producto.setStock(10);
        producto.setCategoria(1L);
        producto.setUmbralStockBajo(5);
    }

    @Test
    @DisplayName("Debería validar producto correctamente cuando todos los campos son válidos")
    void deberiaValidarProductoCorrectamente() {
        assertDoesNotThrow(() -> producto.validar());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el nombre es nulo")
    void deberiaLanzarExcepcionCuandoNombreEsNulo() {
        producto.setNombre(null);

        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> producto.validarNombre()
        );

        assertEquals("El nombre del producto no puede estar vacío.", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el nombre está vacío")
    void deberiaLanzarExcepcionCuandoNombreEstaVacio() {
        producto.setNombre("");

        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> producto.validarNombre()
        );

        assertEquals("El nombre del producto no puede estar vacío.", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el nombre solo contiene espacios")
    void deberiaLanzarExcepcionCuandoNombreSoloEspacios() {
        producto.setNombre("   ");

        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> producto.validarNombre()
        );

        assertEquals("El nombre del producto no puede estar vacío.", exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el nombre excede 50 caracteres")
    void deberiaLanzarExcepcionCuandoNombreExcedeLimite() {
        producto.setNombre("A".repeat(51));

        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> producto.validarNombre()
        );

        assertTrue(exception.getMessage().contains("El nombre no puede tener mas de 50 caracteres"));
    }

    @Test
    @DisplayName("Debería aceptar nombre con exactamente 50 caracteres")
    void deberiaAceptarNombreConExactamente50Caracteres() {
        producto.setNombre("A".repeat(50));
        assertDoesNotThrow(() -> producto.validarNombre());
    }

    @Test
    @DisplayName("Debería aceptar descripción nula")
    void deberiaAceptarDescripcionNula() {
        producto.setDescripcion(null);
        assertDoesNotThrow(() -> producto.validarDescripcion());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando descripción excede 200 caracteres")
    void deberiaLanzarExcepcionCuandoDescripcionExcedeLimite() {
        producto.setDescripcion("A".repeat(201));

        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> producto.validarDescripcion()
        );

        assertTrue(exception.getMessage().contains("La descripcion no puede tener mas de 200 caracteres"));
    }

    @Test
    @DisplayName("Debería aceptar descripción con exactamente 200 caracteres")
    void deberiaAceptarDescripcionConExactamente200Caracteres() {
        producto.setDescripcion("A".repeat(200));
        assertDoesNotThrow(() -> producto.validarDescripcion());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el stock es negativo")
    void deberiaLanzarExcepcionCuandoStockEsNegativo() {
        producto.setStock(-1);

        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> producto.validarStock()
        );

        assertEquals("El stock no puede ser negativo.", exception.getMessage());
    }

    @Test
    @DisplayName("Debería aceptar stock igual a cero")
    void deberiaAceptarStockIgualACero() {
        producto.setStock(0);
        assertDoesNotThrow(() -> producto.validarStock());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando umbral de stock bajo es negativo")
    void deberiaLanzarExcepcionCuandoUmbralStockBajoEsNegativo() {
        producto.setUmbralStockBajo(-1);

        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> producto.validarUmbralStockBajo()
        );

        assertEquals("El umbral de stock bajo no puede ser negativo", exception.getMessage());
    }

    @Test
    @DisplayName("Debería aceptar umbral de stock bajo igual a cero")
    void deberiaAceptarUmbralStockBajoIgualACero() {
        producto.setUmbralStockBajo(0);
        assertDoesNotThrow(() -> producto.validarUmbralStockBajo());
    }

    @Test
    @DisplayName("Debería detectar que el stock ha cambiado")
    void deberiaDetectarCambioDeStock() {
        assertTrue(producto.stockHaCambiado(5));
        assertFalse(producto.stockHaCambiado(10));
    }

    @Test
    @DisplayName("Debería detectar que el stock no ha cambiado cuando es el mismo valor")
    void deberiaDetectarQueStockNoHaCambiado() {
        assertFalse(producto.stockHaCambiado(10));
    }

    @Test
    @DisplayName("Debería fallar validación completa si alguna validación falla")
    void deberiaFallarValidacionCompletaSiAlgunaFalla() {
        producto.setNombre(null);

        assertThrows(ValidationException.class, () -> producto.validar());
    }
}

