package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.models.Room;
import com.example.shabbyshackinn.repos.RoomRepo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomRepo roomRepo;

    public RoomController(RoomRepo roomRepo){
        this.roomRepo = roomRepo;
    }

    @RequestMapping("/getAll")
    public List<Room> getAllRooms(){
        return roomRepo.findAll();
    }
}
