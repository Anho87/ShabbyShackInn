package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.dtos.DetailedContractCustomerDto;
import com.example.shabbyshackinn.dtos.MiniContractCustomerDto;
import com.example.shabbyshackinn.models.ContractCustomer;
import com.example.shabbyshackinn.repos.ContractCustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ContractCustomerServiceTests {

    @Mock
    private ContractCustomerRepo contractCustomerRepo;

    @InjectMocks
    private ContractCustomerServiceImpl service = new ContractCustomerServiceImpl(contractCustomerRepo);

//    @BeforeEach
//    void setUp(){
//        ContractCustomer contractCustomer1 = new ContractCustomer(1L,1,"Eriksson","Erik Eriksson","CEO","Erikssonsvägen 14","Stockholm",17553,"Sverige","123123123","1234-12345");
//        ContractCustomer contractCustomer2 = new ContractCustomer(2L,2,"Nokia","Nokia Nokiasson","BEO","Nokiavägen 14","Göteborg",19712,"Sverige","456456456","5678-56879");
//        service.addContractCustomer(contractCustomer1);
//        service.addContractCustomer(contractCustomer2);
////        contractCustomerRepo.save(contractCustomer1);
////        contractCustomerRepo.save(contractCustomer2);
//
//    }

    Long customerId1 = 1L;
    Long customerId2 = 2L;
    int externalSystemId1 = 1;
    int externalSystemId2 = 2;
    String companyName1 = "Eriksson";
    String companyName2 = "Nokia";
    String contactName1 = "Erik Eriksson";
    String contactName2 = "Nokia Nokiasson";
    String contactTitle1 = "CEO";
    String contactTitle2 = "BEO";
    String streetAdress1 = "Erikssonsvägen 14";
    String streetAdress2 = "Nokiavägen 14";
    String city1 = "Stockholm";
    String city2 = "Göteborg";
    int postalCode1 = 17553;
    int postalCode2 = 19712;
    String country1 = "Sverige";
    String country2 = "Finland";
    String phone1 = "123123123";
    String phone2 = "456456456";
    String fax1 = "1234-12345";
    String fax2 = "5678-56879";
    ContractCustomer contractCustomer1 = new ContractCustomer(customerId1, externalSystemId1, companyName1
            , contactName1, contactTitle1, streetAdress1
            , city1, postalCode1, country1, phone1, fax1);
    ContractCustomer contractCustomer2 = new ContractCustomer(customerId2, externalSystemId2, companyName2
            , contactName2, contactTitle2, streetAdress2
            , city2, postalCode2, country2, phone2, fax2);
    MiniContractCustomerDto miniContractCustomerDto1 = MiniContractCustomerDto.builder().id(customerId1).companyName(companyName1)
            .contactName(contactName1).country(country1).build();
    MiniContractCustomerDto miniContractCustomerDto2 = MiniContractCustomerDto.builder().id(customerId2).companyName(companyName2)
            .contactName(contactName2).country(country2).build();

    @Test
    void getAllContractCustomersTest() {
        when(contractCustomerRepo.findAll()).thenReturn(Arrays.asList(contractCustomer1, contractCustomer2));
        ContractCustomerServiceImpl service2 = new ContractCustomerServiceImpl(contractCustomerRepo);
        List<ContractCustomer> contractCustomerList = service2.getAllContractCustomers();

        assertEquals(2, contractCustomerList.size());
    }

    @Test
    void addContractCustomerTest() {
        when(contractCustomerRepo.save(contractCustomer1)).thenReturn(contractCustomer1);
        ContractCustomerServiceImpl service2 = new ContractCustomerServiceImpl(contractCustomerRepo);

        String feedBack = service2.addContractCustomer(contractCustomer1);
        assertTrue(feedBack.equalsIgnoreCase("Contract customer" + contractCustomer1.companyName + "is saved!"));
    }

    @Test
    void getAllMiniContractCustomersTest() {
        when(contractCustomerRepo.findAll()).thenReturn(Arrays.asList(contractCustomer1, contractCustomer2));
        ContractCustomerServiceImpl service2 = new ContractCustomerServiceImpl(contractCustomerRepo);
        List<MiniContractCustomerDto> miniContractCustomerList = service2.getAllMiniContractCustomers();
        assertEquals(2, miniContractCustomerList.size());
        assertEquals(miniContractCustomerDto1.getId(), miniContractCustomerList.get(0).getId());
        assertEquals(miniContractCustomerDto1.getCompanyName(), miniContractCustomerList.get(0).getCompanyName());
        assertEquals(miniContractCustomerDto1.getContactName(), miniContractCustomerList.get(0).getContactName());
        assertEquals(miniContractCustomerDto1.getCountry(), miniContractCustomerList.get(0).getCountry());

        assertEquals(miniContractCustomerDto2.getId(), miniContractCustomerList.get(1).getId());
        assertEquals(miniContractCustomerDto2.getCompanyName(), miniContractCustomerList.get(1).getCompanyName());
        assertEquals(miniContractCustomerDto2.getContactName(), miniContractCustomerList.get(1).getContactName());
        assertEquals(miniContractCustomerDto2.getCountry(), miniContractCustomerList.get(1).getCountry());
    }
    
    @Test
    void contractCustomerToMiniContractCustomerDtoTest(){
        MiniContractCustomerDto actual = service.contractCustomerToMiniContractCustomerDto(contractCustomer1);
        
        assertEquals(actual.getId(),contractCustomer1.id);
        assertEquals(actual.getCompanyName(),contractCustomer1.companyName);
        assertEquals(actual.getContactName(),contractCustomer1.contactName);
        assertEquals(actual.getCountry(),contractCustomer1.country);
    }
    
    @Test
    void saveOrUpdateContractCustomerTest(){
        String newContactName = "Andreas Holmberg";
        String oldContactNameForContactCustomer1 = contractCustomer1.contactName;
        contractCustomer1.contactName = newContactName;
        when(contractCustomerRepo.findContractCustomerByExternalSystemId(contractCustomer1.externalSystemId)).thenReturn(contractCustomer1);
        ContractCustomerServiceImpl service2 = new ContractCustomerServiceImpl(contractCustomerRepo);
        String feedBack = service2.saveOrUpdateContractCustomer(contractCustomer1);
        assertTrue(feedBack.equalsIgnoreCase("Contract customer " + contractCustomer1.companyName + " is updated"));
        assertEquals(newContactName,contractCustomer1.contactName);
        assertNotEquals(oldContactNameForContactCustomer1,contractCustomer1.contactName);

        ContractCustomer newContractCustomer = new ContractCustomer(3L,3,"Sony"
                ,"Sony Sonysson","DEO","Tokyovägen 15","Tokyo"
                ,12323,"Japan","09887771","4312-1233");
        when(contractCustomerRepo.findContractCustomerByExternalSystemId(newContractCustomer.externalSystemId)).thenReturn(null);
        String feedBacknewCustomer = service2.saveOrUpdateContractCustomer(newContractCustomer);
        System.out.println(feedBacknewCustomer);
        assertTrue(feedBacknewCustomer.equalsIgnoreCase("Contract customer" + newContractCustomer.companyName + "is saved!"));
    }
    
    @Test
    void getContractCustomerByExternalSystemIdTest(){
        when(contractCustomerRepo.findContractCustomerByExternalSystemId(1)).thenReturn(contractCustomer1);
        ContractCustomerServiceImpl service2 = new ContractCustomerServiceImpl(contractCustomerRepo);
        ContractCustomer actual = service2.getContractCustomerByExternalSystemId(1);
        
        assertEquals(actual.id, contractCustomer1.id);
        assertEquals(actual.externalSystemId, contractCustomer1.externalSystemId);
        assertEquals(actual.companyName, contractCustomer1.companyName);
        assertEquals(actual.contactName, contractCustomer1.contactName);
        assertEquals(actual.contactTitle, contractCustomer1.contactTitle);
        assertEquals(actual.streetAddress, contractCustomer1.streetAddress);
        assertEquals(actual.city, contractCustomer1.city);
        assertEquals(actual.postalCode, contractCustomer1.postalCode);
        assertEquals(actual.country, contractCustomer1.country);
        assertEquals(actual.phone, contractCustomer1.phone);
        assertEquals(actual.fax, contractCustomer1.fax);
    }

    @Test
    void updateContractCustomerTest(){
        String newContactName = "Andreas Holmberg";
        String oldContactNameForContactCustomer1 = contractCustomer1.contactName;
        ContractCustomer updatedContractCustomer = new ContractCustomer(customerId1,externalSystemId1,companyName1
                ,newContactName,contactTitle1,streetAdress1,city1,postalCode1,country1,phone1,fax1);
        ContractCustomerServiceImpl service2 = new ContractCustomerServiceImpl(contractCustomerRepo);
        String feedBack = service2.updateContractCustomer(contractCustomer1,updatedContractCustomer);
        assertTrue(feedBack.equalsIgnoreCase("Contract customer " + contractCustomer1.companyName + " is updated"));
        assertEquals(newContactName,contractCustomer1.contactName);
        assertNotEquals(oldContactNameForContactCustomer1,contractCustomer1.contactName);

    }
    
    @Test
    void contractCustomerToDetailedContractCustomerDtoTest(){
        DetailedContractCustomerDto actual = service.contractCustomerToDetailedContractCustomerDto(contractCustomer1);

        assertEquals(actual.getId(), contractCustomer1.id);
        assertEquals(actual.getExternalSystemId(), contractCustomer1.externalSystemId);
        assertEquals(actual.getCompanyName(), contractCustomer1.companyName);
        assertEquals(actual.getContactName(), contractCustomer1.contactName);
        assertEquals(actual.getContactTitle(), contractCustomer1.contactTitle);
        assertEquals(actual.getStreetAddress(), contractCustomer1.streetAddress);
        assertEquals(actual.getCity(), contractCustomer1.city);
        assertEquals(actual.getPostalCode(), contractCustomer1.postalCode);
        assertEquals(actual.getCountry(), contractCustomer1.country);
        assertEquals(actual.getPhone(), contractCustomer1.phone);
        assertEquals(actual.getFax(), contractCustomer1.fax);
    }
    
    @Test
    void findDetailedContractCustomerByIdTest(){
        when(contractCustomerRepo.findById(contractCustomer1.id)).thenReturn(Optional.of(contractCustomer1));
        ContractCustomerServiceImpl service2 = new ContractCustomerServiceImpl(contractCustomerRepo);
        DetailedContractCustomerDto actual = service2.findDetailedContractCustomerById(contractCustomer1.id);


        assertEquals(actual.getId(), contractCustomer1.id);
        assertEquals(actual.getExternalSystemId(), contractCustomer1.externalSystemId);
        assertEquals(actual.getCompanyName(), contractCustomer1.companyName);
        assertEquals(actual.getContactName(), contractCustomer1.contactName);
        assertEquals(actual.getContactTitle(), contractCustomer1.contactTitle);
        assertEquals(actual.getStreetAddress(), contractCustomer1.streetAddress);
        assertEquals(actual.getCity(), contractCustomer1.city);
        assertEquals(actual.getPostalCode(), contractCustomer1.postalCode);
        assertEquals(actual.getCountry(), contractCustomer1.country);
        assertEquals(actual.getPhone(), contractCustomer1.phone);
        assertEquals(actual.getFax(), contractCustomer1.fax);
    }

    @Test
    void findAllBySearchAndSortOrderTest(){
        String searchWord = "n";
        String sortCol = "companyName";
        String sortOrder = "ASC";
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortCol);
        when(contractCustomerRepo.findAllByCompanyNameContainsOrContactNameContainsOrCountryContains
                (searchWord,searchWord,searchWord,sort)).thenReturn(Arrays.asList(contractCustomer2,contractCustomer1));
        List<ContractCustomer> actual = contractCustomerRepo.findAllByCompanyNameContainsOrContactNameContainsOrCountryContains
                (searchWord,searchWord,searchWord,sort);
        assertEquals(actual.get(0).id, contractCustomer2.id);
        assertEquals(actual.get(0).externalSystemId, contractCustomer2.externalSystemId);
        assertEquals(actual.get(0).companyName, contractCustomer2.companyName);
        assertEquals(actual.get(0).contactName, contractCustomer2.contactName);
        assertEquals(actual.get(0).contactTitle, contractCustomer2.contactTitle);
        assertEquals(actual.get(0).streetAddress, contractCustomer2.streetAddress);
        assertEquals(actual.get(0).city, contractCustomer2.city);
        assertEquals(actual.get(0).postalCode, contractCustomer2.postalCode);
        assertEquals(actual.get(0).country, contractCustomer2.country);
        assertEquals(actual.get(0).phone, contractCustomer2.phone);
        assertEquals(actual.get(0).fax, contractCustomer2.fax);

        assertEquals(actual.get(1).id, contractCustomer1.id);
        assertEquals(actual.get(1).externalSystemId, contractCustomer1.externalSystemId);
        assertEquals(actual.get(1).companyName, contractCustomer1.companyName);
        assertEquals(actual.get(1).contactName, contractCustomer1.contactName);
        assertEquals(actual.get(1).contactTitle, contractCustomer1.contactTitle);
        assertEquals(actual.get(1).streetAddress, contractCustomer1.streetAddress);
        assertEquals(actual.get(1).city, contractCustomer1.city);
        assertEquals(actual.get(1).postalCode, contractCustomer1.postalCode);
        assertEquals(actual.get(1).country, contractCustomer1.country);
        assertEquals(actual.get(1).phone, contractCustomer1.phone);
        assertEquals(actual.get(1).fax, contractCustomer1.fax);
    }
}
