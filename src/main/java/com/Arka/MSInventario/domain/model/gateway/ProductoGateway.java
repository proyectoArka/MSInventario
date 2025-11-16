package com.Arka.MSInventario.domain.model.gateway;

import com.Arka.MSInventario.domain.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoGateway {

    Producto saveProductos(Producto producto);
    Optional<Producto> buscarPorNombre(String nombre);
    List<Producto> findAll();
    List<Producto> buscarProductos(String nombre);
    Optional<Producto> buscarPorId(Long id);
    // Nuevo método para verificar si existen productos por categoría
    boolean existenProductosPorCategoria(Long categoriaId);

    /**
     * Da de baja lógica a un producto cambiando el campo isDelete a true.
     * @param id el ID del producto
     * @return el producto dado de baja
     */
    Producto darDeBajaProducto(Long id);

    /**
     * Obtiene todos los productos activos cuyo stock está por debajo o igual al umbral de stock bajo.
     * @return lista de productos con stock bajo
     */
    List<Producto> obtenerProductosConStockBajo();
}
