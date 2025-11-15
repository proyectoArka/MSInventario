package com.Arka.MSInventario.application.mapper;

import com.Arka.MSInventario.application.dto.ProductCartDto;
import com.Arka.MSInventario.application.dto.ProductoDTO;
import com.Arka.MSInventario.domain.model.Categoria;
import com.Arka.MSInventario.domain.model.Producto;
import com.Arka.MSInventario.domain.model.gateway.CategoriaGateway;

public class ProductoMapper {

    private final CategoriaGateway categoriaGateway;

    public ProductoMapper(CategoriaGateway categoriaGateway) {
        this.categoriaGateway = categoriaGateway;
    }

    // Convierte un Producto de dominio a ProductoDTO.
    public ProductoDTO toDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setUmbralStockBajo(producto.getUmbralStockBajo());
        dto.setCreatedAt(producto.getCreatedAt());

        // Obtener el nombre de la categoría usando el gateway
        dto.setCategoria(
            categoriaGateway.buscarCategoriaPorId(producto.getCategoria())
                .map(Categoria::getNombre)
                .orElse("Desconocida")
        );

        return dto;
    }

    // Convierte un Producto de dominio a ProductCartDto.
    public ProductCartDto toCartDTO(Producto producto) {
        return new ProductCartDto(
            producto.getNombre(),
            producto.getDescripcion(),
            producto.getStock(),
            producto.getPrecio()
        );
    }
}

