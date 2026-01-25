package com.campus.booking.repository.impl;

import com.campus.booking.domain.Booking;
import com.campus.booking.repository.BookingRepository;

import java.util.*;

public class BookingRepositoryImpl implements BookingRepository {

    private final Map<Integer, Booking> bookings = new HashMap<>();

    @Override
    public void save(Booking booking) {
        bookings.put(booking.getId(), booking);
    }

    @Override
    public Optional<Booking> findById(int id) {
        return Optional.ofNullable(bookings.get(id));
    }

    @Override
    public List<Booking> findAll() {
        return new ArrayList<>(bookings.values());
    }
}
