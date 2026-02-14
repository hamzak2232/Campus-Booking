package com.campus.booking.service.impl;

import com.campus.booking.domain.Student;
import com.campus.booking.service.StudentService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StudentService studentService;  // use interface

    public UserDetailsServiceImpl(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public UserDetails loadUserByUsername(String studentId) throws UsernameNotFoundException {

        Student student = studentService.getStudentById(studentId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                student.getStudentId(),
                student.getPassword(),
                List.of(new SimpleGrantedAuthority(student.getRole().name()))
        );
    }
}

