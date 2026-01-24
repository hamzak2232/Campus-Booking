package com.campus.booking.domain;

import java.time.LocalDateTime;

public class Booking {
    private final int id;           // auto-generated
    private final Student student;
    private final Room room;
    private final LocalDateTime timestamp;

    public Booking(int id, Student student, Room room) {
        this.id = id;
        this.student = student;
        this.room = room;
        this.timestamp = LocalDateTime.now();  // booking time
    }

    public int getId() { return id; }
    public Student getStudent() { return student; }
    public Room getRoom() { return room; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "Booking #" + id + ": " + student + " -> " + room + " at " + timestamp;
    }
}
