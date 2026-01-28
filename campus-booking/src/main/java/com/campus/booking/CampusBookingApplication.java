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

            if (repo.findByStudentId("admin").isEmpty()) {
                repo.save(new Student(
                        "admin",
                        "System Admin",
                        "admin@campus.com",
                        encoder.encode("admin123"),
                        Role.ADMIN
                ));
            }

            if (repo.findByStudentId("s1").isEmpty()) {
                repo.save(new Student(
                        "s1",
                        "Hamza",
                        "hamza@campus.com",
                        encoder.encode("student123"),
                        Role.STUDENT
                ));
            }

            if (roomService.getRoomById(1).isEmpty()) {
                roomService.addRoom(new Room("Lab A", RoomType.LAB));
            }

            if (roomService.getRoomById(2).isEmpty()) {
                roomService.addRoom(new Room("Meeting Room", RoomType.MEETING_ROOM));
            }
        };
    }

}
