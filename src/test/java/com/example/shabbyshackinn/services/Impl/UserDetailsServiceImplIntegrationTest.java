package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.security.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserDetailsServiceImplIntegrationTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @BeforeEach
    void setUp() {
        Role roleReceptionist = Role.builder().name("Receptionist").build();
        Role roleAdmin = Role.builder().name("Admin").build();

        roleRepo.save(roleReceptionist);
        roleRepo.save(roleAdmin);

        userRepo.save(CreateUser("test1@test.test","Receptionist","123"));
        userRepo.save(CreateUser("test2@test.test","Admin","456"));
        userRepo.save(CreateUser("test3@test.test","Receptionist","789"));
    }
    private User CreateUser(String mail, String group, String password) {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(roleRepo.findByName(group));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(password);
        return User.builder().enabled(true).password(hash).username(mail).roles(roles).build();
    }

    @Test
    void loadUserByUsername(){
        User user = userRepo.getByUsername("test1@test.test");

        assertEquals("test1@test.test",user.getUsername());
        assertEquals(1,user.getRoles().size());
    }

    @Test
    void findAllRoleNames(){
        List<String> roles = roleRepo.findAllRoleNames();

        assertEquals(2,roles.size());
        assertTrue(roles.contains("Admin"));
        assertTrue(roles.contains("Receptionist"));
    }

    @Test
    void findRoleByName(){
        Role roleReceptionist = Role.builder().name("Receptionist").build();
        Role roleAdmin = Role.builder().name("Admin").build();

        Role roleReceptionistResult = roleRepo.findByName("Receptionist");
        Role roleAdminResult = roleRepo.findByName("Admin");

        assertEquals(roleAdmin.getName(),roleAdminResult.getName());
        assertEquals(roleReceptionist.getName(),roleReceptionistResult.getName());
    }
}
