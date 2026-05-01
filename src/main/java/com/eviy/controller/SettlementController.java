package com.eviy.controller;

import com.eviy.entity.DebtType;
import com.eviy.entity.Settlement;
import com.eviy.entity.User;
import com.eviy.service.SettlementService;
import com.eviy.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;
    private final UserService userService;

    @PostMapping("/pay")
    public ResponseEntity<Settlement> pay(@RequestBody PayRequest request) {
        User payer = userService.getByEmail(request.payerEmail());
        User receiver = userService.getByEmail(request.receiverEmail());
        Settlement settlement = settlementService.pay(
                payer, receiver, request.amount(), request.type()
        );
        return ResponseEntity.ok(settlement);
    }
    public record PayRequest(
            @JsonProperty("payerEmail") String payerEmail,
            @JsonProperty("receiverEmail") String receiverEmail,
            @JsonProperty("amount") BigDecimal amount,
            @JsonProperty("type") DebtType type
    ) {}
}