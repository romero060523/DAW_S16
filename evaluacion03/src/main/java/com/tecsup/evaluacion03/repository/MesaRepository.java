package com.tecsup.evaluacion03.repository;

import com.tecsup.evaluacion03.model.Mesa;
import com.tecsup.evaluacion03.enums.EstadoMesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    /**
     * Busca una mesa por número
     */
    Optional<Mesa> findByNumero(String numero);

    /**
     * Busca todas las mesas disponibles
     */
    List<Mesa> findByEstado(EstadoMesa estado);

    /**
     * Busca todas las mesas disponibles
     */
    List<Mesa> findByEstadoAndCapacidadGreaterThanEqual(EstadoMesa estado, Integer capacidad);

    /**
     * Cuenta mesas disponibles
     */
    long countByEstado(EstadoMesa estado);
}
