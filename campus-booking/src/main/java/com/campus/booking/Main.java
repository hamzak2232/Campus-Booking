package com.campus.booking;

import com.campus.booking.app.CampusBookingApp;
import com.campus.booking.repository.impl.JdbcBookingRepositoryImpl;
import com.campus.booking.repository.impl.JdbcRoomRepositoryImpl;
import com.campus.booking.repository.impl.JdbcStudentRepositoryImpl;
import com.campus.booking.service.impl.BookingServiceImpl;
import com.campus.booking.service.impl.RoomServiceImpl;
import com.campus.booking.service.impl.StudentServiceImpl;

public class Main {

    public static void main(String[] args) {

        // -----------------------------
        // Repositories (JDBC)
        // -----------------------------
        JdbcStudentRepositoryImpl studentRepository = new JdbcStudentRepositoryImpl();
        JdbcRoomRepositoryImpl roomRepository = new JdbcRoomRepositoryImpl();
        JdbcBookingRepositoryImpl bookingRepository = new JdbcBookingRepositoryImpl();

        // -----------------------------
        // Services
        // -----------------------------
        StudentServiceImpl studentService = new StudentServiceImpl(studentRepository);
        RoomServiceImpl roomService = new RoomServiceImpl(roomRepository);
        BookingServiceImpl bookingService = new BookingServiceImpl(
                bookingRepository,
                studentRepository,
                roomRepository
        );

        // -----------------------------
        // App
        // -----------------------------
        CampusBookingApp app = new CampusBookingApp(
                studentService,
                roomService,
                bookingService
        );

        // Start the console app
        app.start();
    }
}
