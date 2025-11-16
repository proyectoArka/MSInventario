package com.Arka.MSInventario.application.mapper;

import com.Arka.MSInventario.application.dto.CategoriaUpdateDTO;
import com.Arka.MSInventario.domain.model.Categoria;

public class CategoriaMapper {

    // Convierte un CategoriaUpdateDTO a Categoria de dominio
    public Categoria toDomain(CategoriaUpdateDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        return categoria;
    }
}

