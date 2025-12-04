package com.tecsup.pagos_linea.dto;

import com.tecsup.pagos_linea.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private Long id;
    private Long pedidoId;
    private String stripePaymentIntentId;
    private String clientSecret;
    private BigDecimal amount;
    private String currency;
    private Payment.PaymentStatus status;
    private String paymentMethod;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private String message;

    public static PaymentResponse fromPayment(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.setId(payment.getId());
        response.setPedidoId(payment.getPedidoId());
        response.setStripePaymentIntentId(payment.getStripePaymentIntentId());
        response.setClientSecret(payment.getStripeClientSecret());
        response.setAmount(payment.getAmount());
        response.setCurrency(payment.getCurrency());
        response.setStatus(payment.getStatus());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setCreatedAt(payment.getCreatedAt());
        response.setCompletedAt(payment.getCompletedAt());
        return response;
    }
}
