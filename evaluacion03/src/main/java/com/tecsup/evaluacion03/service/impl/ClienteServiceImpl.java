package com.tecsup.evaluacion03.service.impl;

import com.tecsup.evaluacion03.model.Cliente;
import com.tecsup.evaluacion03.repository.ClienteRepository;
import com.tecsup.evaluacion03.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente update(Long id, Cliente cliente) {
        Cliente exist = clienteRepository.findById(id).orElseThrow();
        exist.setNombres(cliente.getNombres());
        exist.setApellidos(cliente.getApellidos());
        exist.setCorreo(cliente.getCorreo());
        exist.setTelefono(cliente.getTelefono());
        exist.setActivo(cliente.getActivo());

        return clienteRepository.save(exist);
    }

    @Override
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente findById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cliente> findActivos() {
        return clienteRepository.findByActivoTrue();
    }

    @Override
    public Cliente findByDni(String dni) {
        return clienteRepository.findByDni(dni).orElse(null);
    }

    @Override
    public Cliente findByCorreo(String correo) {
        return clienteRepository.findByCorreo(correo).orElse(null);
    }

}
