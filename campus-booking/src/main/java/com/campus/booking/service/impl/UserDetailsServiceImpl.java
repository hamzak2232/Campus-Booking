package com.campus.booking.service.impl;

import com.campus.booking.domain.Student;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StudentServiceImpl studentService;

    public UserDetailsServiceImpl(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @Override
    public UserDetails loadUserByUsername(String studentId) throws UsernameNotFoundException {
        Student student = studentService.getStudentById(studentId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(student.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                student.getStudentId(),
                student.getPassword(),
                authorities
        );
    }
}