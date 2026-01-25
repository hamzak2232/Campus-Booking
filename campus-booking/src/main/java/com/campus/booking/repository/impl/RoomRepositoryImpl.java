package com.campus.booking.repository.impl;

import com.campus.booking.domain.Room;
import com.campus.booking.repository.RoomRepository;

import java.util.*;

public class RoomRepositoryImpl implements RoomRepository {

    private final Map<Integer, Room> rooms = new HashMap<>();

    @Override
    public void save(Room room) {
        rooms.put(room.getId(), room);
    }

    @Override
    public Optional<Room> findById(int id) {
        return Optional.ofNullable(rooms.get(id));
    }

    @Override
    public List<Room> findAll() {
        return new ArrayList<>(rooms.values());
    }
}
