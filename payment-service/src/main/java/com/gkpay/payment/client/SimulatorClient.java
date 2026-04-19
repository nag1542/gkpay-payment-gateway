package com.gkpay.payment.client;

import com.gkpay.payment.dto.SimulatorPaymentRequest;
import com.gkpay.payment.dto.SimulatorPaymentResponse;
import com.gkpay.payment.exception.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class SimulatorClient {

    private final RestTemplate restTemplate;

    @Value("${simulator.base-url}")
    private String simulatorBaseUrl;

    public SimulatorPaymentResponse simulatePayment(SimulatorPaymentRequest request) {
        try {
            ResponseEntity<SimulatorPaymentResponse> response = restTemplate.postForEntity(
                    simulatorBaseUrl + "/simulate/payment",
                    request,
                    SimulatorPaymentResponse.class
            );

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new ExternalServiceException("Simulator service returned invalid response");
            }
            return response.getBody();
        } catch (RestClientException ex) {
            throw new ExternalServiceException("Failed to call simulator service: " + ex.getMessage());
        }
    }
}
