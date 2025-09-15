package com.Arka.MSInventario.domain.model.gateway;

import com.Arka.MSInventario.domain.model.HistorialStock;

import java.util.Optional;

public interface HistorialStockGateway {
    HistorialStock saveHistorialStock(HistorialStock historialStock);
}
