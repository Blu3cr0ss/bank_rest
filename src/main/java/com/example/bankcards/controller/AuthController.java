package com.example.bankcards.controller;

import com.example.bankcards.service.JwtService;
import com.example.bankcards.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserService userService;

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        if (!userService.tryAuthenticate(username, password))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        long id = userService.getIdByUsername(username);

        return ResponseEntity.ok(jwtService.generate(id, username, userService.getRoleById(id)));
    }
}
