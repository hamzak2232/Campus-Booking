package com.campus.booking.repository;

import com.campus.booking.domain.Booking;
import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    void save(Booking booking);
    Optional<Booking> findById(int id);
    List<Booking> findAll();
}
