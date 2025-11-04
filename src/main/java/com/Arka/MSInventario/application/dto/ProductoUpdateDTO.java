package com.Arka.MSInventario.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoUpdateDTO {
    private String nombre;
    private String descripcion;
    private Integer precio;
    private Integer stock;
    private Long categoria;
    private Integer umbralStockBajo;
}
