package com.example.shabbyshackinn.utilities;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class HtmlFileService {

    public void saveHtmlToFile(String htmlCode) {
        try {
            FileWriter writer = new FileWriter("bookingConfirmationEmail.html");
            writer.write(htmlCode);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
