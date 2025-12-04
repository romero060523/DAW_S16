package com.tecsup.pagos_linea.repository;

import com.tecsup.pagos_linea.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPedidoId(Long pedidoId);

    Optional<Payment> findByStripePaymentIntentId(String stripePaymentIntentId);

    List<Payment> findByStatus(Payment.PaymentStatus status);
}
