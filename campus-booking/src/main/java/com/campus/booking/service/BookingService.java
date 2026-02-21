package com.campus.booking.service;

import com.campus.booking.domain.Booking;
import com.campus.booking.dto.BookingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service layer contract for managing room bookings.
 * <p>
 * Handles booking creation, retrieval, and pagination.
 * Business rules such as duplicate booking prevention and
 * room availability validation are enforced in the implementation.
 */

public interface BookingService {

    /**
     * Creates a booking for a given student and room.
     *
     * @param studentId the unique student identifier
     * @param roomId    the room database identifier
     * @return the created {@link Booking}
     * @throws com.campus.booking.exception.ResourceNotFoundException
     *         if the student or room does not exist
     * @throws com.campus.booking.exception.ConflictException
     *         if the booking already exists or the room is unavailable
     */

    Booking createBooking(String studentId, Long roomId);

    /**
     * Retrieves a booking by its database ID.
     *
     * @param id booking identifier
     * @return an {@link Optional} containing the booking if found
     */

    Optional<Booking> getBookingById(Long id);

    /**
     * Retrieves paginated bookings.
     *
     * @param pageable pagination information
     * @return paginated list of bookings
     */

    Page<Booking> getAllBookings(Pageable pageable);

    /**
     * Retrieves paginated bookings as DTOs.
     *
     * @param pageable pagination information
     * @return paginated list of {@link BookingDTO}
     */

    Page<BookingDTO> getAllBookingDTOs(Pageable pageable);
}
