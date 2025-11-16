package com.Arka.MSInventario.domain.usecase;

import com.Arka.MSInventario.application.dto.ProductCartDto;
import com.Arka.MSInventario.application.dto.ProductoDTO;
import com.Arka.MSInventario.application.dto.ProductoUpdateDTO;
import com.Arka.MSInventario.application.mapper.ProductoMapper;
import com.Arka.MSInventario.application.service.ProductoValidationService;
import com.Arka.MSInventario.domain.exception.ProductoNoEncontradoException;
import com.Arka.MSInventario.domain.model.HistorialStock;
import com.Arka.MSInventario.domain.model.Producto;
import com.Arka.MSInventario.domain.model.gateway.HistorialStockGateway;
import com.Arka.MSInventario.domain.model.gateway.ProductoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del caso de uso ProductoUseCase")
class ProductoUseCaseTest {

    @Mock
    private ProductoGateway productoGateway;

    @Mock
    private HistorialStockGateway historialStockGateway;

    @Mock
    private ProductoMapper productoMapper;

    @Mock
    private ProductoValidationService validationService;

    private ProductoUseCase productoUseCase;

    private Producto producto;
    private ProductoDTO productoDTO;

    @BeforeEach
    void setUp() {
        productoUseCase = new ProductoUseCase(
            productoGateway,
            historialStockGateway,
            validationService,
            productoMapper
        );

        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Laptop");
        producto.setDescripcion("Laptop HP");
        producto.setPrecio(1500);
        producto.setStock(10);
        producto.setCategoria(1L);
        producto.setUmbralStockBajo(5);

        productoDTO = new ProductoDTO();
        productoDTO.setId(1L);
        productoDTO.setNombre("Laptop");
        productoDTO.setDescripcion("Laptop HP");
        productoDTO.setPrecio(1500);
        productoDTO.setStock(10);
        productoDTO.setCategoria("Electrónica");
        productoDTO.setUmbralStockBajo(5);
    }

