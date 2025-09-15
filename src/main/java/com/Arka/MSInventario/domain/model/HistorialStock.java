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
public class HistorialStock {
    private long id;
    private long id_Producto;
    private LocalDateTime fecha_Cambio;
    private int cantidad_nueva;
}
