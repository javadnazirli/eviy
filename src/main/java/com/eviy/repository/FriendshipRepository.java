package com.eviy.repository;

import com.eviy.entity.Friendship;
import com.eviy.entity.FriendshipStatus;
import com.eviy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query("SELECT f FROM Friendship f WHERE " +
            "(f.requester = :user OR f.receiver = :user) " +
            "AND f.status = :status")
    List<Friendship> findAllByUserAndStatus(@Param("user") User user,
                                            @Param("status") FriendshipStatus status);

    @Query("SELECT f FROM Friendship f WHERE " +
            "(f.requester = :user1 AND f.receiver = :user2) OR " +
            "(f.requester = :user2 AND f.receiver = :user1)")
    Optional<Friendship> findBetweenUsers(@Param("user1") User user1,
                                          @Param("user2") User user2);
}