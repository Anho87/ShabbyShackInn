package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.dtos.DetailedRoomDto;
import com.example.shabbyshackinn.dtos.MiniRoomDto;
import com.example.shabbyshackinn.models.Room;

import java.util.List;

public interface RoomService {//
    DetailedRoomDto roomToDetailedRoomDTO(Room room);
    List<DetailedRoomDto> getAllRooms();
    String addRoom(DetailedRoomDto room);
    Room detailedRoomToRoom(DetailedRoomDto room);
    MiniRoomDto roomToMiniRoomDto(Room room);
    String updateRoom(DetailedRoomDto room);
    String deleteRoom(Long id);
    MiniRoomDto findMiniRoomByRoomNumber(int roomNumber);
}
