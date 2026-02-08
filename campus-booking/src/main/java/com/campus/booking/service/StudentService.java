package com.campus.booking.service;

import com.campus.booking.domain.Student;
import com.campus.booking.dto.StudentCreateDTO;
import com.campus.booking.dto.StudentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student registerStudent(StudentCreateDTO dto);
    Optional<Student> getStudentById(String id);   // this means studentId
    Page<Student> getAllStudents(Pageable pageable);
    StudentDTO toDTO(Student student);
}
