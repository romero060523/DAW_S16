package com.tecsup.pagos_linea.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Long pedidoId;
    private BigDecimal amount;
    private String currency;
    private String customerEmail;
    private String description;
}
