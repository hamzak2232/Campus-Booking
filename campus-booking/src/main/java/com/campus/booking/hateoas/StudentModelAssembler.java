package com.campus.booking.hateoas;

import com.campus.booking.controller.StudentController;
import com.campus.booking.dto.StudentDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class StudentModelAssembler
        implements RepresentationModelAssembler<StudentDTO, EntityModel<StudentDTO>> {

    @Override
    public EntityModel<StudentDTO> toModel(StudentDTO student) {
        return EntityModel.of(student,
                linkTo(methodOn(StudentController.class)
                        .getStudent(student.studentId())).withSelfRel(),
                linkTo(methodOn(StudentController.class)
                        .getAllStudents(0,10)).withRel("students")
        );
    }
}
