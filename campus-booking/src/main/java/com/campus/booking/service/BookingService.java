package com.campus.booking.service;

import com.campus.booking.domain.Booking;
import com.campus.booking.dto.BookingDTO;

import java.util.List;
import java.util.Optional;

public interface BookingService {

    Booking createBooking(String studentId, Integer roomId);

    Optional<Booking> getBookingById(Integer id);

//    List<Booking> getAllBookings();

    List<BookingDTO> getAllBookingDTOs();
}
