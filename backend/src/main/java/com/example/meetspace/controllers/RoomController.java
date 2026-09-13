package com.example.meetspace.controllers;

import com.example.meetspace.models.Room;
import com.example.meetspace.repositories.RoomRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    RoomRepository roomRepository;

    @GetMapping
    public List<Room> getAllRooms(@RequestParam(required = false) Integer minCapacity) {
        if (minCapacity != null) {
            return roomRepository.findByCapacityGreaterThanEqual(minCapacity);
        }
        return roomRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Room> createRoom(@Valid @RequestBody Room room) {
        Room _room = roomRepository.save(new Room(room.getName(), room.getLocation(), room.getCapacity(), room.getAmenities()));
        return ResponseEntity.ok(_room);
    }
}
