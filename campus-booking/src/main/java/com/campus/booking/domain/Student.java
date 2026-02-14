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

    @NotBlank(message = "{student.studentId.required}")
    @Column(name = "student_id", nullable = false, updatable = false)
    private String studentId;

    @NotBlank(message = "{student.name.required}")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "{student.email.required}")
    @Email(message = "{student.email.invalid}")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "{student.password.required}")
    @Size(min = 8, message = "{student.password.min}")
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
