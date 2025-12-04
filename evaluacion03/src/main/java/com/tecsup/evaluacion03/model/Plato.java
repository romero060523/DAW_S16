package com.tecsup.evaluacion03.model;

import com.tecsup.evaluacion03.enums.TipoPlato;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "plato")
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del plato no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotNull(message = "El tipo de plato es requerido")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPlato tipo;

    @NotNull(message = "El precio es requerido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(length = 500)
    private String descripcion;

    @NotNull(message = "El estado activo es requerido")
    @Column(nullable = false)
    private Boolean activo = true;

    @Min(value = 0, message = "El tiempo de preparación no puede ser negativo")
    @Column
    private Integer tiempoPreparacionMinutos;

    // Relación OneToMany con DetallePedido
    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;

    // Constructores
    public Plato() {
    }

    public Plato(String nombre, TipoPlato tipo, BigDecimal precio, String descripcion, Boolean activo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoPlato getTipo() {
        return tipo;
    }

    public void setTipo(TipoPlato tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Integer getTiempoPreparacionMinutos() {
        return tiempoPreparacionMinutos;
    }

    public void setTiempoPreparacionMinutos(Integer tiempoPreparacionMinutos) {
        this.tiempoPreparacionMinutos = tiempoPreparacionMinutos;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }
}
