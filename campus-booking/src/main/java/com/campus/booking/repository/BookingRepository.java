package com.campus.booking.repository;

import com.campus.booking.domain.Booking;
import com.campus.booking.domain.Room;
import com.campus.booking.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Optional<Booking> findByStudentAndRoom(Student student, Room room);
}
