package com.campus.booking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record StudentCreateDTO(
        @NotBlank String studentId,
        @NotBlank String name,
        @Email String email,
        @Size(min = 8) String password
) {}
