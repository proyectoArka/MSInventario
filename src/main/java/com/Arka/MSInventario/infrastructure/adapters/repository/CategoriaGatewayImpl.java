package com.Arka.MSInventario.infrastructure.adapters.repository;

import com.Arka.MSInventario.domain.model.Categoria;
import com.Arka.MSInventario.domain.model.gateway.CategoriaGateway;
import com.Arka.MSInventario.infrastructure.adapters.entity.CategoriaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

}
