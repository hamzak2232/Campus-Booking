package com.campus.booking.repository;

import com.campus.booking.domain.Room;
import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    void save(Room room);
    Optional<Room> findById(int id);
    List<Room> findAll();
}
