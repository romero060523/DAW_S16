package com.tecsup.evaluacion03.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "bitacora")
public class Bitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El usuario no puede estar vacío")
    @Column(nullable = false, length = 50)
    private String usuario;

    @NotBlank(message = "La acción no puede estar vacía")
    @Column(nullable = false, length = 50)
    private String accion;

    @NotBlank(message = "La entidad no puede estar vacía")
    @Column(nullable = false, length = 50)
    private String entidad;

    @Column
    private Long entidadId;

    @Column(length = 1000)
    private String detalles;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaHora;

    // Constructores
    public Bitacora() {
    }

    public Bitacora(String usuario, String accion, String entidad, Long entidadId, String detalles) {
        this.usuario = usuario;
        this.accion = accion;
        this.entidad = entidad;
        this.entidadId = entidadId;
        this.detalles = detalles;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public Long getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(Long entidadId) {
        this.entidadId = entidadId;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
