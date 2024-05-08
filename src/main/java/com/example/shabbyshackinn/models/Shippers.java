package com.example.shabbyshackinn.models;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


public class Shippers {
    @Id
    @GeneratedValue
    public int id;
    public String companyName;
    public String phone;
 
}
