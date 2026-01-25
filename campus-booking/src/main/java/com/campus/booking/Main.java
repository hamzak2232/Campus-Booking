package com.campus.booking;

import com.campus.booking.app.CampusBookingApp;
import com.campus.booking.repository.impl.BookingRepositoryImpl;
import com.campus.booking.repository.impl.RoomRepositoryImpl;
import com.campus.booking.repository.impl.StudentRepositoryImpl;
import com.campus.booking.service.impl.BookingServiceImpl;
import com.campus.booking.service.impl.RoomServiceImpl;
import com.campus.booking.service.impl.StudentServiceImpl;

public class Main {

    public static void main(String[] args) {

        // Repositories
        StudentRepositoryImpl studentRepository = new StudentRepositoryImpl();
        RoomRepositoryImpl roomRepository = new RoomRepositoryImpl();
        BookingRepositoryImpl bookingRepository = new BookingRepositoryImpl();

        // Services
        StudentServiceImpl studentService =
                new StudentServiceImpl(studentRepository);

        RoomServiceImpl roomService =
                new RoomServiceImpl(roomRepository);

        BookingServiceImpl bookingService =
                new BookingServiceImpl(
                        bookingRepository,
                        studentRepository,
                        roomRepository
                );

        // App
        CampusBookingApp app =
                new CampusBookingApp(
                        studentService,
                        roomService,
                        bookingService
                );

        app.start();
    }
}
