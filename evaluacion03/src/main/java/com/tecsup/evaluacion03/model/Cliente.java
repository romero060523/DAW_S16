package com.tecsup.evaluacion03.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 12, unique = true, nullable = true)
    private String dni;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String nombres;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(length = 20)
    private String telefono;

    @Email(message = "El correo debe ser válido")
    @Column(length = 100)
    private String correo;

    @NotNull(message = "El estado activo es requerido")
    @Column(nullable = false)
    private Boolean activo = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    // Relación OneToMany con Pedido
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Pedido> pedidos;

    // Constructores
    public Cliente() {
    }

    public Cliente(String dni, String nombres, String apellidos, String telefono, String correo) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.activo = true;
    }

    // Métodos de negocio
    /**
     * Retorna el nombre completo del cliente
     */
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
