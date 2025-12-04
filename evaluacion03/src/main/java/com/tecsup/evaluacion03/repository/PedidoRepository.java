package com.tecsup.evaluacion03.repository;

import com.tecsup.evaluacion03.model.Pedido;
import com.tecsup.evaluacion03.enums.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    /**
     * Busca todos los pedidos por estado
     */
    List<Pedido> findByEstado(EstadoPedido estado);

    /**
     * Busca pedidos pendientes o en preparación
     */
    List<Pedido> findByEstadoOrEstado(EstadoPedido estado1, EstadoPedido estado2);

    /**
     * Busca pedidos de un cliente específico
     */
    List<Pedido> findByClienteId(Long clienteId);

    /**
     * Busca pedidos de una mesa específica
     */
    List<Pedido> findByMesaId(Long mesaId);

    /**
     * Busca pedidos dentro de un rango de fechas
     */
    List<Pedido> findByFechaHoraBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    /**
     * Busca pedidos no cerrados (pendientes, en preparación, servidos)
     */
    List<Pedido> findByEstadoNot(EstadoPedido estado);
}
