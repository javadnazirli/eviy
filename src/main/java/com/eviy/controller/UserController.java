package com.eviy.controller;

import com.eviy.entity.User;
import com.eviy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/friends")
    public ResponseEntity<List<User>> getFriends(@RequestParam String email) {
        User user = userService.getByEmail(email);
        List<User> friends = userService.getFriends(user);
        return ResponseEntity.ok(friends);
    }
}