package com.example.shabbyshackinn;

import com.example.shabbyshackinn.models.Booking;
import com.example.shabbyshackinn.models.Customer;
import com.example.shabbyshackinn.models.Room;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.CustomerRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShabbyShackInnApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShabbyShackInnApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CustomerRepo customerRepo, RoomRepo roomRepo, BookingRepo bookingRepo) {
        return (args) -> {
            Customer cust1 = new Customer("Andreas");
            Customer cust2 = new Customer("Johan");
            Customer cust3 = new Customer("Felix");
            Customer cust4 = new Customer("Kalle");
            Customer cust5 = new Customer("Peter");

            customerRepo.save(cust1);
            customerRepo.save(cust2);
            customerRepo.save(cust3);
            customerRepo.save(cust4);
            customerRepo.save(cust5);


            Room room1 = new Room(1, 2, 1);
            Room room2 = new Room(2, 1, 2);
            Room room3 = new Room(3, 2, 1);
            Room room4 = new Room(4, 3, 0);
            Room room5 = new Room(5, 1, 1);

            roomRepo.save(room1);
            roomRepo.save(room2);
            roomRepo.save(room3);
            roomRepo.save(room4);
            roomRepo.save(room5);
            
            Booking b1 = new Booking(cust1, 2,"2024-04-20", "2024-05-01");
            Booking b2 = new Booking(cust3,2,"2024-04-21", "2024-05-02");
            Booking b3 = new Booking(cust5, 1,"2024-04-15", "2024-04-17");
            
            bookingRepo.save(b1);
            bookingRepo.save(b2);
            bookingRepo.save(b3);
            
            
        };
    }

}
