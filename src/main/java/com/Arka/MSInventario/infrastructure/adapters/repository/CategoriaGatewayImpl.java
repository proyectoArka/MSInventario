package com.Arka.MSInventario.infrastructure.adapters.repository;

import com.Arka.MSInventario.domain.exception.CategoriaNoEncontradaException;
import com.Arka.MSInventario.domain.model.Categoria;
import com.Arka.MSInventario.domain.model.gateway.CategoriaGateway;
import com.Arka.MSInventario.infrastructure.adapters.entity.CategoriaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoriaGatewayImpl implements CategoriaGateway {
    private final CategoriaRepository categoriaRepository;

    @Override
    public Categoria saveCategoria(Categoria categoria) {
        CategoriaEntity entity = new CategoriaEntity(categoria);
        return categoriaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<Categoria> bucarCategoriaPorNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre)
                .map(CategoriaEntity::toDomain);
    }

    @Override
    public Optional<Categoria> buscarCategoriaPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(CategoriaEntity::toDomain);
    }

    @Override
    public List<Categoria> obtenerTodasCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Categoria actualizarCategoria(Long id, Categoria categoria) {
        CategoriaEntity entity = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNoEncontradaException(id));

        entity.setNombre(categoria.getNombre());

        return categoriaRepository.save(entity).toDomain();
    }

    @Override
    public void eliminarCategoria(Long id) {
        CategoriaEntity entity = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNoEncontradaException(id));

        categoriaRepository.delete(entity);
    }

}
