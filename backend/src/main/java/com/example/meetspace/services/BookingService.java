package com.example.meetspace.services;

import com.example.meetspace.models.Booking;
import com.example.meetspace.models.Room;
import com.example.meetspace.models.User;
import com.example.meetspace.repositories.BookingRepository;
import com.example.meetspace.repositories.RoomRepository;
import com.example.meetspace.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    public Booking createBooking(Long userId, Long roomId, LocalDateTime startTime, LocalDateTime endTime) throws Exception {
        if (startTime.isAfter(endTime) || startTime.isEqual(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        // Conflict Detection Logic (Server-side)
        boolean hasConflict = bookingRepository.existsConflictingBooking(roomId, startTime, endTime);
        if (hasConflict) {
            throw new Exception("Room is already booked for the selected time slot");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new Exception("Room not found"));

        Booking booking = new Booking(user, room, startTime, endTime, "CONFIRMED");
        
        // Mock Google Calendar Sync
        booking.setGoogleCalendarEventId(UUID.randomUUID().toString());

        return bookingRepository.save(booking);
    }
}
