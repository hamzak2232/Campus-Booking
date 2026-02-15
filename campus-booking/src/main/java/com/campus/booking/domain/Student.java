package com.campus.booking.domain;

import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

import org.hibernate.annotations.*;


import lombok.*;
import jakarta.validation.constraints.Size;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA
@ToString(of = {"studentId", "name", "role"})
@EqualsAndHashCode(of = "studentId", callSuper = false)
@SoftDelete(strategy = SoftDeleteType.DELETED, columnName = "is_deleted")
@SQLDelete(sql = "UPDATE students SET is_deleted = true WHERE id = ?")
@Entity
@Table(
        name = "students",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "student_id"),
                @UniqueConstraint(columnNames = "email")
        },
        indexes = {
                @Index(name = "idx_students_student_id", columnList = "student_id"),
                @Index(name = "idx_students_email", columnList = "email")
        }
)
public class Student extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "{student.studentId.required}")
    @Column(name = "student_id", nullable = false, updatable = false, length = 20)
    private String studentId;

    @NotBlank(message = "{student.name.required}")
    @Column(nullable = false, length = 50)
    private String name;

    @NotBlank(message = "{student.email.required}")
    @Email(message = "{student.email.invalid}")
    @Column(nullable = false, unique = true, length = 100)
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
