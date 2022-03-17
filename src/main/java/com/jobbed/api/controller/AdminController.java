package com.jobbed.api.controller;

import com.jobbed.api.user.domain.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
class AdminController {

    private final UserRepository repository;

    @GetMapping
    public ResponseEntity<?> adminEndpoint(Authentication authentication) {
        return ResponseEntity.ok(authentication);
    }

    @GetMapping("/users")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }
}
