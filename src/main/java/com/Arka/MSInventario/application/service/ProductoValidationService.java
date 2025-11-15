package com.Arka.MSInventario.application.service;

import com.Arka.MSInventario.domain.exception.CategoriaNoEncontradaException;
import com.Arka.MSInventario.domain.exception.ProductoYaExisteException;
import com.Arka.MSInventario.domain.model.Producto;
import com.Arka.MSInventario.domain.model.gateway.CategoriaGateway;
import com.Arka.MSInventario.domain.model.gateway.ProductoGateway;

public class ProductoValidationService {

    private final ProductoGateway productoGateway;
    private final CategoriaGateway categoriaGateway;

    public ProductoValidationService(ProductoGateway productoGateway, CategoriaGateway categoriaGateway) {
        this.productoGateway = productoGateway;
        this.categoriaGateway = categoriaGateway;
    }

    // valida que el producto no exista en el sistema
    public void validarProductoNoExiste(String nombre) {
        if (productoGateway.buscarPorNombre(nombre).isPresent()) {
            throw new ProductoYaExisteException(nombre);
        }
    }

    // valida que la categoria exista en el sistema
    public void validarCategoriaExiste(Long categoriaId) {
        if (categoriaGateway.buscarCategoriaPorId(categoriaId).isEmpty()) {
            throw new CategoriaNoEncontradaException(categoriaId);
        }
    }

    // Valida las reglas de negocio para crear un nuevo producto.
    public void validarNuevoProducto(Producto producto) {
        validarProductoNoExiste(producto.getNombre());
        validarCategoriaExiste(producto.getCategoria());
        producto.validar();
    }

    // Valida las reglas de negocio para actualizar un producto existente.
    public void validarActualizacionProducto(Producto producto) {
        validarCategoriaExiste(producto.getCategoria());
        producto.validar();
    }
}

