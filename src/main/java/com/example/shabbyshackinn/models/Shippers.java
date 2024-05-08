package com.example.shabbyshackinn.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shippers {
    @Id
    @GeneratedValue
    public int id;
    public String companyName;
    public String phone;
}
