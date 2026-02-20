package com.campus.booking.domain;


import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "student", "room", "timestamp"})
@EqualsAndHashCode(of = "id", callSuper = false)
@SQLDelete(sql = "UPDATE bookings SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@Entity
@Table(
        name = "bookings",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_id", "room_id"})
        },
        indexes = {
                @Index(name = "idx_bookings_student", columnList = "student_id"),
                @Index(name = "idx_bookings_room", columnList = "room_id"),
                @Index(name = "idx_bookings_student_room", columnList = "student_id, room_id")
        }
)
public class Booking extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Booking must have a student")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @NotNull(message = "Booking must have a room")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @NotNull(message = "Booking date is required")
    @Column(name = "booking_date", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    public Booking(Student student, Room room) {
        this.student = student;
        this.room = room;
        this.timestamp = LocalDateTime.now();
    }
}
