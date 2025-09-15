package com.Arka.MSInventario.domain.usecase;

import com.Arka.MSInventario.application.dto.ProductoDTO;
import com.Arka.MSInventario.domain.model.HistorialStock;
import com.Arka.MSInventario.domain.model.Producto;
import com.Arka.MSInventario.domain.model.exception.Exception;
import com.Arka.MSInventario.domain.model.gateway.CategoriaGateway;
import com.Arka.MSInventario.domain.model.gateway.HistorialStockGateway;
import com.Arka.MSInventario.domain.model.gateway.ProductoGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/** Caso de uso para la gestión de productos.*/
@RequiredArgsConstructor
public class ProductoUseCase {
    // Gateway para acceder a la persistencia de productos
    private final ProductoGateway productoGateway;
    private final HistorialStockGateway historialStockGateway;
    private final CategoriaGateway categoriaGateway;

    /**
     * Guarda un nuevo producto si no existe uno con el mismo nombre.
     *
     * @param producto El producto a guardar
     * @return El producto guardado
     * @throws Exception si el producto ya existe
     */
    public Producto saveProducto(Producto producto) {
        if (productoGateway.buscarPorNombre(producto.getNombre()).isPresent()) {
            throw new Exception("El producto " + producto.getNombre() + " ya existe.");
        }
        if(categoriaGateway.buscarCategoriaPorId(producto.getCategoria()).isEmpty()){
            throw new Exception("La categoria con id " + producto.getCategoria() + " no existe.");
        }
        if (producto.getStock() < 0) {
            throw new Exception("El stock no puede ser negativo.");
        }
        if(producto.getDescripcion().length() > 200){
            throw new Exception("La descripcion no puede tener mas de 200 caracteres, tiene: " + producto.getDescripcion().length());
        }
        if(producto.getNombre().length() > 50){
            throw new Exception("El nombre no puede tener mas de 50 caracteres, tiene: " + producto.getNombre().length());
        }
        if(producto.getUmbralStockBajo() < 0){
            throw new Exception("El umbral de stock bajo no puede ser negativo");
        }

        Producto nuevoProducto = productoGateway.saveProductos(producto);

        HistorialStock historial = new HistorialStock();

        historial.setId_Producto(nuevoProducto.getId());
        historial.setCantidad_nueva(nuevoProducto.getStock());

        historialStockGateway.saveHistorialStock(historial);

        return nuevoProducto;
    }

    /**
     * Busca un producto por su nombre.
     *
     * @param nombre Nombre del producto
     * @return Un Optional con el producto si existe, vacío en caso contrario
     */
    public Optional<Producto> buscarPorNombre(String nombre) {
        return productoGateway.buscarPorNombre(nombre);
    }

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param nombre Nombre del producto a actualizar
     * @param producto Datos nuevos del producto
     * @return El producto actualizado
     * @throws Exception si el producto no existe
     */
    public Producto actualizarProducto(String nombre, Producto producto) {
        Optional<Producto> productoExistente = productoGateway.buscarPorNombre(nombre);
        if (productoExistente.isEmpty()) {
            throw new Exception("El producto " + nombre + " no existe.");
        }
        if (producto.getStock() < 0) {
            throw new Exception("El stock no puede ser negativo.");
        }
        if(producto.getDescripcion().length() > 200){
            throw new Exception("La descripcion no puede tener mas de 200 caracteres, tiene: " + producto.getDescripcion().length());
        }
        if(producto.getNombre().length() > 50){
            throw new Exception("El nombre no puede tener mas de 50 caracteres, tiene: " + producto.getNombre().length());
        }
        if(producto.getUmbralStockBajo() < 0){
            throw new Exception("El umbral de stock bajo no puede ser negativo");
        }

        int stockAnterior = productoExistente.get().getStock();

        Producto prodToUpdate = productoExistente.get();
        prodToUpdate.setNombre(producto.getNombre());
        prodToUpdate.setDescripcion(producto.getDescripcion());
        prodToUpdate.setPrecio(producto.getPrecio());
        prodToUpdate.setStock(producto.getStock());
        prodToUpdate.setCategoria(producto.getCategoria());
        prodToUpdate.setUmbralStockBajo(producto.getUmbralStockBajo());


        Producto ProductoActualizado = productoGateway.saveProductos(prodToUpdate);
        if (producto.getStock() == stockAnterior) {
            return ProductoActualizado; // No hay cambio en el stock, no registrar en historial
        }
        HistorialStock historial = new HistorialStock();

        historial.setId_Producto(ProductoActualizado.getId());
        historial.setCantidad_nueva(ProductoActualizado.getStock());
        historialStockGateway.saveHistorialStock(historial);

        return ProductoActualizado;
    }

    public List<ProductoDTO> obtenerTodosLosProductosDTO() {
        List<Producto> productos = productoGateway.findAll();
        return productos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ProductoDTO toDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        // Obtener el nombre de la categoría usando el id
        dto.setCategoria(
                categoriaGateway.buscarCategoriaPorId(producto.getCategoria())
                        .map(categoria -> categoria.getNombre())
                        .orElse("Desconocida")
        );
        return dto;
    }

}