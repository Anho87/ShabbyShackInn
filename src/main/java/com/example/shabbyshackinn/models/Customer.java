package com.example.shabbyshackinn.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String phone;
    private String eMail;
    
    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings;
    
    public Customer(String name, String lastName, String phone, String eMail) {
        this.firstName = name;
        this.lastName = lastName;
        this.phone = phone;
        this.eMail = eMail;
        
    }
}
