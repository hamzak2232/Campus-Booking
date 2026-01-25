package com.campus.booking.repository.impl;

import com.campus.booking.domain.Room;
import com.campus.booking.repository.RoomRepository;
import com.campus.booking.util.DbConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcRoomRepositoryImpl implements RoomRepository {

    @Override
    public void save(Room room) {
        String sql = "INSERT INTO rooms (id, room_code, type, available) " +
                "VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET " +
                "room_code = EXCLUDED.room_code, type = EXCLUDED.type, available = EXCLUDED.available";

        try (Connection conn = DbConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, room.getId());
            pstmt.setString(2, room.getName());
            pstmt.setString(3, room.getType());
            pstmt.setBoolean(4, room.isAvailable());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Room> findById(int id) {
        String sql = "SELECT * FROM rooms WHERE id = ?";
        try (Connection conn = DbConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToRoom(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";

        try (Connection conn = DbConnectionUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rooms.add(mapRowToRoom(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    // Helper method to map a database row to a Room object
    private Room mapRowToRoom(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("room_code");
        String type = rs.getString("type");
        boolean available = rs.getBoolean("available");

        Room room = new Room(id, name, type);
        room.setAvailable(available);

        return room;
    }
}
