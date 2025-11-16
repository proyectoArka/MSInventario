package com.Arka.MSInventario.infrastructure.adapters.repository;

import com.Arka.MSInventario.domain.model.HistorialStock;
import com.Arka.MSInventario.domain.model.gateway.HistorialStockGateway;
import com.Arka.MSInventario.infrastructure.adapters.entity.HistorialStockEntity;
import com.Arka.MSInventario.infrastructure.adapters.entity.ProductoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HistorialStockGatewayImpl implements HistorialStockGateway {
    private final HistorialStockRepository historialStockRepository;
    private final ProductoRepository productoRepository;

    @Override
    public HistorialStock saveHistorialStock(HistorialStock historialStock) {
        ProductoEntity productoEntity = productoRepository.findById(historialStock.getId_Producto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: "));
        HistorialStockEntity entity = new HistorialStockEntity(historialStock, productoEntity);
        return historialStockRepository.save(entity).toDomain();
    }

    @Override
    public List<HistorialStock> obtenerHistorialPorProducto(Long productoId) {
        return historialStockRepository.findByProductoIdOrderByFechaCambioDesc(productoId)
                .stream()
                .map(HistorialStockEntity::toDomain)
                .collect(Collectors.toList());
    }
}
