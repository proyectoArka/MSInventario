package com.Arka.MSInventario.infrastructure.controller;

import com.Arka.MSInventario.application.dto.ProductoDTO;
import com.Arka.MSInventario.domain.model.Producto;
import com.Arka.MSInventario.domain.model.gateway.ProductoGateway;
import com.Arka.MSInventario.domain.usecase.ProductoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos/v1")
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
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<Object> buscarProducto(@PathVariable String nombre) {
        try {
            Optional<Producto> producto = productoUseCase.buscarPorNombre(nombre);
            return producto.<ResponseEntity<Object>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(404).body("Producto no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno");
        }
    }

    @PutMapping("/actualizar/{nombre}")
    public ResponseEntity<Object> actualizarProducto(@PathVariable String nombre, @RequestBody Producto producto) {
        try {
            Producto productoActualizado = productoUseCase.actualizarProducto(nombre, producto);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
