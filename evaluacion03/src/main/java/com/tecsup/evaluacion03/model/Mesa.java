package com.tecsup.evaluacion03.model;

import com.tecsup.evaluacion03.enums.EstadoMesa;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "mesa")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El número de mesa no puede estar vacío")
    @Column(nullable = false, unique = true, length = 50)
    private String numero;

    @NotNull(message = "La capacidad es requerida")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    @Column(nullable = false)
    private Integer capacidad;

    @NotNull(message = "El estado de la mesa es requerido")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMesa estado = EstadoMesa.DISPONIBLE;

    @Column(length = 100)
    private String ubicacion;

    // Relación OneToMany con Pedido
    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Pedido> pedidos;

    // Constructores
    public Mesa() {
    }

    public Mesa(String numero, Integer capacidad, EstadoMesa estado) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    // Métodos de negocio
    /**
     * Marca la mesa como ocupada
     */
    public void ocupar() {
        this.estado = EstadoMesa.OCUPADA;
    }

    /**
     * Marca la mesa como disponible
     */
    public void liberar() {
        this.estado = EstadoMesa.DISPONIBLE;
    }

    /**
     * Verifica si la mesa está disponible
     */
    public boolean estaDisponible() {
        return EstadoMesa.DISPONIBLE.equals(this.estado);
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoMesa getEstado() {
        return estado;
    }

    public void setEstado(EstadoMesa estado) {
        this.estado = estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
