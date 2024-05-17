package com.example.shabbyshackinn.models;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@JsonTypeName("RoomClosed")
@Entity
@DiscriminatorValue("RoomClosed")
public class RoomClosed extends RoomEvent {
    @Override
    public String getType() {
        return "RoomClosed";
    }

    @Override
    public String getCleaningByUser() {
        return "";
    }
}
