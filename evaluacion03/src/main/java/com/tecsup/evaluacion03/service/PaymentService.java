package com.tecsup.evaluacion03.service;

import com.tecsup.evaluacion03.client.PaymentClient;
import com.tecsup.evaluacion03.model.Pedido;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentClient paymentClient;

    /**
     * Crea un PaymentIntent en Stripe para un pedido
     */
    public PaymentClient.PaymentResponse createPaymentIntent(Pedido pedido) {
        try {
            // Calcular total con IGV (18%)
            BigDecimal totalConIgv = pedido.getTotal().multiply(BigDecimal.valueOf(1.18));

            PaymentClient.PaymentRequest request = new PaymentClient.PaymentRequest(
                    pedido.getId(),
                    totalConIgv,
                    "PEN",
                    pedido.getCliente() != null ? pedido.getCliente().getCorreo() : null,
                    "Pedido #" + pedido.getId() + " - Restaurante TECSUP"
            );

            ResponseEntity<PaymentClient.PaymentResponse> response = paymentClient.createPayment(request);
            log.info("PaymentIntent creado para pedido {}: {}", pedido.getId(), 
                    response.getBody() != null ? response.getBody().stripePaymentIntentId() : "null");
            return response.getBody();

        } catch (Exception e) {
            log.error("Error al crear PaymentIntent para pedido {}: {}", pedido.getId(), e.getMessage());
            return new PaymentClient.PaymentResponse(
                    null, pedido.getId(), null, null, null, null,
                    "FAILED", null, null, null, "Error: " + e.getMessage()
            );
        }
    }

    /**
     * Confirma un pago después del checkout
     */
    public PaymentClient.PaymentResponse confirmPayment(String paymentIntentId) {
        try {
            ResponseEntity<PaymentClient.PaymentResponse> response = paymentClient.confirmPayment(paymentIntentId);
            log.info("Pago confirmado: {}", paymentIntentId);
            return response.getBody();

        } catch (Exception e) {
            log.error("Error al confirmar pago {}: {}", paymentIntentId, e.getMessage());
            return new PaymentClient.PaymentResponse(
                    null, null, paymentIntentId, null, null, null,
                    "FAILED", null, null, null, "Error: " + e.getMessage()
            );
        }
    }

    /**
     * Obtiene el estado de un pago por ID de pedido
     */
    public PaymentClient.PaymentResponse getPaymentByPedido(Long pedidoId) {
        try {
            ResponseEntity<PaymentClient.PaymentResponse> response = paymentClient.getPaymentByPedido(pedidoId);
            return response.getBody();
        } catch (Exception e) {
            log.error("Error al obtener pago para pedido {}: {}", pedidoId, e.getMessage());
            return null;
        }
    }
}
