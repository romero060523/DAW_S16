package com.tecsup.evaluacion03.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    @NotNull(message = "El pedido es requerido")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plato_id", nullable = false)
    @NotNull(message = "El plato es requerido")
    private Plato plato;

    @NotNull(message = "La cantidad es requerida")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Column(nullable = false)
    private Integer cantidad;

    @NotNull(message = "El precio unitario es requerido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio unitario debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @NotNull(message = "El subtotal es requerido")
    @DecimalMin(value = "0.0", message = "El subtotal no puede ser negativo")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    // Constructores
    public DetallePedido() {
    }

    public DetallePedido(Plato plato, Integer cantidad) {
        this.plato = plato;
        this.cantidad = cantidad;
        this.precioUnitario = plato.getPrecio();
        this.calcularSubtotal();
    }

    // Métodos de negocio
    /**
     * Calcula el subtotal multiplicando precioUnitario * cantidad
     * Se debe llamar cada vez que se modifique cantidad o precioUnitario
     */
    public void calcularSubtotal() {
        if (this.precioUnitario != null && this.cantidad != null) {
            this.subtotal = this.precioUnitario.multiply(new BigDecimal(this.cantidad));
        }
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        this.calcularSubtotal(); // Recalcula al cambiar cantidad
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
        this.calcularSubtotal(); // Recalcula al cambiar precio
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
