package com.example.shabbyshackinn.repos;

import com.example.shabbyshackinn.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepo extends JpaRepository<Room, Long> {
    Room findRoomByRoomNumber(int roomNumber);


    @Query("SELECT r FROM Room r WHERE (r.beds + r.possibleExtraBeds) >= :amountOfPersons")
    List<Room> findAllByBedsPlusExtraBedsIsGreaterThanEqual(@Param("amountOfPersons") int amountOfPersons);


    @Query("SELECT r FROM Room r WHERE r.id NOT IN :bookedRoomsList")
    List<Room> findAllByIdIsNot(@Param("bookedRoomsList") List<Long> bookedRoomsList);


}
