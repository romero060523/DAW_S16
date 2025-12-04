package com.tecsup.evaluacion03.service;

import com.tecsup.evaluacion03.model.Rol;

import java.util.List;

public interface RolService {
    Rol save(Rol rol);
    Rol update(Long id, Rol rol);
    void delete(Long id);
    List<Rol> findAll();
    Rol findById(Long id);
    Rol findByNombre(String nombre);
}

