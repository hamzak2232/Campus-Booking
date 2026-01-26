package com.campus.booking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(
        of = {"studentId", "name", "admin"},
        includeFieldNames = false
)
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
    private Integer id;       // unique student id

    @Column(name = "student_id", nullable = false)
    private String studentId; // university student ID

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "admin", nullable = false)
    private boolean admin;
}