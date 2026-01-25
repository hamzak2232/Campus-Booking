package com.campus.booking.repository.impl;

import com.campus.booking.domain.Student;
import com.campus.booking.repository.StudentRepository;
import com.campus.booking.util.DbConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcStudentRepositoryImpl implements StudentRepository {

    @Override
    public void save(Student student) {
        // Upsert: insert if not exists, else update
        String sql = "INSERT INTO students (student_id, name, email, admin) " +
                "VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (student_id) DO UPDATE SET " +
                "name = EXCLUDED.name, email = EXCLUDED.email, admin = EXCLUDED.admin";

        try (Connection conn = DbConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getId());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getEmail());
            pstmt.setBoolean(4, student.isAdmin());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Student> findById(String id) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (Connection conn = DbConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Student student = mapRowToStudent(rs);
                return Optional.of(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DbConnectionUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                students.add(mapRowToStudent(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Helper to map ResultSet row to Student object
    private Student mapRowToStudent(ResultSet rs) throws SQLException {
        String studentId = rs.getString("student_id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        boolean admin = rs.getBoolean("admin");

        return new Student(studentId, name, email, admin);
    }
}
