package com.example.shabbyshackinn.models;

public enum RoomType {
    SINGLE("Single"),
    DOUBLE("Double");
    
    public final String roomType;
    
    RoomType(String roomType){
        this.roomType=roomType;
    }
}
