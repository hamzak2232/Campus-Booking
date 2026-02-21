package com.campus.booking.service;

import com.campus.booking.domain.Student;
import com.campus.booking.dto.StudentCreateDTO;
import com.campus.booking.dto.StudentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service contract for managing students.
 * <p>
 * Handles student registration, retrieval, and DTO conversion.
 */

public interface StudentService {

    /**
     * Registers a new student.
     *
     * @param dto data required to create a student
     * @return the persisted {@link Student}
     * @throws IllegalArgumentException if the password is blank
     */

    Student registerStudent(StudentCreateDTO dto);

    /**
     * Retrieves a student by their unique student ID.
     *
     * @param id student unique identifier
     * @return an {@link Optional} containing the student if found
     */

    Optional<Student> getStudentById(String id);   // this means studentId

    /**
     * Retrieves paginated students.
     *
     * @param pageable pagination information
     * @return paginated list of students
     */

    Page<Student> getAllStudents(Pageable pageable);

    /**
     * Converts a {@link Student} entity to a {@link StudentDTO}.
     *
     * @param student the entity to convert
     * @return converted DTO
     */

    StudentDTO toDTO(Student student);
}
