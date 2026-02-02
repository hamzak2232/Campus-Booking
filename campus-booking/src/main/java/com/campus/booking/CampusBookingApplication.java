package com.campus.booking;

import com.campus.booking.domain.Role;
import com.campus.booking.domain.Room;
import com.campus.booking.domain.RoomType;
import com.campus.booking.domain.Student;
import com.campus.booking.repository.StudentRepository;
import com.campus.booking.service.RoomService;
import com.campus.booking.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CampusBookingApplication {

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
                            encoder.encode("admin123"),
                            Role.ADMIN
                    )));


            repo.findByStudentId("s1")
                    .or(() -> repo.findByEmail("hamza@campus.com"))
                    .orElseGet(() -> repo.save(new Student(
                            "s1",
                            "Hamza",
                            "hamza@campus.com",
                            encoder.encode("student123"),
                            Role.STUDENT
                    )));


            // Rooms
            if (roomService.getAllRooms().stream().noneMatch(r -> r.getRoomCode().equals("Lab A"))) {
                roomService.addRoom(new Room("Lab A", RoomType.LAB));
            }

            if (roomService.getAllRooms().stream().noneMatch(r -> r.getRoomCode().equals("Meeting Room"))) {
                roomService.addRoom(new Room("Meeting Room", RoomType.MEETING_ROOM));
            }
        };
    }

}
