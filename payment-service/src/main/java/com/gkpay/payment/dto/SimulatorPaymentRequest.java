package com.gkpay.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimulatorPaymentRequest {
    private String paymentId;
    private Double amount;
    private String paymentMethod;
}
