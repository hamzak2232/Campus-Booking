package com.campus.booking.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.*;
import jakarta.validation.constraints.Size;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA
@ToString(of = {"studentId", "name", "role"})
@EqualsAndHashCode(of = "studentId")
@Entity
@Table(
        name = "students",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "student_id"),
                @UniqueConstraint(columnNames = "email")
        }
)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Student ID is required")
    @Column(name = "student_id", nullable = false, updatable = false)
    private String studentId;

    @NotBlank(message = "Student name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public Student(String studentId, String name, String email, String password, Role role) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
