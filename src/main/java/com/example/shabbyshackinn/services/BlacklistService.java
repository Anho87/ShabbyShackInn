package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.models.BlackListedCustomer;
import com.example.shabbyshackinn.models.BlacklistResponse;

import java.util.List;

public interface BlacklistService {

    String addBlackListedCustomer(BlackListedCustomer customer);

    BlacklistResponse checkIfEmailIsBlacklisted(String email);

    String updateBlacklistedCustomer(BlackListedCustomer blackListedCustomer);

    List<BlackListedCustomer> getBlacklistedCustomersFromAPI();

}
