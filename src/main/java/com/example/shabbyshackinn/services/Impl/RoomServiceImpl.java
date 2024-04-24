package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.dtos.DetailedRoomDto;
import com.example.shabbyshackinn.dtos.MiniRoomDto;
import com.example.shabbyshackinn.models.Room;
import com.example.shabbyshackinn.repos.RoomRepo;
import com.example.shabbyshackinn.services.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepo roomRepo;

    public RoomServiceImpl(RoomRepo roomRepo){
        this.roomRepo = roomRepo;
    }

    @Override
    public DetailedRoomDto roomToDetailedRoomDTO(Room room) {
        return DetailedRoomDto.builder().id(room.getId()).roomType(room.getRoomType()).beds(room.getBeds())
                .possibleExtraBeds(room.getPossibleExtraBeds()).roomNumber(room.getRoomNumber()).build();
    }

    @Override
    public List<DetailedRoomDto> getAllRooms() {
        return roomRepo.findAll().stream().map(r -> roomToDetailedRoomDTO(r)).toList();
    }

    @Override
    public String addRoom(DetailedRoomDto room) {
        roomRepo.save(detailedRoomToRoom(room));
        return "Room " + room.getRoomType() + " added";
    }

    @Override
    public Room detailedRoomToRoom(DetailedRoomDto room) {
        return Room.builder().id(room.getId()).roomType(room.getRoomType()).beds(room.getBeds())
                .possibleExtraBeds(room.getPossibleExtraBeds()).roomNumber(room.getRoomNumber()).build();
    }

    @Override
    public MiniRoomDto roomToMiniRoomDto(Room room) {
        return MiniRoomDto.builder().id(room.getId()).roomType(room.getRoomType())
                .roomNumber(room.getRoomNumber()).build();
    }

    @Override
    public String updateRoom(DetailedRoomDto room) {
        roomRepo.save(detailedRoomToRoom(room));
        return "Room " + room.getRoomType() + " updated";
    }

    @Override
    public String deleteRoom(Long id) {
        roomRepo.deleteById(id);
        return "Room deleted";
    }
}
