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
@Schema(description = "DTO simplificado del producto para uso en carrito de compras o integraciones con otros microservicios")
public class ProductCartDto {

    @Schema(description = "Nombre del producto", example = "Laptop HP Pavilion")
    private String nombre;

    @Schema(description = "Descripción del producto", example = "Laptop HP Pavilion 15.6 pulgadas")
    private String descripcion;

    @Schema(description = "Cantidad disponible en stock", example = "10")
    private int stock;

    @Schema(description = "Precio del producto", example = "1500")
    private int price;
}
