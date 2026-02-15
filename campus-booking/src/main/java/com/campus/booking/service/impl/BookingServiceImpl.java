package com.campus.booking.service.impl;

import com.campus.booking.domain.Booking;
import com.campus.booking.domain.Room;
import com.campus.booking.domain.Student;
import com.campus.booking.dto.BookingDTO;
import com.campus.booking.repository.BookingRepository;
import com.campus.booking.repository.RoomRepository;
import com.campus.booking.repository.StudentRepository;
import com.campus.booking.service.BookingService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final StudentRepository studentRepository;
    private final RoomRepository roomRepository;

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
    public Booking createBooking(String studentId, Integer roomId) {

        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        bookingRepository.findByStudentAndRoom(student, room).ifPresent(b -> {
            throw new IllegalStateException("Booking already exists for this student and room");
        });

        if (!room.isAvailable()) {
            throw new IllegalStateException("Room is already booked");
        }

        // Domain-driven way
        room.markUnavailable();

        Booking booking = new Booking(student, room);

        return bookingRepository.save(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> getBookingById(Integer id) {
        return bookingRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Booking> getAllBookings(Pageable pageable) {
        return bookingRepository.findAllWithStudentAndRoom(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingDTO> getAllBookingDTOs(Pageable pageable) {
        return bookingRepository.findAllWithStudentAndRoom(pageable)
                .map(b -> new BookingDTO(
                        b.getId(),
                        b.getStudent().getStudentId(),
                        b.getStudent().getName(),
                        b.getRoom().getRoomCode(),
                        b.getRoom().getType().toString(),
                        b.getTimestamp()
                ));
    }
}
