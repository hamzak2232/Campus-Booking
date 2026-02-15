package com.campus.booking.hateoas;

import com.campus.booking.controller.BookingController;
import com.campus.booking.controller.RoomController;
import com.campus.booking.controller.StudentController;
import com.campus.booking.domain.Booking;
import com.campus.booking.dto.BookingDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class BookingModelAssembler
        implements RepresentationModelAssembler<Booking, EntityModel<BookingDTO>> {

    @Override
    public EntityModel<BookingDTO> toModel(Booking booking) {

        BookingDTO dto = new BookingDTO(
                booking.getId(),
                booking.getStudent().getStudentId(),
                booking.getStudent().getName(),
                booking.getRoom().getRoomCode(),
                booking.getRoom().getType().toString(),
                booking.getTimestamp()
        );

        return EntityModel.of(dto,
                linkTo(methodOn(BookingController.class).getBooking(booking.getId())).withSelfRel(),
                linkTo(methodOn(StudentController.class)
                        .getStudent(booking.getStudent().getStudentId())).withRel("student"),
                linkTo(methodOn(RoomController.class)
                        .getRoom(booking.getRoom().getId())).withRel("room")
        );
    }
}
