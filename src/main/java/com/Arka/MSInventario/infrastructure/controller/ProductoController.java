package com.Arka.MSInventario.infrastructure.controller;

import com.Arka.MSInventario.application.dto.HistorialStockDTO;
import com.Arka.MSInventario.application.dto.ProductCartDto;
import com.Arka.MSInventario.application.dto.ProductoDTO;
import com.Arka.MSInventario.application.dto.ProductoUpdateDTO;
import com.Arka.MSInventario.application.mapper.HistorialStockMapper;
import com.Arka.MSInventario.domain.model.Producto;
import com.Arka.MSInventario.domain.usecase.ProductoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "API para la gestión de productos del inventario")
public class ProductoController {
    
    private final ProductoUseCase productoUseCase;
    private final HistorialStockMapper historialStockMapper;

    @Operation(
            summary = "Crear un nuevo producto",
            description = "Crea un nuevo producto en el inventario. Valida que el nombre sea único y que la categoría exista. Registra automáticamente el stock inicial en el historial."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos - validación fallida",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Producto ya existe con ese nombre",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoría no encontrada",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping("/crear")
    public ResponseEntity<Producto> CrearProducto(
            @Parameter(description = "Datos del producto a crear", required = true)
            @RequestBody Producto producto) {
        Producto nuevoProducto = productoUseCase.saveProducto(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    @Operation(
            summary = "Listar todos los productos",
            description = "Obtiene una lista completa de todos los productos del inventario en formato DTO con información de la categoría"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/listar")
    public ResponseEntity<?> listarProductos() {
        try {
            List<ProductoDTO> productos = productoUseCase.obtenerTodosLosProductosDTO();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno");
        }
    }

    @Operation(
            summary = "Buscar producto por ID",
            description = "Busca un producto específico por su identificador único y retorna sus datos completos en formato DTO"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Producto no encontrado",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarProducto(
            @Parameter(description = "ID único del producto", required = true, example = "1")
            @PathVariable Long id) {
        try {
            Optional<ProductoDTO> producto = productoUseCase.buscarPorId(id);
            return producto.<ResponseEntity<Object>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(404).body("Producto no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno");
        }
    }

    @Operation(
            summary = "Actualizar producto existente",
            description = "Actualiza los datos de un producto existente. Si el stock cambia, registra el cambio en el historial automáticamente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Producto no encontrado",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Object> actualizarProducto(
            @Parameter(description = "ID del producto a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos a actualizar del producto", required = true)
            @RequestBody ProductoUpdateDTO productoUpdateDTO) {
        try {
            Producto productoActualizado = productoUseCase.actualizarProducto(id, productoUpdateDTO);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Buscar productos por nombre",
            description = "Busca productos que contengan el texto especificado en su nombre (búsqueda case-insensitive)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Búsqueda exitosa (puede retornar lista vacía)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/buscarProductos/{nombre}")
    public ResponseEntity<Object> buscarProductos(
            @Parameter(description = "Texto a buscar en el nombre del producto", required = true, example = "Laptop")
            @PathVariable String nombre) {
        try {
            List<ProductoDTO> productoDTO = productoUseCase.buscarProductosDTO(nombre);
            return ResponseEntity.ok(productoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno..." + e.getMessage());
        }
    }

    @Operation(
            summary = "Obtener stock y precio del producto",
            description = "Obtiene información simplificada del producto (nombre, stock y precio) para ser usado en el carrito de compras u otros microservicios"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Información obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCartDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Producto no encontrado",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/stockprice/{id}")
    public ResponseEntity<Object> obtenerStockPriceProducto(
            @Parameter(description = "ID del producto", required = true, example = "1")
            @PathVariable Long id) {
        try {
            ProductCartDto stockPrice = productoUseCase.obtenerStockPriceProducto(id);
            return ResponseEntity.ok(stockPrice);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno..." + e.getMessage());
        }
    }

    @Operation(
            summary = "Dar de baja un producto",
            description = "Realiza una baja lógica del producto cambiando el campo isDelete a true. El producto no se elimina físicamente de la base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto dado de baja exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Producto no encontrado con el ID proporcionado",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PatchMapping("/baja/{id}")
    public ResponseEntity<Object> darDeBajaProducto(
            @Parameter(description = "ID del producto a dar de baja", required = true, example = "1")
            @PathVariable Long id) {
        try {
            Producto productoBaja = productoUseCase.darDeBajaProducto(id);
            return ResponseEntity.ok(productoBaja);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Obtener historial de cambios de stock",
            description = "Obtiene el historial completo de cambios de stock de un producto específico, ordenado del más reciente al más antiguo. Muestra la fecha de cada cambio y la cantidad actualizada"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Historial obtenido exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HistorialStockDTO.class))
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "El producto no tiene historial de cambios de stock",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Producto no encontrado con el ID proporcionado",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/historial-stock/{id}")
    public ResponseEntity<Object> obtenerHistorialStock(
            @Parameter(description = "ID del producto", required = true, example = "1")
            @PathVariable Long id) {
        try {
            List<HistorialStockDTO> historial = productoUseCase.obtenerHistorialStockPorProducto(id)
                    .stream()
                    .map(historialStockMapper::toDTO)
                    .collect(Collectors.toList());

            if (historial.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(historial);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Obtener productos con stock bajo",
            description = "Retorna todos los productos activos cuyo stock actual está por debajo o igual al umbral de stock bajo configurado. Útil para identificar productos que requieren reabastecimiento"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos con stock bajo obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class))
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay productos con stock bajo",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/stock-bajo")
    public ResponseEntity<Object> obtenerProductosConStockBajo() {
        try {
            List<ProductoDTO> productos = productoUseCase.obtenerProductosConStockBajo();

            if (productos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener productos con stock bajo: " + e.getMessage());
        }
    }
}

