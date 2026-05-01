package com.eviy.service;

import com.eviy.entity.DebtType;
import com.eviy.entity.Settlement;
import com.eviy.entity.User;
import com.eviy.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository settlementRepository;

    public Settlement pay(User payer, User receiver,
                          BigDecimal amount, DebtType type) {
        Settlement settlement = new Settlement();
        settlement.setPayer(payer);
        settlement.setReceiver(receiver);
        settlement.setAmount(amount);
        settlement.setType(type);
        return settlementRepository.save(settlement);
    }

    public BigDecimal getTotalPaid(User payer, User receiver, DebtType type) {
        return settlementRepository
                .sumAmountByPayerAndReceiverAndType(payer, receiver, type);
    }
}