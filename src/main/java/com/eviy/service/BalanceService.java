package com.eviy.service;

import com.eviy.entity.DebtType;
import com.eviy.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final TransactionService transactionService;
    private final SettlementService settlementService;

    public BigDecimal getNetBalance(User user1, User user2, DebtType type) {

        BigDecimal user1Owes = transactionService
                .getTotalAmount(user1, user2, type)
                .subtract(settlementService.getTotalPaid(user1, user2, type));

        BigDecimal user2Owes = transactionService
                .getTotalAmount(user2, user1, type)
                .subtract(settlementService.getTotalPaid(user2, user1, type));

        return user1Owes.subtract(user2Owes);
    }
}