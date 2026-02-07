package com.campus.booking.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"roomCode", "type", "available"})
@EqualsAndHashCode(of = "roomCode")
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Room code is required")
    @Column(name = "room_code", nullable = false, unique = true, updatable = false)
    private String roomCode;

    @NotNull(message = "Room type is required")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type", columnDefinition = "room_type_enum", nullable = false)
    private RoomType type;

    @NotNull(message = "Room capacity is required")
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private boolean available = true;

    public Room(String roomCode, Integer capacity, RoomType type) {
        this.roomCode = roomCode;
        this.capacity = capacity;
        this.type = type;
        this.available = true;
    }

    public void markUnavailable() {
        this.available = false;
    }

    public void markAvailable() {
        this.available = true;
    }
}
