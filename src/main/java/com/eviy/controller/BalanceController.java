package com.eviy.controller;

import com.eviy.entity.DebtType;
import com.eviy.entity.User;
import com.eviy.service.BalanceService;
import com.eviy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<BigDecimal> getBalance(
            @RequestParam String email1,
            @RequestParam String email2,
            @RequestParam DebtType type) {
        User user1 = userService.getByEmail(email1);
        User user2 = userService.getByEmail(email2);
        BigDecimal balance = balanceService.getNetBalance(user1, user2, type);
        return ResponseEntity.ok(balance);
    }

}