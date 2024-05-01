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
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class ShabbyShackInnApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShabbyShackInnApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner demo(CustomerRepo customerRepo, RoomRepo roomRepo, BookingRepo bookingRepo) {
//        return (args) -> {
//            Customer cust1 = new Customer("Andreas", "Holmberg", "0702879445", "Andreas.holmber@airbnb.se");
//            Customer cust2 = new Customer("Johan", "Johnsson", "0702345116", "johan.johnsson@airbnb.se");
//            Customer cust3 = new Customer("Felix", "Dahlberg", "0706547889", "Felix.Dahlber@airbnb.se");
//            Customer cust4 = new Customer("Kalle", "Johansson", "0706584225", "Kalle.johansson@airnbnb.se");
//            Customer cust5 = new Customer("Peter", "Petersson", "0709878456", "Peter.petersson@airbnb.se");
//            Customer cust6 = new Customer("Alice", "Smith", "0701000220", "alice.smith@domain.com");
//            Customer cust7 = new Customer("Bob", "Brown", "0702000330", "bob.brown@domain.com");
//            Customer cust8 = new Customer("Carol", "Davis", "0703000440", "carol.davis@domain.com");
//            Customer cust9 = new Customer("David", "Evans", "0704000550", "david.evans@domain.com");
//            Customer cust10 = new Customer("Eve", "Foster", "0705000660", "eve.foster@domain.com");
//            Customer cust11 = new Customer("Frank", "Gibson", "0706000770", "frank.gibson@domain.com");
//            Customer cust12 = new Customer("Grace", "Harris", "0707000880", "grace.harris@domain.com");
//            Customer cust13 = new Customer("Hank", "Irwin", "0708000990", "hank.irwin@domain.com");
//            Customer cust14 = new Customer("Ivy", "Jones", "0709000100", "ivy.jones@domain.com");
//            Customer cust15 = new Customer("John", "Klein", "0710000110", "john.klein@domain.com");
//
//
//
//            customerRepo.save(cust1);
//            customerRepo.save(cust2);
//            customerRepo.save(cust3);
//            customerRepo.save(cust4);
//            customerRepo.save(cust5);
//            customerRepo.save(cust6);
//            customerRepo.save(cust7);
//            customerRepo.save(cust8);
//            customerRepo.save(cust9);
//            customerRepo.save(cust10);
//            customerRepo.save(cust11);
//            customerRepo.save(cust12);
//            customerRepo.save(cust13);
//            customerRepo.save(cust14);
//            customerRepo.save(cust15);
//
//
//            Room room1 = new Room(1, RoomType.DOUBLE, 2, 1);
//            Room room2 = new Room(2, RoomType.SINGLE, 1, 0);
//            Room room3 = new Room(3, RoomType.DOUBLE, 2, 2);
//            Room room4 = new Room(4, RoomType.DOUBLE, 2, 0);
//            Room room5 = new Room(5, RoomType.SINGLE, 1, 0);
//            Room room6 = new Room(6, RoomType.SINGLE, 1, 0);
//            Room room7 = new Room(7, RoomType.DOUBLE, 2, 1);
//            Room room8 = new Room(8, RoomType.DOUBLE, 2, 2);
//            Room room9 = new Room(9, RoomType.SINGLE, 1, 0);
//            Room room10 = new Room(10, RoomType.DOUBLE, 2, 0);
//            Room room11 = new Room(11, RoomType.SINGLE, 1, 0);
//            Room room12 = new Room(12, RoomType.DOUBLE, 2, 1);
//            Room room13 = new Room(13, RoomType.DOUBLE, 2, 2);
//            Room room14 = new Room(14, RoomType.SINGLE, 1, 0);
//            Room room15 = new Room(15, RoomType.DOUBLE, 2, 0);
//
//            roomRepo.save(room1);
//            roomRepo.save(room2);
//            roomRepo.save(room3);
//            roomRepo.save(room4);
//            roomRepo.save(room5);
//            roomRepo.save(room6);
//            roomRepo.save(room7);
//            roomRepo.save(room8);
//            roomRepo.save(room9);
//            roomRepo.save(room10);
//            roomRepo.save(room11);
//            roomRepo.save(room12);
//            roomRepo.save(room13);
//            roomRepo.save(room14);
//            roomRepo.save(room15);
//
//            LocalDate today = LocalDate.now();
//
//            Booking b1 = new Booking(cust1, today.plusDays(1),today.plusDays(5),12345,1,room1);
//            Booking b2 = new Booking(cust3,today.plusDays(1),today.plusDays(5),54321,0,room2);
//            Booking b3 = new Booking(cust5, today.plusDays(1),today.plusDays(5),98765,2,room3);
//            Booking b4 = new Booking(cust2, today.plusDays(1),today.plusDays(5),98765,0,room4);
//            Booking b5 = new Booking(cust1, today.plusDays(1),today.plusDays(5),98765,0,room5);
//            Booking booking6 = new Booking(cust6, today.plusDays(5), today.plusDays(7), 6001, 0, room6);
//            Booking booking7 = new Booking(cust7, today.plusDays(5), today.plusDays(7), 6002, 1, room7);
//            Booking booking8 = new Booking(cust8, today.plusDays(5), today.plusDays(7), 6003, 2, room8);
//            Booking booking9 = new Booking(cust9, today.plusDays(5), today.plusDays(7), 6004, 0, room9);
//            Booking booking10 = new Booking(cust10, today.plusDays(5), today.plusDays(7), 6005, 0, room10);
//            Booking booking11 = new Booking(cust11, today.plusDays(6), today.plusDays(9), 6006, 0, room11);
//            Booking booking12 = new Booking(cust12, today.plusDays(6), today.plusDays(9), 6007, 1, room12);
//            Booking booking13 = new Booking(cust1, today.plusDays(6), today.plusDays(9), 6008, 2, room13);
//            Booking booking14 = new Booking(cust2, today.plusDays(6), today.plusDays(9), 6009, 0, room14);
//            Booking booking15 = new Booking(cust3, today.plusDays(6), today.plusDays(9), 6010, 0, room15);
//            bookingRepo.save(b1);
//            bookingRepo.save(b2);
//            bookingRepo.save(b3);
//            bookingRepo.save(b4);
//            bookingRepo.save(b5);
//            bookingRepo.save(booking6);
//            bookingRepo.save(booking7);
//            bookingRepo.save(booking8);
//            bookingRepo.save(booking9);
//            bookingRepo.save(booking10);
//            bookingRepo.save(booking11);
//            bookingRepo.save(booking12);
//            bookingRepo.save(booking13);
//            bookingRepo.save(booking14);
//            bookingRepo.save(booking15);
//        };
//    }

}
