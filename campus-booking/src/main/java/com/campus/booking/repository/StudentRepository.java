package com.campus.booking.repository;

import com.campus.booking.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByStudentId(String studentId);

    Optional<Student> findByEmail(String email);
}
