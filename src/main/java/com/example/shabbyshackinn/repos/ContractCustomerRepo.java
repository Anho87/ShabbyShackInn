package com.example.shabbyshackinn.repos;

import com.example.shabbyshackinn.models.ContractCustomer;
import com.example.shabbyshackinn.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractCustomerRepo extends JpaRepository<ContractCustomer, Long> {
}
