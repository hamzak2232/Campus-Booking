package com.campus.booking.service;

import com.campus.booking.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    Room addRoom(Room room);

    Optional<Room> getRoomById(Integer id);

    Page<Room> getAllRooms(Pageable pageable);
}
