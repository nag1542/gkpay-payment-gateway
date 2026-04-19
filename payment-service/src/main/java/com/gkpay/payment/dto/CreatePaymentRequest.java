package com.gkpay.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {

    @NotNull(message = "amount is required")
    @Positive(message = "amount must be greater than 0")
    private Double amount;

    @NotBlank(message = "currency is required")
    private String currency;

    @NotBlank(message = "paymentMethod is required")
    private String paymentMethod;

    @NotBlank(message = "paymentToken is required")
    private String paymentToken;

    @NotBlank(message = "merchantOrderId is required")
    private String merchantOrderId;
}
