package com.example.shabbyshackinn.dtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MiniContractCustomerDto {
    @Id
    @GeneratedValue
    public int id;
    public String companyName;
    public String contactName;
    public String country;
    
}
