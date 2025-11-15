package com.Arka.MSInventario.infrastructure.adapters.entity;

import com.Arka.MSInventario.domain.model.HistorialStock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "HistorialStock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistorialStockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDateTime fecha_Cambio;

    @Column(nullable = false)
    private int cantidad_nueva;

    @ManyToOne
    @JoinColumn(nullable = false, name = "IdProducto")
    private ProductoEntity IdProducto;


    @PrePersist
    public void prePersist() {
        this.fecha_Cambio = LocalDateTime.now();
    }

    // Constructor para convertir de dominio a entidad
    public HistorialStockEntity(HistorialStock historialStock, ProductoEntity productoEntity) {
        this.id = historialStock.getId();
        this.fecha_Cambio = historialStock.getFecha_Cambio();
        this.cantidad_nueva = historialStock.getCantidad_nueva();
        this.IdProducto = productoEntity;
    }

    // Metodo para convertir de entidad a dominio
    public HistorialStock toDomain(){
        return new HistorialStock(
                this.id,
                this.IdProducto != null ? this.IdProducto.getId() : 0,
                this.fecha_Cambio,
                this.cantidad_nueva
        );
    }
}
