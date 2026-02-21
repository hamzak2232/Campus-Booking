package com.campus.booking.controller;

import com.campus.booking.domain.Room;
import com.campus.booking.dto.RoomCreateDTO;
import com.campus.booking.exception.ResourceNotFoundException;
import com.campus.booking.hateoas.RoomModelAssembler;
import com.campus.booking.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final RoomModelAssembler roomAssembler;

    public RoomController(RoomService roomService,
                          RoomModelAssembler roomAssembler) {
        this.roomService = roomService;
        this.roomAssembler = roomAssembler;
    }

    // GET /api/rooms
    @GetMapping
    public PagedModel<EntityModel<Room>> getAllRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> pageData = roomService.getAllRooms(pageable);

        return PagedModel.of(
                pageData.getContent().stream()
                        .map(roomAssembler::toModel)
                        .toList(),
                new PagedModel.PageMetadata(
                        pageData.getSize(),
                        pageData.getNumber(),
                        pageData.getTotalElements(),
                        pageData.getTotalPages()
                )
        );
    }


    // GET /api/rooms/{id}
    @GetMapping("/{id}")
    public EntityModel<Room> getRoom(@PathVariable Long id) {
        Room room = roomService.getRoomById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found: " + id));

        return roomAssembler.toModel(room);
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
