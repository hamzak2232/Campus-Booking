package com.campus.booking.domain;

import jakarta.persistence.*;

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
    private  Integer id;       // unique student id

    @Column(name = "student_id", nullable = false)
    private String studentId; // university student ID

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "admin", nullable = false)
    private boolean admin;

    protected Student() {}

    public Student(String studentId, String name, String email, boolean admin) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.admin = admin;
    }

    public Integer getId() { return id; }
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public boolean isAdmin() { return admin; }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return studentId + " - " + name + (admin ? " (Admin)" : "");
    }
}
