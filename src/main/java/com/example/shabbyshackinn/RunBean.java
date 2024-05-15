package com.example.shabbyshackinn;

import com.example.shabbyshackinn.models.*;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.CustomerRepo;
import com.example.shabbyshackinn.repos.RoomEventRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ComponentScan
public class RunBean implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("RunBean");
    }

    @Bean
    public CommandLineRunner demo(CustomerRepo customerRepo, RoomRepo roomRepo, BookingRepo
            bookingRepo, RoomEventRepo roomEventRepo) {
        return (args) -> {
            Customer cust1 = new Customer("Andreas", "Holmberg", "0702879445", "Andreas.holmber@airbnb.se");
            Customer cust2 = new Customer("Johan", "Johnsson", "0702345116", "johan.johnsson@airbnb.se");
            Customer cust3 = new Customer("Felix", "Dahlberg", "0706547889", "Felix.Dahlber@airbnb.se");
            Customer cust4 = new Customer("Kalle", "Johansson", "0706584225", "Kalle.johansson@airnbnb.se");
            Customer cust5 = new Customer("Peter", "Petersson", "0709878456", "Peter.petersson@airbnb.se");
            Customer cust6 = new Customer("Alice", "Smith", "0701000220", "alice.smith@domain.com");
            Customer cust7 = new Customer("Bob", "Brown", "0702000330", "bob.brown@domain.com");
            Customer cust8 = new Customer("Carol", "Davis", "0703000440", "carol.davis@domain.com");
            Customer cust9 = new Customer("David", "Evans", "0704000550", "david.evans@domain.com");
            Customer cust10 = new Customer("Eve", "Foster", "0705000660", "eve.foster@domain.com");
            Customer cust11 = new Customer("Frank", "Gibson", "0706000770", "frank.gibson@domain.com");
            Customer cust12 = new Customer("Grace", "Harris", "0707000880", "grace.harris@domain.com");
            Customer cust13 = new Customer("Hank", "Irwin", "0708000990", "hank.irwin@domain.com");
            Customer cust14 = new Customer("Ivy", "Jones", "0709000100", "ivy.jones@domain.com");
            Customer cust15 = new Customer("John", "Klein", "0710000110", "john.klein@domain.com");


            customerRepo.save(cust1);
            customerRepo.save(cust2);
            customerRepo.save(cust3);
            customerRepo.save(cust4);
            customerRepo.save(cust5);
            customerRepo.save(cust6);
            customerRepo.save(cust7);
            customerRepo.save(cust8);
            customerRepo.save(cust9);
            customerRepo.save(cust10);
            customerRepo.save(cust11);
            customerRepo.save(cust12);
            customerRepo.save(cust13);
            customerRepo.save(cust14);
            customerRepo.save(cust15);


            Room room1 = new Room(1, RoomType.DOUBLE, 2, 3000, 1);
            Room room2 = new Room(2, RoomType.SINGLE, 1, 1000, 0);
            Room room3 = new Room(3, RoomType.DOUBLE, 2, 4000, 2);
            Room room4 = new Room(4, RoomType.DOUBLE, 2, 2000, 0);
            Room room5 = new Room(5, RoomType.SINGLE, 1, 1000, 0);
            Room room6 = new Room(6, RoomType.SINGLE, 1, 1000, 0);
            Room room7 = new Room(7, RoomType.DOUBLE, 2, 3000, 1);
            Room room8 = new Room(8, RoomType.DOUBLE, 2, 4000, 2);
            Room room9 = new Room(9, RoomType.SINGLE, 1, 1000, 0);
            Room room10 = new Room(10, RoomType.DOUBLE, 2, 2000, 0);
            Room room11 = new Room(11, RoomType.SINGLE, 1, 1000, 0);
            Room room12 = new Room(12, RoomType.DOUBLE, 2, 3000, 1);
            Room room13 = new Room(13, RoomType.DOUBLE, 2, 4000, 2);
            Room room14 = new Room(14, RoomType.SINGLE, 1, 1000, 0);
            Room room15 = new Room(15, RoomType.DOUBLE, 2, 2000, 0);

            roomRepo.save(room1);
            roomRepo.save(room2);
            roomRepo.save(room3);
            roomRepo.save(room4);
            roomRepo.save(room5);
            roomRepo.save(room6);
            roomRepo.save(room7);
            roomRepo.save(room8);
            roomRepo.save(room9);
            roomRepo.save(room10);
            roomRepo.save(room11);
            roomRepo.save(room12);
            roomRepo.save(room13);
            roomRepo.save(room14);
            roomRepo.save(room15);

            LocalDate today = LocalDate.now();

            Booking b1 = new Booking(cust1, today.plusDays(1), today.plusDays(5), 12345, 1, 4000, room1);
            Booking b2 = new Booking(cust3, today.plusDays(1), today.plusDays(5), 54321, 0, 4000, room2);
            Booking b3 = new Booking(cust5, today.plusDays(1), today.plusDays(5), 98765, 2, 4000, room3);
            Booking b4 = new Booking(cust2, today.plusDays(1), today.plusDays(5), 98765, 0, 4000, room4);
            Booking b5 = new Booking(cust1, today.plusDays(1), today.plusDays(5), 98765, 0, 4000, room5);
            Booking b6 = new Booking(cust6, today.plusDays(5), today.plusDays(7), 6001, 0, 2000, room6);
            Booking b7 = new Booking(cust7, today.plusDays(5), today.plusDays(7), 6002, 1, 2000, room7);
            Booking b8 = new Booking(cust8, today.plusDays(5), today.plusDays(7), 6003, 2, 2000, room8);
            Booking b9 = new Booking(cust9, today.plusDays(5), today.plusDays(7), 6004, 0, 2000, room9);
            Booking b10 = new Booking(cust10, today.plusDays(5), today.plusDays(7), 6005, 0, 2000, room10);
            Booking b11 = new Booking(cust11, today.plusDays(6), today.plusDays(9), 6006, 0, 3000, room11);
            Booking b12 = new Booking(cust12, today.plusDays(6), today.plusDays(9), 6007, 1, 3000, room12);
            Booking b13 = new Booking(cust1, today.plusDays(6), today.plusDays(9), 6008, 2, 3000, room13);
            Booking b14 = new Booking(cust2, today.plusDays(6), today.plusDays(9), 6009, 0, 3000, room14);
            Booking b15 = new Booking(cust3, today.plusDays(6), today.plusDays(9), 6010, 0, 3000, room15);
            bookingRepo.save(b1);
            bookingRepo.save(b2);
            bookingRepo.save(b3);
            bookingRepo.save(b4);
            bookingRepo.save(b5);
            bookingRepo.save(b6);
            bookingRepo.save(b7);
            bookingRepo.save(b8);
            bookingRepo.save(b9);
            bookingRepo.save(b10);
            bookingRepo.save(b11);
            bookingRepo.save(b12);
            bookingRepo.save(b13);
            bookingRepo.save(b14);
            bookingRepo.save(b15);

            LocalDateTime localDateTime = LocalDateTime.now();
            RoomEvent roomEvent1 = new RoomEvent("Dörren stängd", 1, localDateTime);
            RoomEvent roomEvent2 = new RoomEvent("Dörren Öppen", 2, localDateTime);
            RoomEvent roomEvent3 = new RoomEvent("Städning påbörjad", 3, localDateTime);
            RoomEvent roomEvent4 = new RoomEvent("Dörren stängd", 4, localDateTime);
            RoomEvent roomEvent5 = new RoomEvent("Dörren stängd", 5, localDateTime);
            RoomEvent roomEvent6 = new RoomEvent("Dörren stängd", 6, localDateTime);
            RoomEvent roomEvent7 = new RoomEvent("Dörren stängd", 7, localDateTime);
            RoomEvent roomEvent8 = new RoomEvent("Dörren stängd", 8, localDateTime);
            RoomEvent roomEvent9 = new RoomEvent("Dörren stängd", 9, localDateTime);
            RoomEvent roomEvent10 = new RoomEvent("Dörren stängd", 10, localDateTime);
            RoomEvent roomEvent11 = new RoomEvent("Dörren stängd", 11, localDateTime);
            RoomEvent roomEvent12 = new RoomEvent("Dörren stängd", 12, localDateTime);
            RoomEvent roomEvent13 = new RoomEvent("Dörren stängd", 13, localDateTime);
            RoomEvent roomEvent14 = new RoomEvent("Dörren stängd", 14, localDateTime);
            RoomEvent roomEvent15 = new RoomEvent("Dörren stängd", 15, localDateTime);

            roomEventRepo.save(roomEvent1);
            roomEventRepo.save(roomEvent2);
            roomEventRepo.save(roomEvent3);
            roomEventRepo.save(roomEvent4);
            roomEventRepo.save(roomEvent5);
            roomEventRepo.save(roomEvent6);
            roomEventRepo.save(roomEvent7);
            roomEventRepo.save(roomEvent8);
            roomEventRepo.save(roomEvent9);
            roomEventRepo.save(roomEvent10);
            roomEventRepo.save(roomEvent11);
            roomEventRepo.save(roomEvent12);
            roomEventRepo.save(roomEvent13);
            roomEventRepo.save(roomEvent14);
            roomEventRepo.save(roomEvent15);

        };
    }
}

