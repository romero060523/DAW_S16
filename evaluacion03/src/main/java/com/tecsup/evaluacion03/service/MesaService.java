package com.tecsup.evaluacion03.service;

import com.tecsup.evaluacion03.model.Mesa;

import java.util.List;

public interface MesaService {
    Mesa save(Mesa mesa);
    Mesa update(Long id, Mesa mesa);
    void delete(Long id);
    List<Mesa> findAll();
    Mesa findById(Long id);
    Mesa findByNumero(String numero);
    List<Mesa> findByEstado(com.tecsup.evaluacion03.enums.EstadoMesa estado);
}

