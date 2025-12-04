package com.tecsup.evaluacion03.service.impl;

import com.tecsup.evaluacion03.model.Rol;
import com.tecsup.evaluacion03.repository.RolRepository;
import com.tecsup.evaluacion03.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    @Autowired
    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public Rol update(Long id, Rol rol) {
        Rol exist = rolRepository.findById(id).orElseThrow();
        exist.setNombre(rol.getNombre());
        exist.setDescripcion(rol.getDescripcion());
        exist.setUsuarios(rol.getUsuarios());

        return rolRepository.save(exist);
    }

    @Override
    public void delete(Long id) {
        rolRepository.deleteById(id);
    }

    @Override
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    @Override
    public Rol findById(Long id) {
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    public Rol findByNombre(String nombre) {
        return rolRepository.findByNombre(nombre).orElse(null);
    }
}
