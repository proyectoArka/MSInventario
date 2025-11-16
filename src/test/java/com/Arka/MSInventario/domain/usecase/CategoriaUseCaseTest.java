package com.Arka.MSInventario.domain.usecase;

import com.Arka.MSInventario.application.service.CategoriaValidationService;
import com.Arka.MSInventario.domain.exception.ValidationException;
import com.Arka.MSInventario.domain.model.Categoria;
import com.Arka.MSInventario.domain.model.gateway.CategoriaGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del caso de uso CategoriaUseCase")
class CategoriaUseCaseTest {

    @Mock
    private CategoriaGateway categoriaGateway;

    @Mock
    private CategoriaValidationService validationService;

    private CategoriaUseCase categoriaUseCase;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoriaUseCase = new CategoriaUseCase(categoriaGateway, validationService);

        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Electrónica");
    }

    @Test
    @DisplayName("Debería guardar una categoría exitosamente")
    void deberiaSaveCategoriaExitosamente() {
        // Given
        doNothing().when(validationService).validationCategoria(any(Categoria.class));
        when(categoriaGateway.saveCategoria(any(Categoria.class))).thenReturn(categoria);

        // When
        Categoria resultado = categoriaUseCase.saveCategoria(categoria);

        // Then
        assertNotNull(resultado);
        assertEquals("Electrónica", resultado.getNombre());
        verify(validationService).validationCategoria(categoria);
        verify(categoriaGateway).saveCategoria(categoria);
    }

    @Test
    @DisplayName("Debería lanzar excepción si la validación falla")
    void deberiaLanzarExcepcionSiValidacionFalla() {
        // Given
        doThrow(new ValidationException("Categoría inválida"))
            .when(validationService).validationCategoria(any(Categoria.class));

        // When & Then
        assertThrows(ValidationException.class, () ->
            categoriaUseCase.saveCategoria(categoria)
        );

        verify(categoriaGateway, never()).saveCategoria(any(Categoria.class));
    }

    @Test
    @DisplayName("Debería validar categoría antes de guardarla")
    void deberiaValidarCategoriaAntesDeGuardar() {
        // Given
        doNothing().when(validationService).validationCategoria(any(Categoria.class));
        when(categoriaGateway.saveCategoria(any(Categoria.class))).thenReturn(categoria);

        // When
        categoriaUseCase.saveCategoria(categoria);

        // Then
        verify(validationService).validationCategoria(categoria);
        verify(categoriaGateway).saveCategoria(categoria);
    }
}

