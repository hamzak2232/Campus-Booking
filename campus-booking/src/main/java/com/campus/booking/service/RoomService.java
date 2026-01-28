package com.campus.booking.service;

import com.campus.booking.domain.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    Room addRoom(Room room);

    Optional<Room> getRoomById(Integer id);

    List<Room> getAllRooms();
}
