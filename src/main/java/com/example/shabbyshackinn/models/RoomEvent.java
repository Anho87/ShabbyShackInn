package com.example.shabbyshackinn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;




// [x] Received '{"type":"RoomOpened","TimeStamp":"2024-05-13T17:36:27.15293805","RoomNo":"7"}'

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomEvent {
    
    @Id
    @GeneratedValue
    private Long id;
    private String type;
    @JsonProperty("RoomNo")
    private int RoomNo;
    @JsonProperty("TimeStamp")
    private LocalDateTime TimeStamp;
    @JsonProperty("CleaningByUser")
    private String CleaningByUser;
    
    public RoomEvent(String type, int RoomNo, LocalDateTime TimeStamp, String CleaningByUser) {
        this.type = type;
        this.RoomNo = RoomNo;
        this.TimeStamp = TimeStamp;
        this.CleaningByUser = CleaningByUser;
    }
    
    public RoomEvent(String type, int RoomNo, LocalDateTime TimeStamp) {
        this.type = type;
        this.RoomNo = RoomNo;
        this.TimeStamp = TimeStamp;
    }
}
