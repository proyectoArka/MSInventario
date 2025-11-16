package com.Arka.MSInventario.infrastructure.controller;

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

@RestController
@RequestMapping("api/v1/categoria")
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "API para la gestión de categorías de productos")
public class CategoriaController {

    private final CategoriaUseCase categoriaUseCase;

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
}

