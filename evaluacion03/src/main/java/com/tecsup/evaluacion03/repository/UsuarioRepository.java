package com.tecsup.evaluacion03.repository;

import com.tecsup.evaluacion03.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por nombre de usuario (login)
     */
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    /**
     * Busca todos los usuarios activos
     */
    List<Usuario> findByActivoTrue();

    /**
     * Busca usuarios por nombre o apellido
     */
    List<Usuario> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);

    /**
     * Verifica si un usuario existe por nombre de usuario
     */
    boolean existsByNombreUsuario(String nombreUsuario);
}
