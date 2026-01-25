package com.campus.booking.repository.impl;

import com.campus.booking.domain.Student;
import com.campus.booking.repository.StudentRepository;

import java.util.*;

public class StudentRepositoryImpl implements StudentRepository {

    private final Map<String, Student> students = new HashMap<>();

    @Override
    public void save(Student student) {
        students.put(student.getId(), student);
    }

    @Override
    public Optional<Student> findById(String id) {
        return Optional.ofNullable(students.get(id));
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students.values());
    }
}
