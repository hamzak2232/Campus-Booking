package com.campus.booking.service;

import com.campus.booking.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service contract for managing rooms.
 * <p>
 * Responsible for room creation, lookup, and pagination.
 */

public interface RoomService {

    /**
     * Adds a new room to the system.
     *
     * @param room the room entity to persist
     * @return the saved {@link Room}
     */

    Room addRoom(Room room);

    /**
     * Retrieves a room by its database ID.
     *
     * @param id room identifier
     * @return an {@link Optional} containing the room if found
     */

    Optional<Room> getRoomById(Long id);

    /**
     * Retrieves paginated rooms.
     *
     * @param pageable pagination information
     * @return paginated list of rooms
     */

    Page<Room> getAllRooms(Pageable pageable);

    /**
     * Retrieves a room by its unique room code.
     *
     * @param roomCode unique room code
     * @return an {@link Optional} containing the room if found
     */

    Optional<Room> getRoomByCode(String roomCode);
}
