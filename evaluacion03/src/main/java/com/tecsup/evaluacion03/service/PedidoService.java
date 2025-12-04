package com.tecsup.evaluacion03.service;

import com.tecsup.evaluacion03.model.Pedido;

import java.util.List;

public interface PedidoService {
    Pedido save(Pedido pedido);
    Pedido update(Long id, Pedido pedido);
    void delete(Long id);
    List<Pedido> findAll();
    Pedido findById(Long id);
    List<Pedido> findByEstado(com.tecsup.evaluacion03.enums.EstadoPedido estado);
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByMesaId(Long mesaId);
}

