package com.tecsup.evaluacion03.repository;

import com.tecsup.evaluacion03.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Busca un cliente por DNI
     */
    Optional<Cliente> findByDni(String dni);

    /**
     * Busca todos los clientes activos
     */
    List<Cliente> findByActivoTrue();

    /**
     * Busca clientes por nombre o apellido
     */
    List<Cliente> findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(String nombres, String apellidos);

    /**
     * Busca un cliente por correo
     */
    Optional<Cliente> findByCorreo(String correo);

    /**
     * Busca clientes activos por nombre
     */
    List<Cliente> findByNombresContainingIgnoreCaseAndActivoTrue(String nombres);
}
