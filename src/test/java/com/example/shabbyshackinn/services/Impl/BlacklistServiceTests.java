package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.models.BlackListedCustomer;
import com.example.shabbyshackinn.models.BlacklistResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
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
        when(jsonStreamProvider.getDataStreamFullBlacklist()).thenReturn(getClass().getClassLoader().getResourceAsStream("blacklist.json"));

        List<BlackListedCustomer> result = sut.getBlacklistedCustomersFromAPI();

        assertEquals(2, result.size());
        assertEquals(163, result.get(0).getId());
        assertEquals("ShabbyShackInn", result.get(0).getGroup());
        assertFalse(result.get(0).isOk());

    }

    @Test
    void checkIfEmailIsBlacklistedTest() throws IOException {
        String okEmail = "okEmail@tets.test";
        String notOkEmail = "notOkEmail@tets.test";
        String emailNotOnBlacklist = "notOnBlacklist@tets.test";

        when(jsonStreamProvider.getDataStreamBlacklistCheck(argThat(email -> email != null && !email.isEmpty()))).thenReturn(getClass().getClassLoader().getResourceAsStream("blacklistResponseOk.json"));
        when(jsonStreamProvider.getDataStreamBlacklistCheck(okEmail)).thenReturn(getClass().getClassLoader().getResourceAsStream("blacklistResponseOk.json"));
        when(jsonStreamProvider.getDataStreamBlacklistCheck(notOkEmail)).thenReturn(getClass().getClassLoader().getResourceAsStream("blacklistResponseNotOk.json"));

        BlacklistResponse resultOkEmail = sut.checkIfEmailIsBlacklisted(okEmail);

        BlacklistResponse resultNotOkEmail = sut.checkIfEmailIsBlacklisted(notOkEmail);

        BlacklistResponse resultEmailNotOnBlacklist = sut.checkIfEmailIsBlacklisted(emailNotOnBlacklist);
        System.out.println(resultEmailNotOnBlacklist.getStatusText());

        assertEquals("OK", resultOkEmail.getStatusText());
        assertTrue(resultOkEmail.isOk());
        assertEquals("Blacklisted", resultNotOkEmail.getStatusText());
        assertFalse(resultNotOkEmail.isOk());
        assertEquals("OK", resultEmailNotOnBlacklist.getStatusText());
        assertTrue(resultEmailNotOnBlacklist.isOk());
    }

}
