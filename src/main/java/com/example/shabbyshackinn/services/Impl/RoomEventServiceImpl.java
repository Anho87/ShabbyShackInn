package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.models.RoomEvent;
import com.example.shabbyshackinn.repos.RoomEventRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import com.example.shabbyshackinn.services.RoomEventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomEventServiceImpl implements RoomEventService {
    
    private final RoomEventRepo roomEventRepo;
    
    public RoomEventServiceImpl(RoomEventRepo roomEventRepo) {
        this.roomEventRepo = roomEventRepo;
    }
    
    @Override
    public List<RoomEvent> getAllRoomEvents(Long id) {
//        return roomEventRepo.findAll().stream().filter(roomEvent -> roomEvent.getRoomNo() == id).toList();
        return roomEventRepo.findRoomEventsByRoomNo(Math.toIntExact(id));
    }
}
