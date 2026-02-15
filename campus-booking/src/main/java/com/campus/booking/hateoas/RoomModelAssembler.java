package com.campus.booking.hateoas;

import com.campus.booking.controller.RoomController;
import com.campus.booking.domain.Room;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RoomModelAssembler implements RepresentationModelAssembler<Room, EntityModel<Room>> {

    @Override
    public EntityModel<Room> toModel(Room room) {
        return EntityModel.of(room,
                linkTo(methodOn(RoomController.class).getRoom(room.getId())).withSelfRel(),
                linkTo(methodOn(RoomController.class).getAllRooms(0,10)).withRel("rooms")
        );
    }
}
