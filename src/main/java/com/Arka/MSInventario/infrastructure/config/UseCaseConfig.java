package com.Arka.MSInventario.infrastructure.config;

import com.Arka.MSInventario.application.mapper.ProductoMapper;
import com.Arka.MSInventario.application.service.CategoriaValidationService;
import com.Arka.MSInventario.application.service.ProductoValidationService;
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
    public ProductoMapper productoMapper(CategoriaGateway categoriaGateway) {
        return new ProductoMapper(categoriaGateway);
    }

    @Bean
    public ProductoValidationService productoValidationService(
            ProductoGateway productoGateway,
            CategoriaGateway categoriaGateway) {
        return new ProductoValidationService(productoGateway, categoriaGateway);
    }

    @Bean CategoriaValidationService categoriaValidationService(CategoriaGateway categoriaGateway) {
        return new CategoriaValidationService(categoriaGateway);
    }

    @Bean
    public ProductoUseCase productoUseCase(
            ProductoGateway productoGateway,
            HistorialStockGateway historialStockGateway,
            ProductoValidationService validationService,
            ProductoMapper mapper) {
        return new ProductoUseCase(productoGateway, historialStockGateway, validationService, mapper);
    }

    @Bean
    public CategoriaUseCase categoriaUseCase(CategoriaGateway categoriaGateway,
                                             CategoriaValidationService categoriaValidationService) {
        return new CategoriaUseCase(categoriaGateway, categoriaValidationService);
    }
}
