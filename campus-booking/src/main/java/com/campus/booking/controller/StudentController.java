package com.campus.booking.controller;

import com.campus.booking.domain.Student;
import com.campus.booking.dto.StudentCreateDTO;
import com.campus.booking.dto.StudentDTO;
import com.campus.booking.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // GET /api/students
    @GetMapping
    public Page<StudentDTO> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return studentService.getAllStudents(pageable)
                .map(studentService::toDTO);
    }
    // GET /api/students/{studentId}
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable String studentId) {
        return studentService.getStudentById(studentId)
                .map(studentService::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // POST /api/students
    @PostMapping
    public ResponseEntity<StudentDTO> registerStudent(@Valid @RequestBody StudentCreateDTO dto) {
        Student savedStudent = studentService.registerStudent(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/api/students/" + savedStudent.getStudentId())
                .body(studentService.toDTO(savedStudent));
    }
}
