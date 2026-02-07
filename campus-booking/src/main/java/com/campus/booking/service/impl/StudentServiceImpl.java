package com.campus.booking.service.impl;

import com.campus.booking.domain.Student;
import com.campus.booking.dto.StudentDTO;
import com.campus.booking.repository.StudentRepository;
import com.campus.booking.service.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void registerStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        studentRepository.save(student);
    }

    @Override
    public Optional<Student> getStudentById(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public StudentDTO toDTO(Student student){
        return new StudentDTO(
                student.getStudentId(),
                student.getName(),
                student.getEmail(),
                student.getRole()
        );
    }
}
