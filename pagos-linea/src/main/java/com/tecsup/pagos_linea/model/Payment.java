package com.tecsup.pagos_linea.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pedidoId;

    @Column(length = 100)
    private String stripePaymentIntentId;

    @Column(length = 255)
    private String stripeClientSecret;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(length = 10)
    private String currency = "PEN";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(length = 50)
    private String paymentMethod;

    @Column(length = 100)
    private String customerEmail;

    @Column(length = 500)
    private String description;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    public enum PaymentStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED,
        CANCELLED,
        REFUNDED
    }
}
