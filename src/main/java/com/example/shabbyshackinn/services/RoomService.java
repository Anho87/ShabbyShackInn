package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.dtos.DetailedBookingDto;
import com.example.shabbyshackinn.dtos.DetailedRoomDto;
import com.example.shabbyshackinn.dtos.MiniRoomDto;
import com.example.shabbyshackinn.models.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {//
    DetailedRoomDto roomToDetailedRoomDTO(Room room);
    List<DetailedRoomDto> getAllRooms();
    Room detailedRoomToRoom(DetailedRoomDto room);
    MiniRoomDto roomToMiniRoomDto(Room room);
    MiniRoomDto findMiniRoomByRoomNumber(int roomNumber);
    List<DetailedRoomDto> findAvailableRooms(List<DetailedBookingDto> detailedBookingDtoList,int amountOfPersons);
    DetailedRoomDto findDetailedRoomById(Long id);
    MiniRoomDto findMiniRoomById(Long id);
}
