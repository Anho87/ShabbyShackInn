package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.dtos.DetailedBookingDto;
import com.example.shabbyshackinn.dtos.DetailedRoomDto;
import com.example.shabbyshackinn.dtos.MiniRoomDto;
import com.example.shabbyshackinn.models.Booking;
import com.example.shabbyshackinn.models.Room;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import com.example.shabbyshackinn.services.BookingService;
import com.example.shabbyshackinn.services.RoomService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepo roomRepo;
    private final BookingRepo bookingRepo;
    private final BookingService bookingService;

    public RoomServiceImpl(RoomRepo roomRepo, BookingRepo bookingRepo, BookingService bookingService){
        this.roomRepo = roomRepo;
        this.bookingRepo = bookingRepo;
        this.bookingService = bookingService;
    }

    @Override
    public DetailedRoomDto roomToDetailedRoomDTO(Room room) {
        return DetailedRoomDto.builder().id(room.getId()).roomType(room.getRoomType()).price(room.getPrice()).beds(room.getBeds())
                .possibleExtraBeds(room.getPossibleExtraBeds()).roomNumber(room.getRoomNumber()).build();
    }

    @Override
    public List<DetailedRoomDto> getAllRooms() {
        return roomRepo.findAll().stream().map(r -> roomToDetailedRoomDTO(r)).toList();
    }
    

    @Override
    public Room detailedRoomToRoom(DetailedRoomDto room) {
        return Room.builder().id(room.getId()).roomType(room.getRoomType()).beds(room.getBeds()).price(room.getPrice())
                .possibleExtraBeds(room.getPossibleExtraBeds()).roomNumber(room.getRoomNumber()).build();
    }

    @Override
    public MiniRoomDto roomToMiniRoomDto(Room room) {
        return MiniRoomDto.builder().id(room.getId()).roomType(room.getRoomType())
                .roomNumber(room.getRoomNumber()).build();
    }
    
    @Override
    public MiniRoomDto findMiniRoomByRoomNumber(int roomNumber){
        return roomToMiniRoomDto(roomRepo.findRoomByRoomNumber(roomNumber));
    }
    
    
    @Override
    public DetailedRoomDto findDetailedRoomById(Long id){
        return roomToDetailedRoomDTO(roomRepo.findById(id).orElse(null));
    }
    @Override
    public MiniRoomDto findMiniRoomById(Long id){
        return roomToMiniRoomDto(roomRepo.findById(id).get());
    }

    @Override
    public List<DetailedRoomDto> findAvailableRooms(List<Long> alreadyBookedRoomsIds) {
            return roomRepo.findAllByIdIsNot(alreadyBookedRoomsIds).stream()
                    .map(this::roomToDetailedRoomDTO).toList();
    }

    @Override
    public List<DetailedRoomDto> findBigEnoughRoomsForNumberOfGuests(int amountOfPersons){
        return roomRepo.findAllByBedsPlusExtraBedsIsGreaterThanEqual(amountOfPersons).stream()
                .map(this::roomToDetailedRoomDTO).toList();
    }

    @Override
    public List<DetailedRoomDto> searchAvailableRooms(LocalDate startDate, LocalDate endDate, int numberOfGuests) {

        LocalDate today = LocalDate.now();

        if (startDate.isBefore(today) && endDate.isAfter(startDate)) {
            return Collections.emptyList();
        }

        List<DetailedRoomDto> bigEnoughRooms = findBigEnoughRoomsForNumberOfGuests(numberOfGuests);
        List<DetailedBookingDto> alreadyBookedRooms = bookingService.findBookingByDates(startDate, endDate);

        if (!alreadyBookedRooms.isEmpty()) {
            List<Long> roomsAlreadyBookedIds = alreadyBookedRooms.stream()
                    .map(room -> room.getMiniRoomDto().getId())
                    .toList();
            List<DetailedRoomDto> roomsNotAlreadyBooked = findAvailableRooms(roomsAlreadyBookedIds);

            return bigEnoughRooms.stream()
                    .filter(roomsNotAlreadyBooked::contains)
                    .toList();
        } else {
            return bigEnoughRooms;
        }
    }
}
