package com.Arka.MSInventario.infrastructure.controller;

import com.Arka.MSInventario.domain.model.Categoria;
import com.Arka.MSInventario.domain.model.Producto;
import com.Arka.MSInventario.domain.usecase.CategoriaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/categoria/v1")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaUseCase categoriaUseCase;

    @PostMapping("/nuevaCategoria")
    public ResponseEntity<Categoria> nuevaCategoria(@RequestBody Categoria categoria) {
        Categoria nuevaCategoria = categoriaUseCase.saveCategoria(categoria);
        return ResponseEntity.ok(nuevaCategoria);

    }
}
