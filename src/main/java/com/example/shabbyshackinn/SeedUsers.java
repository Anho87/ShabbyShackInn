package com.example.shabbyshackinn;

import com.example.shabbyshackinn.security.UserDataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class SeedUsers implements CommandLineRunner {

    @Autowired
    private UserDataSeeder userDataSeeder;

    @Override
    public void run(String... args) throws Exception {

    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            userDataSeeder.seedUsers();
        };
    }

}
