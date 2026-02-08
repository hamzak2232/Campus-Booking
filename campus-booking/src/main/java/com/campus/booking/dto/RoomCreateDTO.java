package com.campus.booking.dto;

import com.campus.booking.domain.RoomType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoomCreateDTO(
        @NotBlank String roomCode,
        @NotNull RoomType type,
        @Min(20) int capacity
) {}