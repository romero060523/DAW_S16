package com.tecsup.evaluacion03.service;

import com.tecsup.evaluacion03.model.DetallePedido;

import java.util.List;

public interface DetallePedidoService {
    DetallePedido save(DetallePedido detalle);
    DetallePedido update(Long id, DetallePedido detalle);
    void delete(Long id);
    List<DetallePedido> findAll();
    DetallePedido findById(Long id);
    List<DetallePedido> findByPedidoId(Long pedidoId);
}

