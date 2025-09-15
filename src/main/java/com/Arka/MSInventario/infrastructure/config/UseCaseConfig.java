package com.Arka.MSInventario.infrastructure.config;

import com.Arka.MSInventario.domain.model.gateway.CategoriaGateway;
import com.Arka.MSInventario.domain.model.gateway.HistorialStockGateway;
import com.Arka.MSInventario.domain.model.gateway.ProductoGateway;
import com.Arka.MSInventario.domain.usecase.CategoriaUseCase;
import com.Arka.MSInventario.domain.usecase.ProductoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public ProductoUseCase productoUseCase(ProductoGateway productoGateway, HistorialStockGateway historialStockGateway, CategoriaGateway categoriaGateway) {
        return new ProductoUseCase(productoGateway, historialStockGateway, categoriaGateway);
    }

    @Bean
    public CategoriaUseCase categoriaUseCase(CategoriaGateway categoriaGateway) {
        return new CategoriaUseCase(categoriaGateway);
    }
}
