package com.tecsup.evaluacion03.service.impl;

import com.tecsup.evaluacion03.model.DetallePedido;
import com.tecsup.evaluacion03.repository.DetallePedidoRepository;
import com.tecsup.evaluacion03.service.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;

    @Autowired
    public DetallePedidoServiceImpl(DetallePedidoRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    @Override
    public DetallePedido save(DetallePedido detalle) {
        return detallePedidoRepository.save(detalle);
    }

    @Override
    public DetallePedido update(Long id, DetallePedido detalle) {
        DetallePedido exist = detallePedidoRepository.findById(id).orElseThrow();
        exist.setPedido(detalle.getPedido());
        exist.setPlato(detalle.getPlato());
        exist.setCantidad(detalle.getCantidad());
        exist.setPrecioUnitario(detalle.getPrecioUnitario());
        exist.setSubtotal(detalle.getSubtotal());
        return null;
    }

    @Override
    public void delete(Long id) {
        detallePedidoRepository.deleteById(id);
    }

    @Override
    public List<DetallePedido> findAll() {
        return detallePedidoRepository.findAll();
    }

    @Override
    public DetallePedido findById(Long id) {
        return detallePedidoRepository.findById(id).orElse(null);
    }

    @Override
    public List<DetallePedido> findByPedidoId(Long pedidoId) {
        return detallePedidoRepository.findByPedidoId(pedidoId);
    }
}

