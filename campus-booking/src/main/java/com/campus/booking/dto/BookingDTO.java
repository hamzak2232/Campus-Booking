package com.campus.booking.dto;

import java.time.LocalDateTime;

public record BookingDTO(
        Long id,
        String studentId,
        String studentName,
        String roomCode,
        String roomType,
        LocalDateTime timestamp
) {}
