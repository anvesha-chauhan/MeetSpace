package com.example.meetspace.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @Min(1)
    private int capacity;

    private String amenities; // e.g., "Whiteboard, Projector, Video Conferencing"

    public Room(String name, String location, int capacity, String amenities) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.amenities = amenities;
    }
}
