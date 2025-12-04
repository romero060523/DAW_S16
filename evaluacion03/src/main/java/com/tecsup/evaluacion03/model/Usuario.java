package com.tecsup.evaluacion03.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Column(nullable = false, unique = true, length = 50)
    private String nombreUsuario;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Column(nullable = false, length = 255)
    private String password;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String apellido;

    @NotNull(message = "El estado activo es requerido")
    @Column(nullable = false)
    private Boolean activo = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Column
    private LocalDateTime lastLogin;

    @Min(value = 0, message = "Los intentos no pueden ser negativos")
    @Column
    private Integer intentosFallidos = 0;

    // Relación ManyToMany con Rol
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> roles;

    // Constructores
    public Usuario() {
    }

    public Usuario(String nombreUsuario, String password, String nombre, String apellido) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.activo = true;
    }

    // Métodos de negocio
    /**
     * Retorna el nombre completo del usuario
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    /**
     * Incrementa los intentos fallidos (para bloqueo de cuenta)
     */
    public void incrementarIntentosFallidos() {
        if (this.intentosFallidos == null) {
            this.intentosFallidos = 0;
        }
        this.intentosFallidos++;
    }

    /**
     * Reinicia los intentos fallidos
     */
    public void reiniciarIntentosFallidos() {
        this.intentosFallidos = 0;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getIntentosFallidos() {
        return intentosFallidos;
    }

    public void setIntentosFallidos(Integer intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}
