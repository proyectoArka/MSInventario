package com.Arka.MSInventario.application.dto;

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
public class ProductoDTO {

    private String  nombre;
    private String descripcion;
    private int precio;
    private int stock;
    private String categoria;
    private int umbralStockBajo;
    private LocalDateTime createdAt;
}
