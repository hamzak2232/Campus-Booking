package com.campus.booking.service;

import com.campus.booking.domain.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    void addRoom(Room room);
    Optional<Room> getRoomById(int id);
    List<Room> getAllRooms();
}
