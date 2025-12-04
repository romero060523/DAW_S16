package com.tecsup.evaluacion03.service;

import com.tecsup.evaluacion03.model.Cliente;

import java.util.List;

public interface ClienteService {
    Cliente save(Cliente cliente);
    Cliente update(Long id, Cliente cliente);
    void delete(Long id);
    List<Cliente> findAll();
    Cliente findById(Long id);
    List<Cliente> findActivos();
    Cliente findByDni(String dni);
    Cliente findByCorreo(String correo);
}
