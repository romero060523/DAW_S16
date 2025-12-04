package com.tecsup.evaluacion03.service.impl;

import com.tecsup.evaluacion03.enums.EstadoPedido;
import com.tecsup.evaluacion03.model.Pedido;
import com.tecsup.evaluacion03.repository.PedidoRepository;
import com.tecsup.evaluacion03.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public Pedido save(Pedido pedido) {
        pedido.calcularTotal();
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido update(Long id, Pedido pedido) {
        Pedido exist = pedidoRepository.findById(id).orElseThrow();
        exist.setEstado(pedido.getEstado());
        exist.setMesa(pedido.getMesa());
        exist.setCliente(pedido.getCliente());
        exist.setDetalles(pedido.getDetalles());
        exist.setTipoServicio(pedido.getTipoServicio());
        exist.setObservaciones(pedido.getObservaciones());
        exist.calcularTotal();

        return pedidoRepository.save(exist);
    }

    @Override
    public void delete(Long id) {
        pedidoRepository.deleteById(id);
    }

    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido findById(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Pedido> findByEstado(EstadoPedido estado) {
        return pedidoRepository.findByEstado(estado);
    }

    @Override
    public List<Pedido> findByClienteId(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    @Override
    public List<Pedido> findByMesaId(Long mesaId) {
        return pedidoRepository.findByMesaId(mesaId);
    }
}
