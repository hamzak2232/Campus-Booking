package com.campus.booking.repository;

import com.campus.booking.domain.Booking;
import com.campus.booking.domain.Room;
import com.campus.booking.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("""
        SELECT b
        FROM Booking b
        JOIN FETCH b.student
        JOIN FETCH b.room
        """)
    Page<Booking> findAllWithStudentAndRoom(Pageable pageable);

    Optional<Booking> findByStudentAndRoom(Student student, Room room);
}
