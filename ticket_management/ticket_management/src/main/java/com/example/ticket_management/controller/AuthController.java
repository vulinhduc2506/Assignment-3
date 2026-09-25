package com.example.ticket_management.controller;

import com.example.ticket_management.dto.request.LoginRequest;
import com.example.ticket_management.dto.response.LoginResponse;
import com.example.ticket_management.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = (String) authentication.getPrincipal();
        Long employeeId = (Long) authentication.getCredentials();

        return ResponseEntity.ok(
                String.format("{\"id\": %d, \"username\": \"%s\", \"fullName\": \"Nguyễn Văn A\"}", employeeId, username)
        );
    }
}
