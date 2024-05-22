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
    private Long id;
    private String companyName;
    private String contactName;
    private String country;
    
}
