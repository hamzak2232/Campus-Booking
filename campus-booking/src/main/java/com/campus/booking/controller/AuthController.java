package com.campus.booking.controller;

import com.campus.booking.security.JwtUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest request) {

        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.studentId(),
                            request.password()
                    )
            );

            Object principal = authentication.getPrincipal();
            if (!(principal instanceof UserDetails userDetails)) {
                log.error("Login succeeded but principal is not UserDetails. studentId={}", request.studentId());
                throw new IllegalStateException("Authentication principal is not UserDetails");
            }

            log.info("Login success studentId={}", request.studentId());
            return jwtUtil.generateToken(userDetails);

        } catch (BadCredentialsException ex) {
            // Don’t reveal if user exists or not
            log.warn("Login failed (bad credentials) studentId={}", request.studentId());
            throw ex; // your GlobalExceptionHandler turns this into 401
        }
    }

    public record LoginRequest(
            @NotBlank String studentId,
            @NotBlank String password
    ) {}
}
