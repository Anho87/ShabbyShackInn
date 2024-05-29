package com.example.shabbyshackinn.repos;

import com.example.shabbyshackinn.models.ContractCustomer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractCustomerRepo extends JpaRepository<ContractCustomer, Long> {
    List<ContractCustomer> findAllByCompanyNameContainsOrContactNameContainsOrCountryContains
            (String CompanyName, String ContactName, String Country, Sort sort);

    ContractCustomer findContractCustomerByExternalSystemId(int externalSystemId);
}
