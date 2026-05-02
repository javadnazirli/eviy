package com.eviy.service;

import com.eviy.entity.Friendship;
import com.eviy.entity.FriendshipStatus;
import com.eviy.entity.User;
import com.eviy.repository.FriendshipRepository;
import com.eviy.repository.UserRepository;
import com.eviy.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public User register(String name, String email, String password) {

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Bu email zaten kayıtlı");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        List<User> allUsers = userRepository.findAll();
        for (User existing : allUsers) {
            if (!existing.getId().equals(user.getId())) {
                Friendship friendship = new Friendship();
                friendship.setRequester(user);
                friendship.setReceiver(existing);
                friendship.setStatus(FriendshipStatus.ACCEPTED);
                friendshipRepository.save(friendship);
            }
        }

        return user;
    }

    public String login(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        User user = userRepository.findByEmail(email).orElseThrow();
        return jwtUtil.generateToken(user.getEmail());
    }
}