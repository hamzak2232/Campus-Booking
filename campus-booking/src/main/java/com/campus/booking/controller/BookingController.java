package com.campus.booking.controller;

import com.campus.booking.domain.Booking;
import com.campus.booking.dto.BookingDTO;
import com.campus.booking.dto.BookingRequestDTO;
import com.campus.booking.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // GET /api/bookings
    @GetMapping
    public List<BookingDTO> getAllBookings() {
        return bookingService.getAllBookingDTOs();
    }

    // GET /api/bookings/{id}
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable Integer id) {
        return bookingService.getBookingById(id)
                .map(b -> new BookingDTO(
                        b.getId(),
                        b.getStudent().getStudentId(),
                        b.getStudent().getName(),
                        b.getRoom().getRoomCode(),
                        b.getRoom().getType().toString(),
                        b.getTimestamp()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/bookings
    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequestDTO request) {
        try {
            // Get authenticated student from JWT
            UserDetails user = (UserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            // Create booking
            Booking booking = bookingService.createBooking(user.getUsername(), request.getRoomId());

            // Convert to DTO before returning
            BookingDTO bookingDTO = new BookingDTO(
                    booking.getId(),
                    booking.getStudent().getStudentId(),
                    booking.getStudent().getName(),
                    booking.getRoom().getRoomCode(),
                    booking.getRoom().getType().toString(),
                    booking.getTimestamp()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(bookingDTO);

        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
