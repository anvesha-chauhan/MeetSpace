package com.example.meetspace.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    private String status; // "CONFIRMED", "CANCELLED"

    private String googleCalendarEventId;

    public Booking(User user, Room room, LocalDateTime startTime, LocalDateTime endTime, String status) {
        this.user = user;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }
}
