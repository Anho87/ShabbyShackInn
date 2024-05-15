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
    private final RoomRepo roomRepo;
    
    public RoomEventServiceImpl(RoomEventRepo roomEventRepo, RoomRepo roomRepo) {
        this.roomEventRepo = roomEventRepo;
        this.roomRepo = roomRepo;
    }
    
    @Override
    public List<RoomEvent> getAllRoomEvents(Long id) {
        return roomEventRepo.findAll().stream().filter(roomEvent -> roomEvent.getRoomNo() == id).toList();
    }
}
