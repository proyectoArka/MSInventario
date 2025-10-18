package com.Arka.MSInventario.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoClienteDTO {
    private String  nombre;
    private String descripcion;
    private int precio;
    private int stock;
    private String categoria;
}
