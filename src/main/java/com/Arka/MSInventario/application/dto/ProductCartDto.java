package com.Arka.MSInventario.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCartDto {
    private String nombre;
    private String descripcion;
    private int stock;
    private int price;
}
