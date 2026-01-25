package com.campus.booking.domain;

public class Room {
    private final int id;          // unique room id
    private final String name;
    private final RoomType type;     // e.g., Lab, Meeting Room
    private boolean available;     // can be updated by service

    public Room(int id, String name, RoomType type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.available = true;     // default available
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public RoomType getType() { return type; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return name + " (" + type + ") - " + (available ? "Available" : "Booked");
    }
}
