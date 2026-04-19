package com.gkpay.simulator.controller;

import com.gkpay.simulator.dto.SimulatorRequest;
import com.gkpay.simulator.dto.SimulatorResponse;
import com.gkpay.simulator.service.SimulatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simulate")
@RequiredArgsConstructor
public class SimulatorController {

    private final SimulatorService simulatorService;

    @PostMapping("/payment")
    public ResponseEntity<SimulatorResponse> simulatePayment(@Valid @RequestBody SimulatorRequest request) {
        return ResponseEntity.ok(simulatorService.processPayment(request));
    }
}
