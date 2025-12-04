package com.tecsup.evaluacion03.repository;

import com.tecsup.evaluacion03.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    /**
     * Busca un rol por nombre exacto
     */
    Optional<Rol> findByNombre(String nombre);

    /**
     * Verifica si un rol existe
     */
    boolean existsByNombre(String nombre);
}
