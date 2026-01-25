package com.campus.booking.repository;

import com.campus.booking.domain.Student;
import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    void save(Student student);
    Optional<Student> findById(String id);
    List<Student> findAll();
}
