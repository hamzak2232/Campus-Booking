package com.campus.booking.service.impl;

import com.campus.booking.domain.Role;
import com.campus.booking.domain.Student;
import com.campus.booking.dto.StudentCreateDTO;
import com.campus.booking.dto.StudentDTO;
import com.campus.booking.repository.StudentRepository;
import com.campus.booking.service.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository,
                              PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Student registerStudent(StudentCreateDTO dto) {
        Student student = new Student(
                dto.studentId(),
                dto.name(),
                dto.email(),
                passwordEncoder.encode(dto.password()),
                Role.STUDENT
        );

        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> getStudentById(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    @Override
    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public StudentDTO toDTO(Student student){
        return new StudentDTO(
                student.getId(),
                student.getStudentId(),
                student.getName(),
                student.getEmail(),
                student.getRole()
        );
    }
}
