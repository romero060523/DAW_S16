package com.tecsup.evaluacion03.service;

import com.tecsup.evaluacion03.model.Bitacora;

import java.util.List;

public interface BitacoraService {
    Bitacora save(Bitacora bitacora);
    void delete(Long id);
    List<Bitacora> findAll();
    Bitacora findById(Long id);
    List<Bitacora> findByUsuario(String usuario);
    List<Bitacora> findByAccion(String accion);
}

