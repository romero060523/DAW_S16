package com.tecsup.evaluacion03.service.impl;

import com.tecsup.evaluacion03.model.Bitacora;
import com.tecsup.evaluacion03.repository.BitacoraRepository;
import com.tecsup.evaluacion03.service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BitacoraServiceImpl implements BitacoraService {

    private final BitacoraRepository bitacoraRepository;

    @Autowired
    public BitacoraServiceImpl(BitacoraRepository bitacoraRepository) {
        this.bitacoraRepository = bitacoraRepository;
    }

    @Override
    public Bitacora save(Bitacora bitacora) {
        return bitacoraRepository.save(bitacora);
    }

    @Override
    public void delete(Long id) {
        bitacoraRepository.deleteById(id);
    }

    @Override
    public List<Bitacora> findAll() {
        return bitacoraRepository.findAll();
    }

    @Override
    public Bitacora findById(Long id) {
        return bitacoraRepository.findById(id).orElse(null);
    }

    @Override
    public List<Bitacora> findByUsuario(String usuario) {
        return bitacoraRepository.findByUsuario(usuario);
    }

    @Override
    public List<Bitacora> findByAccion(String accion) {
        return bitacoraRepository.findByAccion(accion);
    }
}
