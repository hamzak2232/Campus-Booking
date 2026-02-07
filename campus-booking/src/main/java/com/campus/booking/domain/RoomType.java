package com.campus.booking.domain;

public enum RoomType {
    LAB("Lab"),
    MEETING_ROOM("Meeting Room"),
    CLASSROOM("Classroom");

    private final String label;

    RoomType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
