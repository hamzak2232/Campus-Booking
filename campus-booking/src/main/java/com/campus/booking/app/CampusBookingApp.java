package com.campus.booking.app;

import com.campus.booking.domain.Booking;
import com.campus.booking.domain.Room;
import com.campus.booking.domain.RoomType;
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
        // -----------------------------
        // Seed Students
        // -----------------------------
        if (studentService.getStudentById("S1").isEmpty()) {
            studentService.registerStudent(
                    new Student("S1", "Hamza", "hamza@email.com", false)
            );
        }

        if (studentService.getStudentById("S2").isEmpty()) {
            studentService.registerStudent(
                    new Student("S2", "Ali", "ali@email.com", false)
            );
        }

        // -----------------------------
        // Seed Rooms
        // -----------------------------
        boolean room1Exists = roomService.getRoomById(1).isPresent();
        if (!room1Exists) {
            roomService.addRoom(new Room(1, "Lab A", RoomType.LAB));
        }

        boolean room2Exists = roomService.getRoomById(2).isPresent();
        if (!room2Exists) {
            roomService.addRoom(new Room(2, "Meeting Room 1", RoomType.MEETING_ROOM));
        }
    }


    private void createBooking() {
        try {
            String studentId = InputUtil.readString("Student ID: ");
            int roomId = InputUtil.readInt("Room ID: ");

            // -----------------------------
            // Validate Student
            // -----------------------------
            if (studentService.getStudentById(studentId).isEmpty()) {
                System.out.println("Error: Student with ID '" + studentId + "' does not exist.");
                return;
            }

            // -----------------------------
            // Validate Room
            // -----------------------------
            if (roomService.getRoomById(roomId).isEmpty()) {
                System.out.println("Error: Room with ID '" + roomId + "' does not exist.");
                return;
            }

            // -----------------------------
            // Create Booking
            // -----------------------------
            Booking booking = bookingService.createBooking(studentId, roomId);

            System.out.println(
                    "Booking successful at " + DateUtil.format(booking.getTimestamp())
            );

        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void showBookings() {
        var bookings = bookingService.getAllBookings();

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("\n=== All Bookings ===");
        for (var booking : bookings) {
            String studentInfo = booking.getStudent().getName() + " (" + booking.getStudent().getId() + ")";
            String roomInfo = booking.getRoom().getName() + " [" + booking.getRoom().getType() + "]";
            String timestamp = DateUtil.format(booking.getTimestamp());

            System.out.println("Booking #" + booking.getId() +
                    ": " + studentInfo +
                    " -> " + roomInfo +
                    " at " + timestamp +
                    " | Room Available: " + (booking.getRoom().isAvailable() ? "Yes" : "No"));
        }
    }

}
