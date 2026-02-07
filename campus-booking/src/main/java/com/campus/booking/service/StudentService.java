package com.campus.booking.service;

import com.campus.booking.domain.Student;
import com.campus.booking.dto.StudentDTO;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    void registerStudent(Student student);
    Optional<Student> getStudentById(String id);   // this means studentId
    List<Student> getAllStudents();
    StudentDTO toDTO(Student student);
}
