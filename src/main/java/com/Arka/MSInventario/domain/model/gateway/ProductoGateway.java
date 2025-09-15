package com.Arka.MSInventario.domain.model.gateway;

import com.Arka.MSInventario.domain.model.Producto;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para el acceso y manipulación de productos en el sistema.
 */
public interface ProductoGateway {
    /**
     * Guarda un producto en el sistema.
     * @param producto el producto a guardar
     * @return el producto guardado
     */
    Producto saveProductos(Producto producto);

    /**
     * Busca un producto por su nombre.
     * @param nombre el nombre del producto a buscar
     * @return un Optional que contiene el producto si se encuentra, o vacío si no
     */
    Optional<Producto> buscarPorNombre(String nombre);
    List<Producto> findAll();
}
