package com.campus.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BookingRequestDTO {

    @NotBlank(message = "Student ID is required")
    private String studentId;

    @NotNull(message = "Room ID is required")
    private Integer roomId;
}
