package com.eviy.repository;

import com.eviy.entity.DebtType;
import com.eviy.entity.Transaction;
import com.eviy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE " +
            "(t.payer = :user1 AND t.receiver = :user2) OR " +
            "(t.payer = :user2 AND t.receiver = :user1) " +
            "ORDER BY t.createdAt DESC")
    List<Transaction> findBetweenUsers(@Param("user1") User user1,
                                       @Param("user2") User user2);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE " +
            "t.payer = :payer AND t.receiver = :receiver AND t.type = :type")
    BigDecimal sumAmountByPayerAndReceiverAndType(@Param("payer") User payer,
                                                  @Param("receiver") User receiver,
                                                  @Param("type") DebtType type);
}