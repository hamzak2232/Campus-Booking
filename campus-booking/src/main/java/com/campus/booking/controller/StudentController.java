package com.campus.booking.controller;

import com.campus.booking.domain.Student;
import com.campus.booking.dto.StudentCreateDTO;
import com.campus.booking.dto.StudentDTO;
import com.campus.booking.exception.ResourceNotFoundException;
import com.campus.booking.hateoas.StudentModelAssembler;
import com.campus.booking.service.StudentService;
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
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentModelAssembler studentAssembler;

    public StudentController(StudentService studentService,
                             StudentModelAssembler studentAssembler) {
        this.studentService = studentService;
        this.studentAssembler = studentAssembler;
    }

    // GET /api/students
    @GetMapping
    public PagedModel<EntityModel<StudentDTO>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentDTO> pageData =
                studentService.getAllStudents(pageable).map(studentService::toDTO);

        return PagedModel.of(
                pageData.getContent().stream()
                        .map(studentAssembler::toModel)
                        .toList(),
                new PagedModel.PageMetadata(
                        pageData.getSize(),
                        pageData.getNumber(),
                        pageData.getTotalElements(),
                        pageData.getTotalPages()
                )
        );
    }

    // GET /api/students/{studentId}
    @GetMapping("/{studentId}")
    public EntityModel<StudentDTO> getStudent(@PathVariable String studentId) {
        StudentDTO dto = studentService.getStudentById(studentId)
                .map(studentService::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));

        return studentAssembler.toModel(dto);
    }

    // POST /api/students
    @PostMapping
    public ResponseEntity<StudentDTO> registerStudent(@Valid @RequestBody StudentCreateDTO dto) {
        studentService.getStudentById(dto.studentId()).ifPresent(existing -> {
            throw new IllegalArgumentException("Student with ID already exists");
        });
        Student savedStudent = studentService.registerStudent(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/api/students/" + savedStudent.getStudentId())
                .body(studentService.toDTO(savedStudent));
    }
}
