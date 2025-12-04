package com.tecsup.pagos_linea.controller;

import com.tecsup.pagos_linea.dto.PaymentRequest;
import com.tecsup.pagos_linea.dto.PaymentResponse;
import com.tecsup.pagos_linea.service.StripeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PaymentController {

    private final StripeService stripeService;

    /**
     * Crear un nuevo PaymentIntent para un pedido
     * POST /api/pagos/create
     */
    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest request) {
        log.info("Creando pago para pedido: {}", request.getPedidoId());
        PaymentResponse response = stripeService.createPaymentIntent(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Confirmar un pago después del checkout de Stripe
     * POST /api/pagos/confirm
     */
    @PostMapping("/confirm")
    public ResponseEntity<PaymentResponse> confirmPayment(@RequestParam String paymentIntentId) {
        log.info("Confirmando pago: {}", paymentIntentId);
        PaymentResponse response = stripeService.confirmPayment(paymentIntentId);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener estado de pago por ID de pedido
     * GET /api/pagos/pedido/{pedidoId}
     */
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<PaymentResponse> getPaymentByPedido(@PathVariable Long pedidoId) {
        log.info("Consultando pago para pedido: {}", pedidoId);
        PaymentResponse response = stripeService.getPaymentByPedidoId(pedidoId);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener estado de pago por PaymentIntent ID
     * GET /api/pagos/{paymentIntentId}
     */
    @GetMapping("/{paymentIntentId}")
    public ResponseEntity<PaymentResponse> getPaymentByIntentId(@PathVariable String paymentIntentId) {
        log.info("Consultando pago: {}", paymentIntentId);
        PaymentResponse response = stripeService.getPaymentByIntentId(paymentIntentId);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint de health check
     * GET /api/pagos/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Servicio de pagos activo");
    }
}
