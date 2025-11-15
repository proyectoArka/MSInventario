package com.Arka.MSInventario.domain.usecase;

import com.Arka.MSInventario.application.dto.ProductCartDto;
import com.Arka.MSInventario.application.dto.ProductoDTO;
import com.Arka.MSInventario.application.dto.ProductoUpdateDTO;
import com.Arka.MSInventario.domain.model.Categoria;
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
     * @param id Nombre del producto
     * @return Un Optional con el producto si existe, vacío en caso contrario
     */
    public Optional<ProductoDTO> buscarPorId(Long id) {
        return productoGateway.buscarPorId(id)
                .map(this::toDTO);
    }
    /**
     * Actualiza los datos de un producto existente.
     *
     * @param id del producto a actualizar
     * @param productoUpdateDTO Datos nuevos del producto
     * @return El producto actualizado
     * @throws Exception si el producto no existe
     */
    public Producto actualizarProducto(Long id, ProductoUpdateDTO productoUpdateDTO) {
        Optional<Producto> productoExistente = productoGateway.buscarPorId(id);
        if (productoExistente.isEmpty()) {
            throw new Exception("El producto no existe.");
        }
            // 1. APLICAR LOS CAMBIOS DEL DTO AL OBJETO EXISTENTE   
        Producto productoUpdate = productoExistente.get();
        // Guardar el stock anterior para el historial
        int stockAnterior = productoUpdate.getStock();

        // Si el DTO trae un nuevo nombre, lo actualiza.
        if (productoUpdateDTO.getNombre() != null) {
            productoUpdate.setNombre(productoUpdateDTO.getNombre());
        }

        // Si el DTO trae una nueva descripción, la actualiza.
        if (productoUpdateDTO.getDescripcion() != null) {
            productoUpdate.setDescripcion(productoUpdateDTO.getDescripcion());
        }

        // Repetir el patrón para todos los campos:
        if (productoUpdateDTO.getPrecio() != null) {
            productoUpdate.setPrecio(productoUpdateDTO.getPrecio());
        }
        if (productoUpdateDTO.getStock() != null) {
            productoUpdate.setStock(productoUpdateDTO.getStock());
        }
        if (productoUpdateDTO.getCategoria() != null) {
            // Debes validar aquí si la categoría es válida si no lo hiciste en el DTO
            productoUpdate.setCategoria(productoUpdateDTO.getCategoria());
        }
        if (productoUpdateDTO.getUmbralStockBajo() != null) {
            productoUpdate.setUmbralStockBajo(productoUpdateDTO.getUmbralStockBajo());
        }

        // 2. REALIZAR VALIDACIONES SOBRE EL OBJETO FINAL

        // Ahora, si la descripción es null, no se invoca getDescripcion()
        // Si se actualizó la descripción, se valida su longitud.
        if (productoUpdate.getDescripcion() != null && productoUpdate.getDescripcion().length() > 200) {
            throw new Exception("La descripcion no puede tener mas de 200 caracteres, tiene: " + productoUpdateDTO.getDescripcion().length());
        }

        // Si se actualizó el nombre, se valida su longitud.
        if (productoUpdate.getNombre() != null && productoUpdate.getNombre().length() > 50) {
            throw new Exception("El nombre no puede tener mas de 50 caracteres, tiene: " + productoUpdateDTO.getNombre().length());
        }

        // La validación de stock negativo se realiza sobre el valor final
        if (productoUpdate.getStock() < 0) {
            throw new Exception("El stock no puede ser negativo.");
        }

        // 3. GUARDAR Y REGISTRAR HISTORIAL
        Producto productoActualizado = productoGateway.saveProductos(productoUpdate);

        // Lógica para historial de stock (ya la tienes, es correcta)
        if (productoActualizado.getStock() != stockAnterior) {
            HistorialStock historial = new HistorialStock();
            historial.setId_Producto(productoActualizado.getId());
            historial.setCantidad_nueva(productoActualizado.getStock());
            historialStockGateway.saveHistorialStock(historial);
        }

        return productoActualizado;
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
            dto.setId(producto.getId());
            dto.setStock(producto.getStock());
            dto.setUmbralStockBajo(producto.getUmbralStockBajo());
            dto.setCreatedAt(producto.getCreatedAt());
            // Obtener el nombre de la categoría usando el id
            dto.setCategoria(
                    categoriaGateway.buscarCategoriaPorId(producto.getCategoria())
                            .map(Categoria::getNombre)
                            .orElse("Desconocida")
        );
        return dto;
    }

    public   List<ProductoDTO> buscarProductosDTO(String nombre) {
        List<Producto> productos = productoGateway.buscarProductos(nombre);
        return productos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProductCartDto obtenerStockPriceProducto(Long id) {
        Optional<Producto> producto = productoGateway.buscarPorId(id);
        if (producto.isEmpty()) {
            throw new Exception("El producto no existe.");
        }
        return new ProductCartDto(
                producto.get().getNombre(),
                producto.get().getDescripcion(),
                producto.get().getStock(),
                producto.get().getPrecio()
        );
    }

//    private ProductoClienteDTO toClienteDTO(Producto producto) {
//        ProductoClienteDTO dto = new ProductoClienteDTO();
//            dto.setNombre(producto.getNombre());
//            dto.setDescripcion(producto.getDescripcion());
//            dto.setPrecio(producto.getPrecio());
//            dto.setStock(producto.getStock());
//            // Obtener el nombre de la categoría usando el id
//            dto.setCategoria(
//                    categoriaGateway.buscarCategoriaPorId(producto.getCategoria())
//                            .map(Categoria::getNombre)
//                            .orElse("Desconocida")
//        );
//        return dto;
//    }

}