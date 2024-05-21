package com.example.shabbyshackinn.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    @Pattern(regexp = "[a-zA-ZÀ-ÿ'-]+")
    @Size(min = 2, max = 50)
    private String firstName;
    @Pattern(regexp = "[a-zA-ZÀ-ÿ'-]+")
    @Size(min = 2, max = 50)
    private String lastName;
    @Pattern(regexp = "(\\+46)?[0-9]{9,10}")
    private String phone;
    @Email
    private String eMail;
    
    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings;
    
    public Customer(Long id,String name, String lastName, String phone, String eMail) {
        this.id = id;
        this.firstName = name;
        this.lastName = lastName;
        this.phone = phone;
        this.eMail = eMail;
    }
    public Customer(String name, String lastName, String phone, String eMail) {
        this.firstName = name;
        this.lastName = lastName;
        this.phone = phone;
        this.eMail = eMail;
    }

    public Customer(Long id, String firstName, String lastName, String phone, String eMail, List<Booking> bookings) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.eMail = eMail;
        this.bookings = bookings;
    }
    
    public void addBooking(Booking booking){
        bookings.add(booking);
    }
}
