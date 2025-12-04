package com.tecsup.evaluacion03.service.impl;

import com.tecsup.evaluacion03.enums.TipoPlato;
import com.tecsup.evaluacion03.model.Plato;
import com.tecsup.evaluacion03.repository.PlatoRepository;
import com.tecsup.evaluacion03.service.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlatoServiceImpl implements PlatoService {

    private final PlatoRepository platoRepository;

    @Autowired
    public PlatoServiceImpl(PlatoRepository platoRepository) {
        this.platoRepository = platoRepository;
    }

    @Override
    public Plato save(Plato plato) {
        return platoRepository.save(plato);
    }

    @Override
    public Plato update(Long id, Plato plato) {
        Plato exist = platoRepository.findById(id).orElseThrow();
        exist.setNombre(plato.getNombre());
        exist.setTipo(plato.getTipo());
        exist.setPrecio(plato.getPrecio());
        exist.setDescripcion(plato.getDescripcion());
        exist.setActivo(plato.getActivo());
        exist.setTiempoPreparacionMinutos(plato.getTiempoPreparacionMinutos());
        return platoRepository.save(exist);
    }

    @Override
    public void delete(Long id) {
        platoRepository.deleteById(id);
    }

    @Override
    public List<Plato> findAll() {
        return platoRepository.findAll();
    }

    @Override
    public Plato findById(Long id) {
        return platoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Plato> findByTipo(TipoPlato tipo) {
        return platoRepository.findByTipo(tipo);
    }

    @Override
    public List<Plato> findActivos() {
        return platoRepository.findByActivoTrue();
    }

    @Override
    public Plato findByNombre(String nombre) {
        return platoRepository.findByNombre(nombre);
    }
}
