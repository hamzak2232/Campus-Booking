package com.campus.booking.service;

import com.campus.booking.domain.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking createBooking(String studentId, int roomId);
    Optional<Booking> getBookingById(int id);
    List<Booking> getAllBookings();
}
