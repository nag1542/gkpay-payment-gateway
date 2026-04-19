package com.gkpay.simulator.service;

import com.gkpay.simulator.dto.SimulatorRequest;
import com.gkpay.simulator.dto.SimulatorResponse;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class SimulatorService {

    public SimulatorResponse processPayment(SimulatorRequest request) {
        int chance = ThreadLocalRandom.current().nextInt(100);
        String status = chance < 70 ? "SUCCESS" : "FAILED";

        return SimulatorResponse.builder()
                .status(status)
                .build();
    }
}
