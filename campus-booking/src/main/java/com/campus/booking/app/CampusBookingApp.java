package com.campus.booking.app;

import com.campus.booking.domain.Booking;
import com.campus.booking.domain.Room;
import com.campus.booking.domain.Student;
import com.campus.booking.service.BookingService;
import com.campus.booking.service.RoomService;
import com.campus.booking.service.StudentService;
import com.campus.booking.util.AppConstants;
import com.campus.booking.util.DateUtil;
import com.campus.booking.util.InputUtil;

public class CampusBookingApp {

    private final StudentService studentService;
    private final RoomService roomService;
    private final BookingService bookingService;

    public CampusBookingApp(
            StudentService studentService,
            RoomService roomService,
            BookingService bookingService
    ) {
        this.studentService = studentService;
        this.roomService = roomService;
        this.bookingService = bookingService;
    }

    public void start() {
        System.out.println("=== " + AppConstants.APP_NAME + " ===");

        // Seed data (temporary)
        seedData();

        while (true) {
            System.out.println("\n1. Create Booking");
            System.out.println("2. View All Bookings");
            System.out.println("0. Exit");

            int choice = InputUtil.readInt("Choose option: ");

            switch (choice) {
                case 1 -> createBooking();
                case 2 -> showBookings();
                case 0 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void seedData() {
        studentService.registerStudent(
                new Student("S1", "Hamza", "hamza@email.com", false)
        );

        roomService.addRoom(new Room(1, "Lab A", "Computer Lab"));
        roomService.addRoom(new Room(2, "Meeting Room 1", "Meeting Room"));
    }

    private void createBooking() {
        try {
            String studentId = InputUtil.readString("Student ID: ");
            int roomId = InputUtil.readInt("Room ID: ");

            Booking booking = bookingService.createBooking(studentId, roomId);

            System.out.println(
                    "Booking successful at " +
                            DateUtil.format(booking.getTimestamp())
            );

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showBookings() {
        bookingService.getAllBookings()
                .forEach(System.out::println);
    }
}
