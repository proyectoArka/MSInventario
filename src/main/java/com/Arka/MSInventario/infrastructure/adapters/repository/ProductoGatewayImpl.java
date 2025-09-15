package com.Arka.MSInventario.infrastructure.adapters.repository;

import com.Arka.MSInventario.domain.model.Producto;
import com.Arka.MSInventario.domain.model.gateway.ProductoGateway;
import com.Arka.MSInventario.infrastructure.adapters.entity.CategoriaEntity;
import com.Arka.MSInventario.infrastructure.adapters.entity.ProductoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductoGatewayImpl implements ProductoGateway {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    public Producto saveProductos(Producto producto){
        CategoriaEntity categoriaEntity = categoriaRepository.findByid(producto.getCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con id: " + producto.getCategoria()));
        ProductoEntity entity = new ProductoEntity(producto, categoriaEntity);
        return productoRepository.save(entity).toDomain();
    }

    @Override
    public Optional<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombre(nombre)
                .map(ProductoEntity::toDomain);
    }

    @Override
    public java.util.List<Producto> findAll() {
        return productoRepository.findAll()
                .stream()
                .map(ProductoEntity::toDomain)
                .toList();
    }
}
