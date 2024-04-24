package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.dtos.DetailedCustomerDto;
import com.example.shabbyshackinn.dtos.DetailedRoomDto;
import com.example.shabbyshackinn.models.Room;
import com.example.shabbyshackinn.models.RoomType;
import com.example.shabbyshackinn.repos.RoomRepo;
import com.example.shabbyshackinn.services.RoomService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService){
        this.roomService = roomService;
    }

    @RequestMapping("/getAll")
    public List<DetailedRoomDto> getAllRooms(){
        return roomService.getAllRooms();
    }

    @RequestMapping("/add")
    public String addRoom(@RequestParam RoomType roomType, @RequestParam int beds,
                          @RequestParam int roomNumber, @RequestParam int possibleExtraBeds){
        return roomService.updateRoom(new DetailedRoomDto(roomType, beds, roomNumber, possibleExtraBeds));
    }

    @RequestMapping("/delete")
    public String deleteRoom(@RequestParam Long id){
        return roomService.deleteRoom(id);
    }

    @RequestMapping("/update")
    public String updateRoom(@RequestParam Long id,@RequestParam RoomType roomType, @RequestParam int beds,
                             @RequestParam int roomNumber, @RequestParam int possibleExtraBeds){
        return roomService.updateRoom(new DetailedRoomDto(id,roomType,beds,roomNumber,possibleExtraBeds));
    }
}
