package com.campus.booking.domain;

import com.campus.booking.domain.value.Email;
import com.campus.booking.domain.value.PasswordHash;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA
@ToString(of = {"studentId", "name", "role"})
@EqualsAndHashCode(of = "studentId", callSuper = false)
@SQLDelete(sql = "UPDATE students SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
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
    private Long id;

    @NotBlank(message = "{student.studentId.required}")
    @Column(name = "student_id", nullable = false, updatable = false, length = 20)
    private String studentId;

    @NotBlank(message = "{student.name.required}")
    @Column(nullable = false, length = 50)
    private String name;

    @Embedded
    private Email email;

    @Embedded
    private PasswordHash password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public Student(String studentId, String name, Email email, PasswordHash password, Role role) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Convenience getter for code that still expects a String.
     */
    public String getEmailValue() {
        return email == null ? null : email.value();
    }

    /**
     * Convenience getter for Spring Security, etc.
     */
    public String getPasswordValue() {
        return password == null ? null : password.value();
    }

    public void setPassword(PasswordHash password) {
        this.password = password;
    }

}
