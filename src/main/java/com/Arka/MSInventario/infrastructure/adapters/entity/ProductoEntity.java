package com.Arka.MSInventario.infrastructure.adapters.entity;

import com.Arka.MSInventario.domain.model.Producto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(length = 200)
    private String descripcion;

    @Column(nullable = false)
    private int precio;

    @Column(nullable = false)
    private int stock;

    @ManyToOne
    @JoinColumn(name = "categoria", nullable = false)
    private CategoriaEntity categoria;

    @Column(nullable = false)
    private int UmbralStockBajo;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "IdProducto", cascade = CascadeType.ALL)
    private List<HistorialStockEntity> historialStock;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // Metodo para convertir un objeto de dominio Producto a una entidad ProductoEntity
    public ProductoEntity(Producto producto, CategoriaEntity categoriaEntity) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.descripcion = producto.getDescripcion();
        this.precio = producto.getPrecio();
        this.stock = producto.getStock();
        this.categoria = categoriaEntity;
        this.UmbralStockBajo = producto.getUmbralStockBajo();
        this.createdAt = producto.getCreatedAt();
    }

    // Metodo para convertir una entidad ProductoEntity a un objeto de dominio Producto
    public Producto toDomain() {
        return new Producto(
                id,
                nombre,
                descripcion,
                precio,
                stock,
                categoria != null ? categoria.getId() : null,
                UmbralStockBajo,
                createdAt);
    }
}
