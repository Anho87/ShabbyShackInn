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

    @Test
    void findRoomByRoomNumber() {
        roomRepo.deleteAll();
        Room roomWith2Beds = new Room(1, RoomType.SINGLE, 1, 100, 1);
        Room roomWith3Beds = new Room(2, RoomType.DOUBLE, 2, 200, 1);
        Room roomWith4Beds = new Room(3, RoomType.DOUBLE, 2, 300, 2);
        roomRepo.saveAll(List.of(roomWith2Beds, roomWith3Beds, roomWith4Beds));

        int roomNumber = 3;
        Room room = roomRepo.findRoomByRoomNumber(roomNumber);
        assertEquals(3, room.getRoomNumber());
        assertEquals(2, room.getBeds());
        assertEquals(300, room.getPrice());
        assertEquals(2, room.getPossibleExtraBeds());
    }

    @Test
    void findAvailableRoomsForThreePeople() {
        roomRepo.deleteAll();
        Room roomWith2Beds = new Room(1, RoomType.SINGLE, 1, 100, 1);
        Room roomWith3Beds = new Room(2, RoomType.DOUBLE, 2, 200, 1);
        Room roomWith4Beds = new Room(3, RoomType.DOUBLE, 2, 300, 2);

        roomRepo.saveAll(List.of(roomWith2Beds, roomWith3Beds, roomWith4Beds));
        int amountOfPeople = 3;
        List<Room> actual = roomRepo.findAllByBedsPlusExtraBedsIsGreaterThanEqual(amountOfPeople);
        assertEquals(2, actual.size());
    }

    @Test
    void findAvailableRoomsForFourPeople() {
        roomRepo.deleteAll();
        Room roomWith2Beds = new Room(1, RoomType.SINGLE, 1, 100, 1);
        Room roomWith3Beds = new Room(2, RoomType.DOUBLE, 2, 200, 1);
        Room roomWith4Beds = new Room(3, RoomType.DOUBLE, 2, 300, 2);

        roomRepo.saveAll(List.of(roomWith2Beds, roomWith3Beds, roomWith4Beds));
        int amountOfPeople = 4;
        List<Room> actual = roomRepo.findAllByBedsPlusExtraBedsIsGreaterThanEqual(amountOfPeople);
        assertEquals(1, actual.size());
    }

    @Test
    void findAllRoomsByIdIsNot() {
        roomRepo.deleteAll();
        Room roomWith2Beds = new Room(1, RoomType.SINGLE, 1, 100, 1);
        Room roomWith3Beds = new Room(2, RoomType.DOUBLE, 2, 200, 1);
        Room roomWith4Beds = new Room(3, RoomType.DOUBLE, 2, 300, 2);

        roomRepo.saveAll(List.of(roomWith2Beds, roomWith3Beds, roomWith4Beds));
        List<Room> roomsNotInList = roomRepo.findAllByIdIsNot(List.of(roomWith2Beds.getId(), roomWith3Beds.getId()));
        assertEquals(1, roomsNotInList.size());
    }
}