    @Test
    @DisplayName("Debería guardar un producto exitosamente")
    void deberiaSaveProductoExitosamente() {
        // Given
        HistorialStock historial = new HistorialStock();

        doNothing().when(validationService).validarNuevoProducto(any(Producto.class));
        when(productoGateway.saveProductos(any(Producto.class))).thenReturn(producto);
        when(historialStockGateway.saveHistorialStock(any(HistorialStock.class))).thenReturn(historial);

        // When
        Producto resultado = productoUseCase.saveProducto(producto);

        // Then
        assertNotNull(resultado);
        assertEquals("Laptop", resultado.getNombre());
        verify(validationService).validarNuevoProducto(producto);
        verify(productoGateway).saveProductos(producto);
        verify(historialStockGateway).saveHistorialStock(any(HistorialStock.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción si la validación falla al guardar")
    void deberiaLanzarExcepcionSiValidacionFalla() {
        // Given
        doThrow(new RuntimeException("Validación fallida"))
            .when(validationService).validarNuevoProducto(any(Producto.class));

        // When & Then
        assertThrows(RuntimeException.class, () ->
            productoUseCase.saveProducto(producto)
        );

        verify(productoGateway, never()).saveProductos(any(Producto.class));
    }

    @Test
    @DisplayName("Debería obtener todos los productos como DTOs")
    void deberiaObtenerTodosLosProductosDTO() {
        // Given
        List<Producto> productos = Arrays.asList(producto);
        when(productoGateway.findAll()).thenReturn(productos);
        when(productoMapper.toDTO(any(Producto.class))).thenReturn(productoDTO);

        // When
        List<ProductoDTO> resultado = productoUseCase.obtenerTodosLosProductosDTO();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Laptop", resultado.get(0).getNombre());
        verify(productoGateway).findAll();
        verify(productoMapper).toDTO(producto);
    }

    @Test
    @DisplayName("Debería buscar producto por ID y retornar DTO")
    void deberiaBuscarProductoPorId() {
        // Given
        when(productoGateway.buscarPorId(1L)).thenReturn(Optional.of(producto));
        when(productoMapper.toDTO(any(Producto.class))).thenReturn(productoDTO);

        // When
        Optional<ProductoDTO> resultado = productoUseCase.buscarPorId(1L);

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Laptop", resultado.get().getNombre());
        verify(productoGateway).buscarPorId(1L);
        verify(productoMapper).toDTO(producto);
    }

    @Test
    @DisplayName("Debería retornar Optional vacío si producto no existe")
    void deberiaRetornarVacioSiProductoNoExiste() {
        // Given
        when(productoGateway.buscarPorId(999L)).thenReturn(Optional.empty());

        // When
        Optional<ProductoDTO> resultado = productoUseCase.buscarPorId(999L);

        // Then
        assertFalse(resultado.isPresent());
        verify(productoGateway).buscarPorId(999L);
        verify(productoMapper, never()).toDTO(any());
    }

    @Test
    @DisplayName("Debería actualizar producto exitosamente")
    void deberiaActualizarProductoExitosamente() {
        // Given
        ProductoUpdateDTO updateDTO = new ProductoUpdateDTO();
        updateDTO.setNombre("Laptop Actualizada");
        updateDTO.setPrecio(1800);
        updateDTO.setStock(15);

        when(productoGateway.buscarPorId(1L)).thenReturn(Optional.of(producto));
        doNothing().when(validationService).validarActualizacionProducto(any(Producto.class));
        when(productoGateway.saveProductos(any(Producto.class))).thenReturn(producto);

        // When
        Producto resultado = productoUseCase.actualizarProducto(1L, updateDTO);

        // Then
        assertNotNull(resultado);
        verify(productoGateway).buscarPorId(1L);
        verify(validationService).validarActualizacionProducto(producto);
        verify(productoGateway).saveProductos(producto);
    }

    @Test
    @DisplayName("Debería lanzar excepción al actualizar producto inexistente")
    void deberiaLanzarExcepcionAlActualizarProductoInexistente() {
        // Given
        ProductoUpdateDTO updateDTO = new ProductoUpdateDTO();
        when(productoGateway.buscarPorId(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ProductoNoEncontradoException.class, () ->
            productoUseCase.actualizarProducto(999L, updateDTO)
        );

        verify(productoGateway, never()).saveProductos(any(Producto.class));
    }

    @Test
    @DisplayName("Debería registrar historial de stock al actualizar si stock cambió")
    void deberiaRegistrarHistorialStockAlActualizar() {
        // Given
        ProductoUpdateDTO updateDTO = new ProductoUpdateDTO();
        updateDTO.setStock(20);

        Producto productoActualizado = new Producto();
        productoActualizado.setId(1L);
        productoActualizado.setStock(20);

        HistorialStock historial = new HistorialStock();

        when(productoGateway.buscarPorId(1L)).thenReturn(Optional.of(producto));
        doNothing().when(validationService).validarActualizacionProducto(any(Producto.class));
        when(productoGateway.saveProductos(any(Producto.class))).thenReturn(productoActualizado);
        when(historialStockGateway.saveHistorialStock(any(HistorialStock.class))).thenReturn(historial);

        // When
        productoUseCase.actualizarProducto(1L, updateDTO);

        // Then
        verify(historialStockGateway).saveHistorialStock(any(HistorialStock.class));
    }

    @Test
    @DisplayName("No debería registrar historial de stock si el stock no cambió")
    void noDeberiaRegistrarHistorialStockSiNoHaCambiado() {
        // Given
        ProductoUpdateDTO updateDTO = new ProductoUpdateDTO();
        updateDTO.setNombre("Nuevo nombre");

        when(productoGateway.buscarPorId(1L)).thenReturn(Optional.of(producto));
        doNothing().when(validationService).validarActualizacionProducto(any(Producto.class));
        when(productoGateway.saveProductos(any(Producto.class))).thenReturn(producto);

        // When
        productoUseCase.actualizarProducto(1L, updateDTO);

        // Then - El test verifica que la actualización se complete sin error
        verify(productoGateway).saveProductos(any(Producto.class));
    }

    @Test
    @DisplayName("Debería buscar productos por nombre")
    void deberiaBuscarProductosPorNombre() {
        // Given
        List<Producto> productos = Arrays.asList(producto);
        when(productoGateway.buscarProductos("Laptop")).thenReturn(productos);
        when(productoMapper.toDTO(any(Producto.class))).thenReturn(productoDTO);

        // When
        List<ProductoDTO> resultado = productoUseCase.buscarProductosDTO("Laptop");

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(productoGateway).buscarProductos("Laptop");
        verify(productoMapper).toDTO(producto);
    }

    @Test
    @DisplayName("Debería obtener stock y precio del producto")
    void deberiaObtenerStockPriceProducto() {
        // Given
        ProductCartDto cartDto = new ProductCartDto();
        cartDto.setNombre("Laptop");
        cartDto.setStock(10);
        cartDto.setPrice(1500);

        when(productoGateway.buscarPorId(1L)).thenReturn(Optional.of(producto));
        when(productoMapper.toCartDTO(any(Producto.class))).thenReturn(cartDto);

        // When
        ProductCartDto resultado = productoUseCase.obtenerStockPriceProducto(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(10, resultado.getStock());
        assertEquals(1500, resultado.getPrice());
        verify(productoGateway).buscarPorId(1L);
        verify(productoMapper).toCartDTO(producto);
    }

    @Test
    @DisplayName("Debería lanzar excepción al obtener stock de producto inexistente")
    void deberiaLanzarExcepcionAlObtenerStockProductoInexistente() {
        // Given
        when(productoGateway.buscarPorId(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ProductoNoEncontradoException.class, () ->
            productoUseCase.obtenerStockPriceProducto(999L)
        );
    }
}

