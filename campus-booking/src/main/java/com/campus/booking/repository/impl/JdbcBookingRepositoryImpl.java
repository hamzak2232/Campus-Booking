package com.campus.booking.repository.impl;

import com.campus.booking.domain.Booking;
import com.campus.booking.domain.Room;
import com.campus.booking.domain.Student;
import com.campus.booking.repository.BookingRepository;
import com.campus.booking.util.DbConnectionUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcBookingRepositoryImpl implements BookingRepository {

    @Override
    public void save(Booking booking) {
        String sql = "INSERT INTO bookings (student_id, room_id, booking_date) VALUES (?, ?, ?)";

        try (Connection conn = DbConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, booking.getStudent().getId());
            pstmt.setInt(2, booking.getRoom().getId());
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(booking.getTimestamp()));


            pstmt.executeUpdate();

            // Retrieve auto-generated ID
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    // Optionally set the id in Booking object via reflection or leave it immutable
                    // For now, we can just print it
                    System.out.println("Booking saved with ID: " + generatedId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Booking> findById(int id) {
        String sql = "SELECT b.id as booking_id, b.booking_date, " +
                "s.student_id, s.name as student_name, s.email as student_email, s.admin as student_admin, " +
                "r.id as room_id, r.room_code, r.type, r.available " +
                "FROM bookings b " +
                "JOIN students s ON b.student_id = s.student_id " +   // ✅ FIXED
                "JOIN rooms r ON b.room_id = r.id " +
                "WHERE b.id = ?";


        try (Connection conn = DbConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToBooking(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();

        String sql = "SELECT b.id as booking_id, b.booking_date, " +
                "s.student_id, s.name as student_name, s.email as student_email, s.admin as student_admin, " +
                "r.id as room_id, r.room_code, r.type, r.available " +
                "FROM bookings b " +
                "JOIN students s ON b.student_id = s.student_id " +   // ✅ FIXED
                "JOIN rooms r ON b.room_id = r.id";

        try (Connection conn = DbConnectionUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                bookings.add(mapRowToBooking(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    // Helper method
    private Booking mapRowToBooking(ResultSet rs) throws SQLException {
        // Student object
        Student student = new Student(
                rs.getString("student_id"),
                rs.getString("student_name"),
                rs.getString("student_email"),
                rs.getBoolean("student_admin")
        );

        // Room object
        Room room = new Room(
                rs.getInt("room_id"),
                rs.getString("room_code"),
                rs.getString("type")
        );
        room.setAvailable(rs.getBoolean("available"));

        // Booking timestamp
        LocalDateTime timestamp = rs.getTimestamp("booking_date").toLocalDateTime();

        // Booking object
        return new Booking(rs.getInt("booking_id"), student, room) {
            // Override timestamp to match DB (since your constructor uses now())
            @Override
            public LocalDateTime getTimestamp() {
                return timestamp;
            }
        };
    }
}
