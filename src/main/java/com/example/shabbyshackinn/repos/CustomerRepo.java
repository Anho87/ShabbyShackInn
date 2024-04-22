package com.example.shabbyshackinn.repos;

import com.example.shabbyshackinn.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
}

