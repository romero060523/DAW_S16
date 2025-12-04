package com.tecsup.evaluacion03.repository;

import com.tecsup.evaluacion03.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    /**
     * Busca todos los detalles de un pedido específico
     */
    List<DetallePedido> findByPedidoId(Long pedidoId);

    /**
     * Busca todos los detalles de un plato
     */
    List<DetallePedido> findByPlatoId(Long platoId);

    /**
     * Cuenta cuántos detalles tiene un pedido
     */
    long countByPedidoId(Long pedidoId);
}
