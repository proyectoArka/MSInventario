package com.Arka.MSInventario.infrastructure.adapters.entity;

import com.Arka.MSInventario.domain.model.Categoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "Categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private java.util.List<ProductoEntity> productos;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public CategoriaEntity (Categoria categoria) {
        this.id = categoria.getId();
        this.nombre = categoria.getNombre();
        this.createdAt = categoria.getCreatedAt();
    }

    public Categoria toDomain() {
        return new Categoria(
                this.id,
                this.nombre,
                this.createdAt
        );
    }
}
