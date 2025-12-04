package com.tecsup.evaluacion03.repository;

import com.tecsup.evaluacion03.model.Plato;
import com.tecsup.evaluacion03.enums.TipoPlato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Long> {

    /**
     * Busca todos los platos activos
     */
    List<Plato> findByActivoTrue();

    /**
     * Busca platos por tipo
     */
    List<Plato> findByTipo(TipoPlato tipo);

    /**
     * Busca platos activos por tipo
     */
    List<Plato> findByTipoAndActivoTrue(TipoPlato tipo);

    /**
     * Busca un plato por nombre exacto
     */
    Plato findByNombre(String nombre);

    /**
     * Busca platos cuyo nombre contenga una palabra clave
     */
    List<Plato> findByNombreContainingIgnoreCase(String nombre);
}
