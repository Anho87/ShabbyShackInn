package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.models.BlackListedCustomer;
import com.example.shabbyshackinn.models.BlacklistResponse;
import com.example.shabbyshackinn.services.BlacklistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlacklistServiceImpl implements BlacklistService {



    private final ObjectMapper mapper = new JsonMapper();
    private final String apiUrl = "https://javabl.systementor.se/api/ShabbyShackInn/blacklist";

    @Override
    public String addBlackListedCustomer(BlackListedCustomer customer) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            mapper.writeValue(os, customer);
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Blacklisted customer added successfully");
                return "Blacklisted customer added successfully";
            } else {
                System.out.println("Failed to add blacklisted customer. Response code: " + responseCode);
                return "Failed to add blacklisted customer";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to add blacklisted customer";
        }
    }

    @Override
    public BlacklistResponse checkIfEmailIsBlacklisted(String email){
        try {
            mapper.registerModule(new JavaTimeModule());
           return mapper.readValue(new URL( apiUrl + "check/" + email), BlacklistResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BlacklistResponse.builder().build();
    }
    @Override
    public String updateBlacklistedCustomer(BlackListedCustomer blackListedCustomer){
        try {
            System.out.println(blackListedCustomer.ok);
            URL url = new URL(apiUrl + "/" + blackListedCustomer.getEmail());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            mapper.writeValue(os, blackListedCustomer);
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                System.out.println("Blacklisted customer updated successfully");
                return "Blacklisted customer updated successfully";
            } else {
                System.out.println("Failed to update blacklisted customer. Response code: " + responseCode);
                return "Failed to update blacklisted customer";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to update blacklisted customer";
        }

    }

    @Override
    public List<BlackListedCustomer> getBlacklistedCustomersFromAPI() {
        try {
            return mapper.readValue(new URL(apiUrl),
                    mapper.getTypeFactory().constructCollectionType(List.class, BlackListedCustomer.class));
        } catch (IOException e) {
            e.printStackTrace();

            return new ArrayList<>();
        }
    }
}
