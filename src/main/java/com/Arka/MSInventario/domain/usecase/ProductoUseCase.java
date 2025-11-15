package com.Arka.MSInventario.domain.usecase;

import com.Arka.MSInventario.application.dto.ProductCartDto;
import com.Arka.MSInventario.application.dto.ProductoDTO;
import com.Arka.MSInventario.application.dto.ProductoUpdateDTO;
import com.Arka.MSInventario.application.mapper.ProductoMapper;
import com.Arka.MSInventario.application.service.ProductoValidationService;
import com.Arka.MSInventario.domain.exception.ProductoNoEncontradoException;
import com.Arka.MSInventario.domain.model.HistorialStock;
import com.Arka.MSInventario.domain.model.Producto;
import com.Arka.MSInventario.domain.model.gateway.HistorialStockGateway;
import com.Arka.MSInventario.domain.model.gateway.ProductoGateway;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class ProductoUseCase {
    // Gateway para acceder a la persistencia de productos
    private final ProductoGateway productoGateway;
    private final HistorialStockGateway historialStockGateway;
    private final ProductoValidationService validationService;
    private final ProductoMapper mapper;

    public ProductoUseCase(
            ProductoGateway productoGateway,
            HistorialStockGateway historialStockGateway,
            ProductoValidationService validationService,
            ProductoMapper mapper) {
        this.productoGateway = productoGateway;
        this.historialStockGateway = historialStockGateway;
        this.validationService = validationService;
        this.mapper = mapper;
    }

    // Crea un nuevo producto en el sistema.
    public Producto saveProducto(Producto producto) {
        // Validar usando el servicio de validación
        validationService.validarNuevoProducto(producto);

        // Guardar el producto
        Producto nuevoProducto = productoGateway.saveProductos(producto);

        // Registrar en el historial de stock
        registrarHistorialStock(nuevoProducto.getId(), nuevoProducto.getStock());

        return nuevoProducto;
    }

    // Busca un producto por su ID y lo retorna como DTO.
    public Optional<ProductoDTO> buscarPorId(Long id) {
        return productoGateway.buscarPorId(id)
                .map(mapper::toDTO);
    }

    // Actualiza un producto existente con los datos del DTO proporcionado.
    public Producto actualizarProducto(Long id, ProductoUpdateDTO productoUpdateDTO) {
        // Buscar el producto existente
        Producto productoUpdate = productoGateway.buscarPorId(id)
                .orElseThrow(ProductoNoEncontradoException::new);

        // Guardar el stock anterior para el historial
        int stockAnterior = productoUpdate.getStock();

        // Aplicar los cambios del DTO al objeto existente
        if (productoUpdateDTO.getNombre() != null) {
            productoUpdate.setNombre(productoUpdateDTO.getNombre());
        }
        if (productoUpdateDTO.getDescripcion() != null) {
            productoUpdate.setDescripcion(productoUpdateDTO.getDescripcion());
        }
        if (productoUpdateDTO.getPrecio() != null) {
            productoUpdate.setPrecio(productoUpdateDTO.getPrecio());
        }
        if (productoUpdateDTO.getStock() != null) {
            productoUpdate.setStock(productoUpdateDTO.getStock());
        }
        if (productoUpdateDTO.getCategoria() != null) {
            productoUpdate.setCategoria(productoUpdateDTO.getCategoria());
        }
        if (productoUpdateDTO.getUmbralStockBajo() != null) {
            productoUpdate.setUmbralStockBajo(productoUpdateDTO.getUmbralStockBajo());
        }

        // Validar el producto actualizado
        validationService.validarActualizacionProducto(productoUpdate);

        // Guardar el producto actualizado
        Producto productoActualizado = productoGateway.saveProductos(productoUpdate);

        // Registrar cambio en historial si el stock cambió
        if (productoActualizado.stockHaCambiado(stockAnterior)) {
            registrarHistorialStock(productoActualizado.getId(), productoActualizado.getStock());
        }

        return productoActualizado;
    }

    // Obtiene todos los productos y los retorna como DTOs.
    public List<ProductoDTO> obtenerTodosLosProductosDTO() {
        List<Producto> productos = productoGateway.findAll();
        return productos.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }


    // Busca productos por nombre y los retorna como DTOs.
    public List<ProductoDTO> buscarProductosDTO(String nombre) {
        List<Producto> productos = productoGateway.buscarProductos(nombre);
        return productos.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    // Obtiene el stock y precio de un producto por su ID.
    public ProductCartDto obtenerStockPriceProducto(Long id) {
        Producto producto = productoGateway.buscarPorId(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));

        return mapper.toCartDTO(producto);
    }

    // Registra un cambio en el historial de stock.
    private void registrarHistorialStock(Long productoId, int cantidadNueva) {
        HistorialStock historial = new HistorialStock();
        historial.setId_Producto(productoId);
        historial.setCantidad_nueva(cantidadNueva);
        historialStockGateway.saveHistorialStock(historial);
    }
}