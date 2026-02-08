package com.campus.booking.controller;

import com.campus.booking.security.JwtUtil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest request) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.studentId(),
                        request.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetails userDetails)) {
            throw new IllegalStateException("Authentication principal is not UserDetails");
        }

        return jwtUtil.generateToken(userDetails);
    }

    public record LoginRequest(
            @NotBlank String studentId,
            @NotBlank String password
    ) {}
}
