package com.gkpay.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String paymentId;
    private String merchantOrderId;
    private Double amount;
    private String status;
    private LocalDateTime createdAt;
}
