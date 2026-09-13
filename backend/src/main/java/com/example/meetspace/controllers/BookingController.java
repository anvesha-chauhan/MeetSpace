package com.example.meetspace.controllers;

import com.example.meetspace.dto.BookingRequest;
import com.example.meetspace.dto.MessageResponse;
import com.example.meetspace.models.Booking;
import com.example.meetspace.repositories.BookingRepository;
import com.example.meetspace.security.UserDetailsImpl;
import com.example.meetspace.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    BookingRepository bookingRepository;

    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequest request, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        try {
            Booking booking = bookingService.createBooking(userDetails.getId(), request.getRoomId(), request.getStartTime(), request.getEndTime());
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/my")
    public ResponseEntity<List<Booking>> getMyBookings(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Booking> bookings = bookingRepository.findByUserId(userDetails.getId());
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Booking>> getRoomBookings(@PathVariable Long roomId) {
        List<Booking> bookings = bookingRepository.findByRoomId(roomId);
        return ResponseEntity.ok(bookings);
    }
}
