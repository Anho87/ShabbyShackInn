package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.models.Room;
import com.example.shabbyshackinn.models.RoomType;
import com.example.shabbyshackinn.repos.RoomRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class RoomRepoIntegrationTest {

    @Autowired
    private RoomRepo roomRepo;

    @BeforeEach
    void setUp() {
        roomRepo.deleteAll();
        
        Room roomWith2Beds = new Room(1L, RoomType.SINGLE, 1, 1, 100, 1);
        Room roomWith3Beds = new Room(2L, RoomType.DOUBLE, 2, 2, 200, 1);
        Room roomWith4Beds = new Room(3L, RoomType.DOUBLE, 3, 2, 300, 2);

        roomRepo.saveAll(List.of(roomWith2Beds, roomWith3Beds, roomWith4Beds));
    }

    @Test
    void findRoomByRoomNumber() {
        int roomNumber = 3;
        Room room = roomRepo.findRoomByRoomNumber(roomNumber);
        assertEquals(3L, room.getId());
        assertEquals(3, room.getRoomNumber());
        assertEquals(2, room.getBeds());
        assertEquals(300, room.getPrice());
        assertEquals(2, room.getPossibleExtraBeds());
    }

    @Test
    void findAvailableRoomsForThreePeople() {
        int amountOfPeople = 3;
        List<Room> actual = roomRepo.findAllByBedsPlusExtraBedsIsGreaterThanEqual(amountOfPeople);
        assertEquals(2, actual.size());
    }

    @Test
    void findAvailableRoomsForFourPeople() {
        int amountOfPeople = 4;
        List<Room> actual = roomRepo.findAllByBedsPlusExtraBedsIsGreaterThanEqual(amountOfPeople);
        assertEquals(1, actual.size());
    }

    @Test
    void findAllRoomsByIdIsNot() {
        List<Room> roomsNotInList = roomRepo.findAllByIdIsNot(List.of(2L, 3L));
        assertEquals(1, roomsNotInList.size());
    }
}
