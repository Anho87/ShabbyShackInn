package com.example.shabbyshackinn.repos;


import com.example.shabbyshackinn.models.RoomEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomEventRepo extends JpaRepository<RoomEvent, Long> {
}
