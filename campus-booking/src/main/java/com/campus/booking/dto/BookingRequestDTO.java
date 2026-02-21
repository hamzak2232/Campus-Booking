package com.campus.booking.dto;

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

    @NotNull(message = "Room ID is required")
    private Long roomId;
}
