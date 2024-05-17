package com.example.shabbyshackinn.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("RoomCleaningFinished")
public class RoomCleaningFinished extends RoomEvent {
    @Getter(onMethod_ = {@JsonProperty("CleaningByUser")})
    public String CleaningByUser;

    @Override
    public String getType() {
        return "RoomCleaningFinished";
    }


}
