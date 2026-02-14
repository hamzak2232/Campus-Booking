package com.campus.booking.controller;

import com.campus.booking.domain.Room;
import com.campus.booking.dto.RoomCreateDTO;
import com.campus.booking.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // GET /api/rooms
    @GetMapping
    public Page<Room> getAllRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return roomService.getAllRooms(pageable);
    }

    // GET /api/rooms/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Integer id) {
        return roomService.getRoomById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/rooms
    @PostMapping
    public ResponseEntity<Room> addRoom(@Valid @RequestBody RoomCreateDTO dto) {

        roomService.getRoomByCode(dto.roomCode()).ifPresent(r -> {
            throw new IllegalArgumentException("Room with this code already exists");
        });

        Room room = new Room(
                dto.roomCode(),
                dto.capacity(),
                dto.type()
        );

        Room saved = roomService.addRoom(room);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/api/rooms/" + saved.getId())
                .body(saved);
    }
}
