package com.Arka.MSInventario.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para actualizar un producto existente. Todos los campos son opcionales")
public class ProductoUpdateDTO {

    @Schema(description = "Nuevo nombre del producto (opcional)", example = "Laptop HP Pavilion 16", maxLength = 50)
    private String nombre;

    @Schema(description = "Nueva descripción del producto (opcional)", example = "Laptop HP Pavilion actualizada", maxLength = 200)
    private String descripcion;

    @Schema(description = "Nuevo precio del producto (opcional)", example = "1800")
    private Integer precio;

    @Schema(description = "Nueva cantidad en stock (opcional). Si cambia, se registra en el historial", example = "15")
    private Integer stock;

    @Schema(description = "Nuevo ID de categoría (opcional)", example = "2")
    private Long categoria;

    @Schema(description = "Nuevo umbral de stock bajo (opcional)", example = "3")
    private Integer umbralStockBajo;
}
