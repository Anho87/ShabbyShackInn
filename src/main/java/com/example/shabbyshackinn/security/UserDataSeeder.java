package com.example.shabbyshackinn.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDataSeeder {
    @Autowired
    UserRepo userRepository;
    
    @Autowired
    RoleRepo roleRepository;
    
    public void seedUsers() {
        if (roleRepository.findByName("Admin") == null) {
            addRole("Admin");
        }
        if (roleRepository.findByName("Receptionist") == null) {
            addRole("Receptionist");
        }
        if (userRepository.getByUsername("johan.johnsson@airbnb.se") == null) {
            addUser("johan.johnsson@airbnb.se", "Admin", "1");
        }
        if (userRepository.getByUsername("Andreas.holmber@airbnb.se") == null) {
            addUser("Andreas.holmber@airbnb.se", "Receptionist", "2");
        }
    }
    
    private void addUser(String mail, String group, String password) {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName(group));
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(password);
        User user = User.builder().enabled(true).password(hash).username(mail).roles(roles).build();
        userRepository.save(user);
    }
    
    private void addRole(String name) {
        Role role = new Role();
        roleRepository.save(Role.builder().name(name).build());
    }
}
