package com.example.shabbyshackinn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RoomClosed.class,name="RoomClosed"),
        @JsonSubTypes.Type(value = RoomCleaningFinished.class,name="RoomCleaningFinished"),
        @JsonSubTypes.Type(value = RoomCleaningStarted.class,name="RoomCleaningStarted"),
        @JsonSubTypes.Type(value = RoomOpened.class,name="RoomOpened")
})
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
