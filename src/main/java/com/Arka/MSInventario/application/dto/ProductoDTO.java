package com.Arka.MSInventario.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de respuesta con información completa del producto incluyendo nombre de categoría")
public class ProductoDTO {

    @Schema(description = "Identificador único del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto (máximo 50 caracteres)", example = "Laptop HP Pavilion", maxLength = 50)
    private String  nombre;

    @Schema(description = "Descripción detallada del producto (máximo 200 caracteres)", example = "Laptop HP Pavilion 15.6 pulgadas", maxLength = 200)
    private String descripcion;

    @Schema(description = "Precio del producto en la moneda local", example = "1500")
    private int precio;

    @Schema(description = "Cantidad disponible en stock", example = "10")
    private int stock;

    @Schema(description = "Nombre de la categoría a la que pertenece el producto", example = "Electrónica")
    private String categoria;

    @Schema(description = "Umbral de stock bajo para alertas", example = "5")
    private int umbralStockBajo;

    @Schema(description = "Fecha y hora de creación del producto", example = "2025-01-15T10:30:00")
    private LocalDateTime createdAt;
}
