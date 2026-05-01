package com.eviy.service;

import com.eviy.entity.DebtType;
import com.eviy.entity.Transaction;
import com.eviy.entity.User;
import com.eviy.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Transaction create(User payer, User receiver,
                              BigDecimal amount, DebtType type, String note) {
        Transaction transaction = new Transaction();
        transaction.setPayer(payer);
        transaction.setReceiver(receiver);
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setNote(note);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getBetweenUsers(User user1, User user2) {
        return transactionRepository.findBetweenUsers(user1, user2);
    }

    public BigDecimal getTotalAmount(User payer, User receiver, DebtType type) {
        return transactionRepository
                .sumAmountByPayerAndReceiverAndType(payer, receiver, type);
    }
}