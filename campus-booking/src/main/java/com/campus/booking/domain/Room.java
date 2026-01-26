package com.campus.booking.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "room_code", nullable = false, unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private RoomType type;
    @Column(nullable = false)
    private boolean available = true;

    //No-args constructor required by JPA
    protected Room() {}

    public Room(String name, RoomType type) {
        this.name = name;
        this.type = type;
        this.available = true;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public RoomType getType() { return type; }
    public void setType(RoomType type) { this.type = type; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }


    @Override
    public String toString() {
        return name + " (" + type + ") - " + (available ? "Available" : "Booked");
    }
}
