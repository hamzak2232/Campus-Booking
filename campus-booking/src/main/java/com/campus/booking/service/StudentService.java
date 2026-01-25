package com.campus.booking.service;

import com.campus.booking.domain.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    void registerStudent(Student student);
    Optional<Student> getStudentById(String id);
    List<Student> getAllStudents();
}
