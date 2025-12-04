package com.tecsup.evaluacion03.repository;

import com.tecsup.evaluacion03.model.Pedido;
import com.tecsup.evaluacion03.enums.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    /**
     * Busca un pedido por ID cargando eagerly sus relaciones (cliente, mesa, detalles con plato)
     * Esto evita LazyInitializationException al renderizar la vista de factura
     */
    @Query("SELECT p FROM Pedido p " +
           "LEFT JOIN FETCH p.cliente " +
           "LEFT JOIN FETCH p.mesa " +
           "LEFT JOIN FETCH p.detalles d " +
           "LEFT JOIN FETCH d.plato " +
           "WHERE p.id = :id")
    Optional<Pedido> findByIdWithRelations(@Param("id") Long id);
}
