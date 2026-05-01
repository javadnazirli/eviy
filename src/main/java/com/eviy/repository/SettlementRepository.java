package com.eviy.repository;

import com.eviy.entity.DebtType;
import com.eviy.entity.Settlement;
import com.eviy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {

    @Query("SELECT COALESCE(SUM(s.amount), 0) FROM Settlement s WHERE " +
            "s.payer = :payer AND s.receiver = :receiver AND s.type = :type")
    BigDecimal sumAmountByPayerAndReceiverAndType(@Param("payer") User payer,
                                                  @Param("receiver") User receiver,
                                                  @Param("type") DebtType type);
}