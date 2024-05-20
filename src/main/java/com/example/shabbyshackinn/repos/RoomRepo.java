package com.example.shabbyshackinn.repos;

import com.example.shabbyshackinn.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepo extends JpaRepository<Room,Long> {
    Room findRoomByRoomNumber(int roomNumber);
    @Query(value = "SELECT * FROM Room r WHERE r.id NOT IN :roomIds AND (r.beds + r.possible_extra_beds) >= :amountOfPersons", nativeQuery = true)
    List<Room> findAllByIdIsNotAndBedsPlusPossibleExtraBedsIsGreaterThanEqual(List<Long> roomIds, int amountOfPersons);

}
