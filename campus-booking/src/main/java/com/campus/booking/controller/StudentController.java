package com.campus.booking.controller;

import com.campus.booking.domain.Student;
import com.campus.booking.dto.StudentDTO;
import com.campus.booking.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // GET /api/students
    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents().stream()
                .map(((com.campus.booking.service.impl.StudentServiceImpl) studentService)::toDTO)
                .toList();
    }
    // GET /api/students/{studentId}
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable String studentId) {
        return studentService.getStudentById(studentId)
                .map(((com.campus.booking.service.impl.StudentServiceImpl) studentService)::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // POST /api/students
    @PostMapping
    public ResponseEntity<StudentDTO> registerStudent(@RequestBody Student student) {
        studentService.registerStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.toDTO(student));
    }
}
