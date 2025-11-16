package com.Arka.MSInventario.domain.model.gateway;

import com.Arka.MSInventario.domain.model.HistorialStock;

import java.util.List;

public interface HistorialStockGateway {
    HistorialStock saveHistorialStock(HistorialStock historialStock);

    /**
     * Obtiene el historial de cambios de stock de un producto ordenado por fecha descendente.
     * @param productoId el ID del producto
     * @return lista de cambios de stock ordenados del más reciente al más antiguo
     */
    List<HistorialStock> obtenerHistorialPorProducto(Long productoId);
}
