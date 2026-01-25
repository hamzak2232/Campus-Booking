package com.campus.booking.service.impl;

import com.campus.booking.domain.Booking;
import com.campus.booking.domain.Room;
import com.campus.booking.domain.Student;
import com.campus.booking.repository.BookingRepository;
import com.campus.booking.repository.RoomRepository;
import com.campus.booking.repository.StudentRepository;
import com.campus.booking.service.BookingService;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final StudentRepository studentRepository;
    private final RoomRepository roomRepository;

    private final AtomicInteger bookingIdGenerator = new AtomicInteger(1);

    public BookingServiceImpl(
            BookingRepository bookingRepository,
            StudentRepository studentRepository,
            RoomRepository roomRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.studentRepository = studentRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Booking createBooking(String studentId, int roomId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        if (!room.isAvailable()) {
            throw new IllegalStateException("Room is already booked");
        }

        room.setAvailable(false);

        Booking booking = new Booking(
                bookingIdGenerator.getAndIncrement(),
                student,
                room
        );

        bookingRepository.save(booking);
        return booking;
    }

    @Override
    public Optional<Booking> getBookingById(int id) {
        return bookingRepository.findById(id);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
