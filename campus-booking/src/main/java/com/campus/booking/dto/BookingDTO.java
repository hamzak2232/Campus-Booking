package com.campus.booking.dto;

import java.awt.datatransfer.StringSelection;
import java.time.LocalDateTime;

public record BookingDTO(
        Integer id,
        String studentId,
        String studentName,
        String roomCode,
        String roomType,
        LocalDateTime timestamp
) {}
