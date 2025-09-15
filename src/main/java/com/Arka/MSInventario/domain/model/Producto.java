package com.Arka.MSInventario.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    private Long id;
    private String nombre;
    private String descripcion;
    private int precio;
    private int stock;
    private Long categoria;
    private int umbralStockBajo;
    private LocalDateTime createdAt;

}
