package com.jobbed.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/unauthenticated")
@RequiredArgsConstructor
class UnauthenticatedController {

    private final UnauthenticatedService service;

    @GetMapping
    public ResponseEntity<?> unauthenticatedEndpoint() {
        return ResponseEntity.ok(Map.of("unauthenticated", "endpoint"));
    }

    @GetMapping("/url")
    public ResponseEntity<?> urlTest() {
        return ResponseEntity.ok(Map.of("app.url", service.getAppUrl()));
    }
}

@Service
class UnauthenticatedService {

    @Value("${app.url:#{'http://localhost:8080'}}")
    private String APP_URL;

    public String getAppUrl() {
        return this.APP_URL;
    }
}
