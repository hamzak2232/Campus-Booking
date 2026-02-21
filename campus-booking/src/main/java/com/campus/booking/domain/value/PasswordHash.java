package com.campus.booking.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class PasswordHash {

    @Column(name = "password", nullable = false)
    private String value;

    protected PasswordHash() {
        // for JPA
    }

    public PasswordHash(String hashedValue) {
        if (hashedValue == null || hashedValue.isBlank()) {
            throw new IllegalArgumentException("Password hash is required");
        }

        boolean looksBcrypt =
                hashedValue.startsWith("$2a$") || hashedValue.startsWith("$2b$") || hashedValue.startsWith("$2y$");

        if (!looksBcrypt) {
            throw new IllegalArgumentException("Password must be a bcrypt hash");
        }

        this.value = hashedValue;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PasswordHash other)) return false;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "[PROTECTED]";
    }
}