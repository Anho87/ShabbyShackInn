package com.example.shabbyshackinn;

import com.example.shabbyshackinn.models.Booking;
import com.example.shabbyshackinn.models.Customer;
import com.example.shabbyshackinn.models.Room;
import com.example.shabbyshackinn.models.RoomType;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.CustomerRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Objects;

@SpringBootApplication
public class ShabbyShackInnApplication {

    public static void main(String[] args) {
        if (args.length == 0) {
            SpringApplication.run(ShabbyShackInnApplication.class, args);
        } else if (Objects.equals(args[0], "fetchContractCustomers")) {
            SpringApplication application = new SpringApplication(FetchContractCustomers.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        } else if (Objects.equals(args[0], "fetchShippers")) {
            SpringApplication application = new SpringApplication(FetchShippers.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        } else if (Objects.equals(args[0], "readQueue")) {
            SpringApplication application = new SpringApplication(ReadQueueApp.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);

        } else if (Objects.equals(args[0], "runBean")) {
            SpringApplication application = new SpringApplication(RunBean.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);

        }
    }
}
