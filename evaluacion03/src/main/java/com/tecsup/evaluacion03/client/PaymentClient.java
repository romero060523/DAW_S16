package com.tecsup.evaluacion03.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * Cliente Feign para comunicarse con el microservicio de pagos
 */
@FeignClient(name = "pagos-linea", url = "${pagos.service.url:http://localhost:8082}")
public interface PaymentClient {

    @PostMapping("/api/pagos/create")
    ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest request);

    @PostMapping("/api/pagos/confirm")
    ResponseEntity<PaymentResponse> confirmPayment(@RequestParam("paymentIntentId") String paymentIntentId);

    @GetMapping("/api/pagos/pedido/{pedidoId}")
    ResponseEntity<PaymentResponse> getPaymentByPedido(@PathVariable("pedidoId") Long pedidoId);

    @GetMapping("/api/pagos/{paymentIntentId}")
    ResponseEntity<PaymentResponse> getPaymentByIntentId(@PathVariable("paymentIntentId") String paymentIntentId);

    // DTOs internos para evitar dependencias circulares
    record PaymentRequest(
            Long pedidoId,
            BigDecimal amount,
            String currency,
            String customerEmail,
            String description
    ) {}

    record PaymentResponse(
            Long id,
            Long pedidoId,
            String stripePaymentIntentId,
            String clientSecret,
            BigDecimal amount,
            String currency,
            String status,
            String paymentMethod,
            String createdAt,
            String completedAt,
            String message
    ) {}
}
