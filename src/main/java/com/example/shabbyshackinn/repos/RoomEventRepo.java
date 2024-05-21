package com.example.shabbyshackinn.repos;


import com.example.shabbyshackinn.models.RoomEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomEventRepo extends JpaRepository<RoomEvent, Long> {
    @Query(value = "SELECT * FROM room_event r WHERE r.room_no = :id", nativeQuery = true)
    List<RoomEvent> findRoomEventsByRoomNo(int id);
}


