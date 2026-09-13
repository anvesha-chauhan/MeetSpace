package com.example.meetspace.repositories;

import com.example.meetspace.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByRoomId(Long roomId);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Booking b " +
           "WHERE b.room.id = :roomId AND b.status = 'CONFIRMED' " +
           "AND b.startTime < :endTime AND b.endTime > :startTime")
    boolean existsConflictingBooking(@Param("roomId") Long roomId, 
                                     @Param("startTime") LocalDateTime startTime, 
                                     @Param("endTime") LocalDateTime endTime);
}
