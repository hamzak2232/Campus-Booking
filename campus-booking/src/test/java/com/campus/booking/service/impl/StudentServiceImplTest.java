package com.campus.booking.service.impl;

import com.campus.booking.domain.Role;
import com.campus.booking.domain.Student;
import com.campus.booking.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    private StudentRepository studentRepository;
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        studentService = new StudentServiceImpl(studentRepository);
    }

    @Test
    void registerStudent_savesStudent() {
        Student student = new Student("s3", "Ali", "ali@campus.com", "password123", Role.STUDENT);
        studentService.registerStudent(student);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void getStudentById_returnsStudentIfExists() {
        Student student = new Student("s1", "Hamza", "hamza@campus.com", "student123", Role.STUDENT);
        when(studentRepository.findByStudentId("s1")).thenReturn(Optional.of(student));

        Optional<Student> found = studentService.getStudentById("s1");
        assertTrue(found.isPresent());
        assertEquals("Hamza", found.get().getName());
    }

    @Test
    void getStudentById_returnsEmptyIfNotFound() {
        when(studentRepository.findByStudentId("unknown")).thenReturn(Optional.empty());
        Optional<Student> found = studentService.getStudentById("unknown");
        assertFalse(found.isPresent());
    }

    @Test
    void getAllStudents_returnsAll() {
        List<Student> students = List.of(
                new Student("s1", "Hamza", "hamza@campus.com", "student123", Role.STUDENT),
                new Student("s2", "Ali", "ali@campus.com", "password123", Role.STUDENT)
        );
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();
        assertEquals(2, result.size());
    }
}
