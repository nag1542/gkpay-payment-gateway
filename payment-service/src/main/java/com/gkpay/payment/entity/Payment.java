package com.gkpay.payment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @Column(name = "payment_id", nullable = false, updatable = false, unique = true)
    private String paymentId;

    @Column(name = "merchant_order_id", nullable = false)
    private String merchantOrderId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false, length = 10)
    private String currency;

    @Column(name = "payment_method", nullable = false, length = 10)
    private String paymentMethod;

    @Column(name = "payment_token", nullable = false)
    private String paymentToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
