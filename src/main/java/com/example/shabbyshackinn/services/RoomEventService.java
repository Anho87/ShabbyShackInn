package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.models.RoomEvent;

import java.util.List;

public interface RoomEventService {
    public List<RoomEvent> getAllRoomEvents(Long id);
}
