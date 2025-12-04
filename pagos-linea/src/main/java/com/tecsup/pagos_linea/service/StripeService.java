package com.tecsup.pagos_linea.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.tecsup.pagos_linea.dto.PaymentRequest;
import com.tecsup.pagos_linea.dto.PaymentResponse;
import com.tecsup.pagos_linea.model.Payment;
import com.tecsup.pagos_linea.repository.PaymentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripeService {

    private final PaymentRepository paymentRepository;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
        log.info("Stripe API key configurada correctamente");
    }

    /**
     * Crea un PaymentIntent en Stripe y guarda el registro en la BD
     */
    @Transactional
    public PaymentResponse createPaymentIntent(PaymentRequest request) {
        try {
            // Convertir monto a centavos (Stripe trabaja con centavos)
            long amountInCents = request.getAmount()
                    .multiply(BigDecimal.valueOf(100))
                    .longValue();

            // Crear PaymentIntent en Stripe
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amountInCents)
                    .setCurrency(request.getCurrency() != null ? request.getCurrency().toLowerCase() : "pen")
                    .setDescription(request.getDescription())
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build()
                    )
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);
            log.info("PaymentIntent creado: {}", paymentIntent.getId());

            // Guardar en base de datos
            Payment payment = new Payment();
            payment.setPedidoId(request.getPedidoId());
            payment.setStripePaymentIntentId(paymentIntent.getId());
            payment.setStripeClientSecret(paymentIntent.getClientSecret());
            payment.setAmount(request.getAmount());
            payment.setCurrency(request.getCurrency() != null ? request.getCurrency() : "PEN");
            payment.setStatus(Payment.PaymentStatus.PENDING);
            payment.setCustomerEmail(request.getCustomerEmail());
            payment.setDescription(request.getDescription());

            Payment savedPayment = paymentRepository.save(payment);

            PaymentResponse response = PaymentResponse.fromPayment(savedPayment);
            response.setMessage("PaymentIntent creado exitosamente");
            return response;

        } catch (StripeException e) {
            log.error("Error al crear PaymentIntent: {}", e.getMessage());
            PaymentResponse errorResponse = new PaymentResponse();
            errorResponse.setMessage("Error: " + e.getMessage());
            errorResponse.setStatus(Payment.PaymentStatus.FAILED);
            return errorResponse;
        }
    }

    /**
     * Confirma el pago después de que el cliente completa el checkout
     */
    @Transactional
    public PaymentResponse confirmPayment(String paymentIntentId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

            Payment payment = paymentRepository.findByStripePaymentIntentId(paymentIntentId)
                    .orElseThrow(() -> new RuntimeException("Pago no encontrado: " + paymentIntentId));

            if ("succeeded".equals(paymentIntent.getStatus())) {
                payment.setStatus(Payment.PaymentStatus.COMPLETED);
                payment.setCompletedAt(LocalDateTime.now());
                payment.setPaymentMethod(paymentIntent.getPaymentMethod());
                log.info("Pago completado exitosamente: {}", paymentIntentId);
            } else if ("processing".equals(paymentIntent.getStatus())) {
                payment.setStatus(Payment.PaymentStatus.PROCESSING);
            } else {
                payment.setStatus(Payment.PaymentStatus.FAILED);
            }

            Payment updatedPayment = paymentRepository.save(payment);

            PaymentResponse response = PaymentResponse.fromPayment(updatedPayment);
            response.setMessage("Estado del pago: " + paymentIntent.getStatus());
            return response;

        } catch (StripeException e) {
            log.error("Error al confirmar pago: {}", e.getMessage());
            PaymentResponse errorResponse = new PaymentResponse();
            errorResponse.setMessage("Error: " + e.getMessage());
            errorResponse.setStatus(Payment.PaymentStatus.FAILED);
            return errorResponse;
        }
    }

    /**
     * Obtiene el estado actual de un pago por ID de pedido
     */
    public PaymentResponse getPaymentByPedidoId(Long pedidoId) {
        Payment payment = paymentRepository.findByPedidoId(pedidoId)
                .orElse(null);

        if (payment == null) {
            PaymentResponse response = new PaymentResponse();
            response.setMessage("No se encontró pago para el pedido: " + pedidoId);
            return response;
        }

        return PaymentResponse.fromPayment(payment);
    }

    /**
     * Obtiene el estado actual de un pago por PaymentIntent ID
     */
    public PaymentResponse getPaymentByIntentId(String paymentIntentId) {
        Payment payment = paymentRepository.findByStripePaymentIntentId(paymentIntentId)
                .orElse(null);

        if (payment == null) {
            PaymentResponse response = new PaymentResponse();
            response.setMessage("No se encontró pago: " + paymentIntentId);
            return response;
        }

        return PaymentResponse.fromPayment(payment);
    }
}
