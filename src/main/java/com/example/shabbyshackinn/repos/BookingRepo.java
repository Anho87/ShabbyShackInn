package com.example.shabbyshackinn.repos;

import com.example.shabbyshackinn.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking,Long> {
}
