package com.tecsup.evaluacion03.repository;

import com.tecsup.evaluacion03.model.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BitacoraRepository extends JpaRepository<Bitacora, Long> {

    /**
     * Busca registros de auditoría por usuario
     */
    List<Bitacora> findByUsuario(String usuario);

    /**
     * Busca registros de auditoría por acción
     */
    List<Bitacora> findByAccion(String accion);

    /**
     * Busca registros de auditoría por entidad afectada
     */
    List<Bitacora> findByEntidad(String entidad);

    /**
     * Busca registros de auditoría dentro de un rango de fechas
     */
    List<Bitacora> findByFechaHoraBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    /**
     * Busca registros de auditoría por usuario y acción
     */
    List<Bitacora> findByUsuarioAndAccion(String usuario, String accion);
}
