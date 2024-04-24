package com.example.shabbyshackinn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
//
//            customerRepo.save(cust1);
//            customerRepo.save(cust2);
//            customerRepo.save(cust3);
//            customerRepo.save(cust4);
//            customerRepo.save(cust5);
//
//
//            Room room1 = new Room(1, RoomType.DOUBLE, 2, 1);
//            Room room2 = new Room(2,RoomType.SINGLE, 1, 0);
//            Room room3 = new Room(3,RoomType.DOUBLE, 2, 2);
//            Room room4 = new Room(4,RoomType.DOUBLE, 2, 0);
//            Room room5 = new Room(5,RoomType.SINGLE, 1, 0);
//
//            roomRepo.save(room1);
//            roomRepo.save(room2);
//            roomRepo.save(room3);
//            roomRepo.save(room4);
//            roomRepo.save(room5);
//
//            Booking b1 = new Booking(cust1, LocalDate.of(2024,4,22),LocalDate.of(2024,05,01),12345,2,room3);
//            Booking b2 = new Booking(cust3,LocalDate.of(2024,04,15),LocalDate.of(2024,04,17),54321,0,room2);
//            Booking b3 = new Booking(cust5,LocalDate.of(2024,05,01),LocalDate.of(2024,05,03),98765,1,room1);
//
//            bookingRepo.save(b1);
//            bookingRepo.save(b2);
//            bookingRepo.save(b3);
//
//
//        };
//    }

}
