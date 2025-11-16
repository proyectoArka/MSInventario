package com.Arka.MSInventario.application.mapper;

import com.Arka.MSInventario.application.dto.HistorialStockDTO;
import com.Arka.MSInventario.domain.model.HistorialStock;

public class HistorialStockMapper {

    /**
     * Convierte un HistorialStock de dominio a HistorialStockDTO.
     * @param historialStock el objeto de dominio
     * @return el DTO
     */
    public HistorialStockDTO toDTO(HistorialStock historialStock) {
        HistorialStockDTO dto = new HistorialStockDTO();
        dto.setId(historialStock.getId());
        dto.setProductoId(historialStock.getId_Producto());
        dto.setFechaCambio(historialStock.getFecha_Cambio());
        dto.setCantidadNueva(historialStock.getCantidad_nueva());
        return dto;
    }
}

