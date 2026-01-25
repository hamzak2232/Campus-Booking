package com.campus.booking.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionUtil {

    private static final String URL = "jdbc:postgresql://localhost:5432/campus_booking";
    private static final String USER = "postgres";
    private static final String PASSWORD = "onemoretry111";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
