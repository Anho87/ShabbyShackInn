package com.example.shabbyshackinn.services.Impl;


import com.example.shabbyshackinn.models.BlackListedCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BlacklistServiceTests {

    private final JsonStreamProvider jsonStreamProvider = mock(JsonStreamProvider.class);

    BlacklistServiceImpl sut;

    @BeforeEach()
    void setUp() {
        sut = new BlacklistServiceImpl(jsonStreamProvider);
    }

    @Test
    void whenGetBlacklistCustomersShouldMapCorrectly() throws IOException {
        when(jsonStreamProvider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("blacklist.json"));

        List<BlackListedCustomer> result = sut.getBlacklistedCustomersFromAPI();

        assertEquals(2, result.size() );
        assertEquals(163, result.get(0).getId());
        assertEquals("ShabbyShackInn", result.get(0).getGroup());
        assertFalse(result.get(0).isOk());

    }
}
