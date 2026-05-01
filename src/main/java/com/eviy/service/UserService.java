package com.eviy.service;

import com.eviy.entity.Friendship;
import com.eviy.entity.FriendshipStatus;
import com.eviy.entity.User;
import com.eviy.exception.ResourceNotFoundException;
import com.eviy.repository.FriendshipRepository;
import com.eviy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı: " + email));
    }

    public List<User> getFriends(User user) {
        List<Friendship> friendships = friendshipRepository
                .findAllByUserAndStatus(user, FriendshipStatus.ACCEPTED);

        return friendships.stream()
                .map(f -> f.getRequester().getId().equals(user.getId())
                        ? f.getReceiver()
                        : f.getRequester())
                .collect(Collectors.toList());
    }
}