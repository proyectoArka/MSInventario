package com.Arka.MSInventario.infrastructure.controller;

import com.Arka.MSInventario.application.dto.ProductoDTO;
import com.Arka.MSInventario.application.dto.ProductoUpdateDTO;
import com.Arka.MSInventario.domain.model.Producto;
import com.Arka.MSInventario.domain.usecase.ProductoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoUseCase productoUseCase;

    @PostMapping("/crear")
    public ResponseEntity<Producto> CrearProducto(@RequestBody Producto producto){
        Producto nuevoProducto = productoUseCase.saveProducto(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarProductos() {
        try {
            List<ProductoDTO> productos = productoUseCase.obtenerTodosLosProductosDTO();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno");
        }
    }
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarProducto(@PathVariable Long id) {
        try {
            Optional<ProductoDTO> producto = productoUseCase.buscarPorId(id);
            return producto.<ResponseEntity<Object>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(404).body("Producto no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno");
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Object> actualizarProducto(@PathVariable Long id, @RequestBody ProductoUpdateDTO productoUpdateDTO) {
        try {
            Producto productoActualizado = productoUseCase.actualizarProducto(id, productoUpdateDTO);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/buscarProductos/{nombre}")
    public ResponseEntity<Object> buscarProductos(@PathVariable String nombre){
        try {
            List<ProductoDTO> productoDTO = productoUseCase.buscarProductosDTO(nombre);
            return ResponseEntity.ok(productoDTO);
        }
        catch (Exception e){
            return ResponseEntity.status(500).body("Error interno..." + e.getMessage());
        }
    }
}
