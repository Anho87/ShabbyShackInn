package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.dtos.DetailedBookingDto;
import com.example.shabbyshackinn.dtos.DetailedRoomDto;
import com.example.shabbyshackinn.dtos.MiniRoomDto;
import com.example.shabbyshackinn.models.Booking;
import com.example.shabbyshackinn.models.Room;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import com.example.shabbyshackinn.services.RoomService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepo roomRepo;
    final private BookingRepo bookingRepo;

    public RoomServiceImpl(RoomRepo roomRepo, BookingRepo bookingRepo){
        this.roomRepo = roomRepo;
        this.bookingRepo = bookingRepo;
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
//       Room room = roomRepo.findAll().stream().filter(r -> r.getRoomNumber() == roomNumber).findFirst().get();
//       return roomToMiniRoomDto(room);
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
    public List<DetailedRoomDto> findAvailableRooms(List<DetailedBookingDto> detailedBookingDtoList, int amountOfPersons) {
//        if (startDate.isBefore(endDate)){
//            List<DetailedRoomDto> bookedDetaliedRoomsDto = bookingRepo.findAll().stream()
//                    .filter(b -> b.getStartDate().isBefore(endDate) && b.getEndDate().isAfter(startDate))
//                    .map(b -> b.getRoom())
//                    .map(room -> roomToDetailedRoomDTO(room))
//                    .toList();
//            List<Long> bookedRoomsId = bookingRepo.findAllByStartDateIsBeforeAndEndDateIsAfter(endDate,startDate)
//                    .stream()
//                    .map(Booking::getRoom)
//                    .map(Room::getId)
//                    .toList();

//            return getAllRooms().stream()
//                    .filter(room -> !bookedDetaliedRoomsDto.contains(room))
//                    .filter(room -> room.getBeds() + room.getPossibleExtraBeds() >= amountOfPersons)
//                    .toList();
            return roomRepo.findAllByIdIsNotAndBedsPlusPossibleExtraBedsIsGreaterThanEqual
                            (detailedBookingDtoList.stream().map(DetailedBookingDto::getId).toList(),amountOfPersons)
                    .stream().map(this::roomToDetailedRoomDTO).toList();
//        }else {
//            return null;
//        }

    }


}
