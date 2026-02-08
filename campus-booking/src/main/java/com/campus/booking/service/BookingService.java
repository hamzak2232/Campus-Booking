package com.campus.booking.service;

import com.campus.booking.domain.Booking;
import com.campus.booking.dto.BookingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookingService {

    Booking createBooking(String studentId, Integer roomId);

    Optional<Booking> getBookingById(Integer id);

//    List<Booking> getAllBookings();

    Page<BookingDTO> getAllBookingDTOs(Pageable pageable);
}
