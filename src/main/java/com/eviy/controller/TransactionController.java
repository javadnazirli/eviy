package com.eviy.controller;

import com.eviy.entity.DebtType;
import com.eviy.entity.Transaction;
import com.eviy.entity.User;
import com.eviy.service.TransactionService;
import com.eviy.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody TransactionRequest request) {
        User payer = userService.getByEmail(request.payerEmail());
        User receiver = userService.getByEmail(request.receiverEmail());
        Transaction transaction = transactionService.create(
                payer, receiver, request.amount(), request.type(), request.note()
        );
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/between")
    public ResponseEntity<List<Transaction>> getBetween(
            @RequestParam String email1,
            @RequestParam String email2) {
        User user1 = userService.getByEmail(email1);
        User user2 = userService.getByEmail(email2);
        return ResponseEntity.ok(transactionService.getBetweenUsers(user1, user2));
    }

    public record TransactionRequest(
            @JsonProperty("payerEmail")String payerEmail,
            @JsonProperty("receiverEmail")String receiverEmail,
            @JsonProperty("amount")BigDecimal amount,
            @JsonProperty("type")DebtType type,
            @JsonProperty("note")String note
    ) {}
}