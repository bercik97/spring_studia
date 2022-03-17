package com.jobbed.api.security;

import com.jobbed.api.security.dto.AuthenticationRequest;
import com.jobbed.api.security.dto.AuthenticationResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
@AllArgsConstructor
class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.authenticate(request));
    }
}
