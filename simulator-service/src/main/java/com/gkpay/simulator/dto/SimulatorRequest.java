package com.gkpay.simulator.dto;

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
public class SimulatorRequest {

    @NotBlank(message = "paymentId is required")
    private String paymentId;

    @NotNull(message = "amount is required")
    @Positive(message = "amount must be greater than 0")
    private Double amount;

    @NotBlank(message = "paymentMethod is required")
    private String paymentMethod;
}
