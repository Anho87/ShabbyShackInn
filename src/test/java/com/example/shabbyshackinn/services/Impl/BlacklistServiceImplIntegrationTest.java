package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.services.JsonStreamProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BlacklistServiceImplIntegrationTest {

    @Autowired
    JsonStreamProvider jsonStreamProvider;

    BlacklistServiceImpl sut;

    @Test
    void getBlacklistedCustomersWillFetch() throws IOException {
        sut = new BlacklistServiceImpl(jsonStreamProvider);
        Scanner s = new Scanner(sut.jsonStreamProvider.getDataStreamFullBlacklist()).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        assertTrue(result.contains("\"id\""));
        assertTrue(result.contains("\"email\""));
        assertTrue(result.contains("\"name\""));
        assertTrue(result.contains("\"group\""));
        assertTrue(result.contains("\"created\""));
        assertTrue(result.contains("\"ok\""));
    }

    @Test
    void getBlacklistCheckResponseWillFetch() throws IOException {
        sut = new BlacklistServiceImpl(jsonStreamProvider);
        Scanner s = new Scanner(sut.jsonStreamProvider.getDataStreamBlacklistCheck("test@email.test")).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        assertTrue(result.contains("\"statusText\""));
        assertTrue(result.contains("\"ok\""));
    }
}