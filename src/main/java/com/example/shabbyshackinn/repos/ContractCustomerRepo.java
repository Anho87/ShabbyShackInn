package com.example.shabbyshackinn.repos;

import com.example.shabbyshackinn.dtos.MiniContractCustomerDto;
import com.example.shabbyshackinn.models.ContractCustomer;
import com.example.shabbyshackinn.models.Customer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContractCustomerRepo extends JpaRepository<ContractCustomer, Long> {
    List<ContractCustomer> findAllByCompanyNameContainsOrContactNameContainsOrCountryContains
            (String CompanyName,String ContactName,String Country,Sort sort);
}
