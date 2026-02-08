package com.campus.booking.service.impl;

import com.campus.booking.domain.Booking;
import com.campus.booking.domain.Room;
import com.campus.booking.domain.Student;
import com.campus.booking.dto.BookingDTO;
import com.campus.booking.repository.BookingRepository;
import com.campus.booking.repository.RoomRepository;
import com.campus.booking.repository.StudentRepository;
import com.campus.booking.service.BookingService;
import jakarta.transaction.Transactional;
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

        if (!room.isAvailable()) {
            throw new IllegalStateException("Room is already booked");
        }

        // Domain-driven way
        room.markUnavailable();

        Booking booking = new Booking(student, room);

        return bookingRepository.save(booking);
    }

    @Override
    public Optional<Booking> getBookingById(Integer id) {
        return bookingRepository.findById(id);
    }

//    @Override
//    public List<Booking> getAllBookings() {
//        return bookingRepository.findAll();
//    }

    @Override
    public Page<BookingDTO> getAllBookingDTOs(Pageable pageable) {
        return bookingRepository.findAll(pageable)
                .map(b -> new BookingDTO(
                        b.getId(),
                        b.getStudent() != null ? b.getStudent().getStudentId() : "Unknown",
                        b.getStudent() != null ? b.getStudent().getName() : "Unknown",
                        b.getRoom() != null ? b.getRoom().getRoomCode() : "Unknown",
                        b.getRoom() != null && b.getRoom().getType() != null
                                ? b.getRoom().getType().toString() : "Unknown",
                        b.getTimestamp()
                ));
    }
}
