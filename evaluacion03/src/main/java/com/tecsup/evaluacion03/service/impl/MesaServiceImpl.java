package com.tecsup.evaluacion03.service.impl;

import com.tecsup.evaluacion03.enums.EstadoMesa;
import com.tecsup.evaluacion03.model.Mesa;
import com.tecsup.evaluacion03.repository.MesaRepository;
import com.tecsup.evaluacion03.service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesaServiceImpl implements MesaService {

    private final MesaRepository mesaRepository;

    @Autowired
    public MesaServiceImpl(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    @Override
    public Mesa save(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    @Override
    public Mesa update(Long id, Mesa mesa) {
        Mesa exist = mesaRepository.findById(id).orElseThrow();
        exist.setNumero(mesa.getNumero());
        exist.setCapacidad(mesa.getCapacidad());
        exist.setEstado(mesa.getEstado());
        exist.setUbicacion(mesa.getUbicacion());

        return mesaRepository.save(exist);
    }

    @Override
    public void delete(Long id) {
        mesaRepository.deleteById(id);
    }

    @Override
    public List<Mesa> findAll() {
        return mesaRepository.findAll();
    }

    @Override
    public Mesa findById(Long id) {
        return mesaRepository.findById(id).orElse(null);
    }

    @Override
    public Mesa findByNumero(String numero) {
        return mesaRepository.findByNumero(numero).orElse(null);
    }

    @Override
    public List<Mesa> findByEstado(EstadoMesa estado) {
        return mesaRepository.findByEstado(estado);
    }
}
