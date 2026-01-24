package com.campus.booking.domain;

public class Student {
    private final String id;       // unique student id
    private final String name;
    private final String email;
    private final boolean admin;   // optional: admin privileges

    public Student(String id, String name, String email, boolean admin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.admin = admin;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public boolean isAdmin() { return admin; }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}
