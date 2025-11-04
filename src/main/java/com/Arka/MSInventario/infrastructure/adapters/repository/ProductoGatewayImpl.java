package com.Arka.MSInventario.infrastructure.adapters.repository;

import com.Arka.MSInventario.domain.model.Producto;
import com.Arka.MSInventario.domain.model.gateway.ProductoGateway;
import com.Arka.MSInventario.infrastructure.adapters.entity.CategoriaEntity;
import com.Arka.MSInventario.infrastructure.adapters.entity.ProductoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
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
    public List<Producto> findAll() {
        return productoRepository.findAll()
                .stream()
                .map(ProductoEntity::toDomain)
                .toList();
    }

    @Override
    public List<Producto> buscarProductos(String nombre) {
         List<Producto> productos = new java.util.ArrayList<>(productoRepository.findByNombreContainingIgnoreCase(nombre)
                 .stream()
                 .map(ProductoEntity::toDomain)
                 .toList());
        // Si se encontraron productos, buscar productos de la misma categoría que el primero
        Optional<Producto> primerProducto = productos.stream().findFirst();
        Long categoriaProducto = primerProducto.map(Producto::getCategoria).orElse(null);

        if (categoriaProducto != null) {
            List<Producto> productosMismaCategoria = productoRepository.findByCategoria_Id(categoriaProducto)
                    .stream()
                    .map(ProductoEntity::toDomain)
                    .toList();

            // Combina ambas listas y elimina duplicados
            productos.addAll(productosMismaCategoria);
            productos = productos.stream().distinct().toList();
        }

        return productos;

    }

    @Override
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id)
                .map(ProductoEntity::toDomain);
    }
}
