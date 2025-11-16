package com.Arka.MSInventario.infrastructure.controller;

import com.Arka.MSInventario.application.dto.CategoriaUpdateDTO;
import com.Arka.MSInventario.application.mapper.CategoriaMapper;
import com.Arka.MSInventario.domain.model.Categoria;
import com.Arka.MSInventario.domain.usecase.CategoriaUseCase;
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

@RestController
@RequestMapping("api/v1/categoria")
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "API para la gestión de categorías de productos")
public class CategoriaController {

    private final CategoriaUseCase categoriaUseCase;
    private final CategoriaMapper categoriaMapper;

    @Operation(
            summary = "Crear una nueva categoría",
            description = "Crea una nueva categoría para clasificar productos. Valida que el nombre sea único y cumpla con las reglas de negocio (máximo 50 caracteres)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoría creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos - validación fallida",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Categoría ya existe con ese nombre",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping("/nuevacategoria")
    public ResponseEntity<Categoria> nuevaCategoria(
            @Parameter(description = "Datos de la categoría a crear", required = true)
            @RequestBody Categoria categoria) {
        Categoria nuevaCategoria = categoriaUseCase.saveCategoria(categoria);
        return ResponseEntity.ok(nuevaCategoria);
    }

    @Operation(
            summary = "Obtener todas las categorías",
            description = "Retorna una lista con todas las categorías registradas en el sistema, mostrando id, nombre y fecha de creación"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de categorías obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay categorías registradas en el sistema",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerTodasCategorias() {
        List<Categoria> categorias = categoriaUseCase.obtenerTodasCategorias();

        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(categorias);
    }

    @Operation(
            summary = "Actualizar una categoría existente",
            description = "Actualiza el nombre de una categoría existente identificada por su ID. Valida que el nuevo nombre sea único y cumpla con las reglas de negocio (máximo 50 caracteres)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoría actualizada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos - validación fallida",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoría no encontrada con el ID proporcionado",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ya existe otra categoría con ese nombre",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(
            @Parameter(description = "ID de la categoría a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos de la categoría", required = true)
            @RequestBody CategoriaUpdateDTO categoriaUpdateDTO) {

        Categoria categoria = categoriaMapper.toDomain(categoriaUpdateDTO);
        Categoria categoriaActualizada = categoriaUseCase.actualizarCategoria(id, categoria);

        return ResponseEntity.ok(categoriaActualizada);
    }

    @Operation(
            summary = "Eliminar una categoría",
            description = "Elimina una categoría existente identificada por su ID. Valida que la categoría no tenga productos asociados antes de eliminarla"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Categoría eliminada exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoría no encontrada con el ID proporcionado",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "No se puede eliminar la categoría porque tiene productos asociados",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(
            @Parameter(description = "ID de la categoría a eliminar", required = true, example = "1")
            @PathVariable Long id) {

        categoriaUseCase.eliminarCategoria(id);

        return ResponseEntity.noContent().build();
    }
}

