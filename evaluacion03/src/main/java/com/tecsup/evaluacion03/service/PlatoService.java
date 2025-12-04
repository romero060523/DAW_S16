package com.tecsup.evaluacion03.service;

import com.tecsup.evaluacion03.model.Plato;
import com.tecsup.evaluacion03.enums.TipoPlato;

import java.util.List;
import java.util.Optional;

public interface PlatoService {
    Plato save(Plato plato);
    Plato update(Long id, Plato plato);
    void delete(Long id);
    List<Plato> findAll();
    Plato findById(Long id);

    List<Plato> findByTipo(TipoPlato tipo);
    List<Plato> findActivos();
    Plato findByNombre(String nombre);

    // Métodos adicionales según la lógica de negocio
}
