package com.tecsup.evaluacion03.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del rol no puede estar vacío")
    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @Column(length = 200)
    private String descripcion;

    // Relación ManyToMany con Usuario (inversa)
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<Usuario> usuarios;

    // Constructores
    public Rol() {
    }

    public Rol(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
