package com.gkpay.payment.service;

import com.gkpay.payment.client.SimulatorClient;
import com.gkpay.payment.dto.CreatePaymentRequest;
import com.gkpay.payment.dto.PaymentResponse;
import com.gkpay.payment.dto.SimulatorPaymentRequest;
import com.gkpay.payment.dto.SimulatorPaymentResponse;
import com.gkpay.payment.entity.Payment;
import com.gkpay.payment.entity.PaymentStatus;
import com.gkpay.payment.exception.PaymentNotFoundException;
import com.gkpay.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final SimulatorClient simulatorClient;

    @Transactional
    public PaymentResponse createPayment(CreatePaymentRequest request) {
        validateInput(request);

        Payment payment = Payment.builder()
                .paymentId(generateUniquePaymentId())
                .merchantOrderId(request.getMerchantOrderId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .paymentMethod(request.getPaymentMethod().toUpperCase(Locale.ROOT))
                .paymentToken(request.getPaymentToken())
                .status(PaymentStatus.CREATED)
                .build();

        Payment persisted = paymentRepository.save(payment);

        SimulatorPaymentResponse simulatorResponse = simulatorClient.simulatePayment(
                SimulatorPaymentRequest.builder()
                        .paymentId(persisted.getPaymentId())
                        .amount(persisted.getAmount())
                        .paymentMethod(persisted.getPaymentMethod())
                        .build()
        );

        PaymentStatus finalStatus = "SUCCESS".equalsIgnoreCase(simulatorResponse.getStatus())
                ? PaymentStatus.SUCCESS
                : PaymentStatus.FAILED;

        persisted.setStatus(finalStatus);
        Payment updated = paymentRepository.save(persisted);

        return mapToResponse(updated);
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPayment(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
        return mapToResponse(payment);
    }

    private String generateUniquePaymentId() {
        String paymentId;
        do {
            paymentId = "PAY_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase(Locale.ROOT);
        } while (paymentRepository.existsById(paymentId));

        return paymentId;
    }

    private void validateInput(CreatePaymentRequest request) {
        if (request.getAmount() == null || request.getAmount() <= 0) {
            throw new IllegalArgumentException("amount must be greater than 0");
        }
        if (request.getMerchantOrderId() == null || request.getMerchantOrderId().isBlank()) {
            throw new IllegalArgumentException("merchantOrderId must not be null or blank");
        }
        if (request.getPaymentToken() == null || request.getPaymentToken().isBlank()) {
            throw new IllegalArgumentException("paymentToken must not be null or blank");
        }
    }

    private PaymentResponse mapToResponse(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .merchantOrderId(payment.getMerchantOrderId())
                .amount(payment.getAmount())
                .status(payment.getStatus().name())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}
