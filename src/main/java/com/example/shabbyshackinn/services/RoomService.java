package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.dtos.DetailedRoomDto;
import com.example.shabbyshackinn.dtos.MiniRoomDto;
import com.example.shabbyshackinn.models.Room;

import java.time.LocalDate;
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
    List<DetailedRoomDto> findAvailableRooms(LocalDate startDate, LocalDate endDate, int amountOfPersons);
    DetailedRoomDto findDetailedRoomByRoomNumber(int roomNumber);
    DetailedRoomDto findDetailedRoomById(Long id);
    MiniRoomDto findMiniRoomById(Long id);
}
