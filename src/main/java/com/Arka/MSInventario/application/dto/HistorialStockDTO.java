package com.Arka.MSInventario.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa un registro del historial de cambios de stock de un producto")
public class HistorialStockDTO {

    @Schema(description = "ID del registro de historial", example = "1")
    private Long id;

    @Schema(description = "ID del producto", example = "5")
    private Long productoId;

    @Schema(description = "Fecha y hora del cambio de stock", example = "2025-11-15T14:30:00")
    private LocalDateTime fechaCambio;

    @Schema(description = "Nueva cantidad de stock después del cambio", example = "150")
    private Integer cantidadNueva;
}

