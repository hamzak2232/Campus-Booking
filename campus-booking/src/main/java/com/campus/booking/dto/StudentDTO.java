package com.campus.booking.dto;
import com.campus.booking.domain.Role;

public record StudentDTO(
        Long id,
        String studentId,
        String name,
        String email,
        Role role
) {}
