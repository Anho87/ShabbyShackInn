package com.example.shabbyshackinn.models;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@JsonTypeName("RoomOpened")
@Entity
@DiscriminatorValue("RoomOpened")
public class RoomOpened extends RoomEvent {
    @Override
    public String getType() {
        return "RoomOpened";
    }

    @Override
    public String getCleaningByUser() {
        return "";
    }
}
