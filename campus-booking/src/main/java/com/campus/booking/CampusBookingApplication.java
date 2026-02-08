package com.campus.booking;

import com.campus.booking.domain.Role;
import com.campus.booking.domain.Room;
import com.campus.booking.domain.RoomType;
import com.campus.booking.domain.Student;
import com.campus.booking.repository.StudentRepository;
import com.campus.booking.service.RoomService;
import com.campus.booking.service.StudentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CampusBookingApplication {

    @Value("${ADMIN_SEED_PASSWORD}")
    private String adminPassword;

    @Value("${STUDENT_SEED_PASSWORD}")
    private String studentPassword;


    public static void main(String[] args) {
        SpringApplication.run(CampusBookingApplication.class, args);
    }

    @Bean
    CommandLineRunner seedData(
            StudentRepository repo,
            RoomService roomService,
            PasswordEncoder encoder
    ) {
        return args -> {

            repo.findByStudentId("admin")
                    .or(() -> repo.findByEmail("admin@campus.com"))
                    .orElseGet(() -> repo.save(new Student(
                            "admin",
                            "System Admin",
                            "admin@campus.com",
                            encoder.encode(adminPassword),
                            Role.ADMIN
                    )));


            repo.findByStudentId("s1")
                    .or(() -> repo.findByEmail("hamza@campus.com"))
                    .orElseGet(() -> repo.save(new Student(
                            "s1",
                            "Hamza",
                            "hamza@campus.com",
                            encoder.encode(studentPassword),
                            Role.STUDENT
                    )));


            // Rooms (with pagination-safe fetch)
            int page = 0;
            int size = 50; // large enough to check existing rooms
            boolean labExists = roomService.getAllRooms(PageRequest.of(page, size))
                    .stream()
                    .anyMatch(r -> r.getRoomCode().equals("Lab A"));

            if (!labExists) {
                roomService.addRoom(new Room("Lab A", 40, RoomType.LAB));
            }

            boolean meetingExists = roomService.getAllRooms(PageRequest.of(page, size))
                    .stream()
                    .anyMatch(r -> r.getRoomCode().equals("Meeting Room"));

            if (!meetingExists) {
                roomService.addRoom(new Room("Meeting Room", 50, RoomType.MEETING_ROOM));
            }
        };
    }

}
