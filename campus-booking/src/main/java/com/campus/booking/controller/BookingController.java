package com.campus.booking.controller;

import com.campus.booking.domain.Booking;
import com.campus.booking.dto.BookingDTO;
import com.campus.booking.dto.BookingRequestDTO;
import com.campus.booking.service.BookingService;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.campus.booking.hateoas.BookingModelAssembler;


import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookingModelAssembler bookingAssembler;

    public BookingController(BookingService bookingService,
                             BookingModelAssembler bookingAssembler) {
        this.bookingService = bookingService;
        this.bookingAssembler = bookingAssembler;
    }

    // GET /api/bookings
    @GetMapping
    public PagedModel<EntityModel<BookingDTO>> getAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> pageData = bookingService.getAllBookings(pageable);

        return PagedModel.of(
                pageData.getContent().stream()
                        .map(bookingAssembler::toModel)
                        .toList(),
                new PagedModel.PageMetadata(
                        pageData.getSize(),
                        pageData.getNumber(),
                        pageData.getTotalElements(),
                        pageData.getTotalPages()
                )
        );
    }


    // GET /api/bookings/{id}
    @GetMapping("/{id}")
    public EntityModel<BookingDTO> getBooking(@PathVariable Integer id) {
        Booking booking = bookingService.getBookingById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        return bookingAssembler.toModel(booking);
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
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
